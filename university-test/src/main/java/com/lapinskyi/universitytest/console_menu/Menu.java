package com.lapinskyi.universitytest.console_menu;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lapinskyi.universitytest.domain.Degree;
import com.lapinskyi.universitytest.domain.Lector;
import com.lapinskyi.universitytest.services.DepartmentService;
import com.lapinskyi.universitytest.services.LectorService;

@Component
public class Menu {
    static List<String> commands;
    
    private final Logger logger = LoggerFactory.getLogger(Menu.class);
    
    private DepartmentService departmentService;
    private LectorService lectorService;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    static {
        commands = new ArrayList<>();
        commands.add("Who is head of department {department_name}");
        commands.add("Show {department_name} statistics");
        commands.add("Show the average salary for the department {department_name}");
        commands.add("Show count of employee for {department_name}");
        commands.add("Global search by {template}");
    }
    
    @Autowired
    public Menu (DepartmentService departmentService, LectorService lectorService) {
        this.departmentService = departmentService;
        this.lectorService = lectorService;
    }
    
    public void getMenu() {
        out.println("Please, type a command.");
        out.println("List of available commands:");
        commands.stream().forEach(out :: println);
    }
    
    public void makeChoosenAction () {
        for (int i = 0; i < 1; i++) {
            String typedCommand = getTextFromConsole();
            int commandIndex = getCommandIndex(typedCommand);
            switch (commandIndex) {
                case 0:
                    printHeadOfDepartment(typedCommand);
                    break;
                case 1:
                    printDepartmentStatics(typedCommand);
                    break;
                case 2:
                    printAverageSalaryFromDepartment(typedCommand);
                    break;
                case 3:
                    printEmployeeCountFromDepartment(typedCommand);
                    break;
                case 4:
                    printSearchResultLectorsByTemplate(typedCommand);
                    break;
                default:
                    i--;
                    out.println("You type incorect data");
                    out.println("Please, try again");
                    getMenu();
            }
        }
    }
    
    public boolean isContinue() {
        out.println("Do you want to do something else?");
        out.println("1 - Yes.");
        out.println("0 - No.");
        boolean result = false;
        for (int i = 0; i < 1; i++) {
            int choosenNumber = getNumberFromConsole();
            switch (choosenNumber) {
                case 1:
                    result = true;
                    break;
                case 0:
                    result = false;
                    break;
            default:
                i--;
                out.println("You type incorrect data");
                out.println("Please, make your decision");
            }
        }
        
        return result;
    }
    
    private int getNumberFromConsole () {
        StringBuilder result = new StringBuilder();
        try {
           result.append(reader.readLine());
        } catch (IOException e) {
            logger.error("cannot read data from console", e);
        }
        return Integer.parseInt(result.toString());
    }
    
    private String getTextFromConsole () {
        StringBuilder result = new StringBuilder();
        try {
           result.append(reader.readLine());
        } catch (IOException e) {
            logger.error("cannot read data from console", e);
        }
        return result.toString();
    }
    
    private int getCommandIndex(String command) {
        int resultIndex = -1; 
        for (int i = 0; i < commands.size(); i++) {
            String commandPattern = commands.get(i).replaceAll("\\{.*\\}", "\\\\{.*\\\\}");
            if(Pattern.matches(commandPattern, command)) {
                resultIndex = i;
                break;
            }
        }
        return resultIndex;
    }
    
    private void printHeadOfDepartment(String command) {
        String departmentName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
        Lector lector = departmentService.getDepartmentHead(departmentName);
        out.println(String.format("Head of %s department is %s", departmentName, lector.getFirstName() + " " + lector.getLastName()));
    }
    
    private void printDepartmentStatics(String command) {
        String departmentName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
        int assistantsNumber = departmentService.getEmployeeCountFromDepartmentByDegree(departmentName, Degree.ASSISTANT);
        int associateProfessorNumber = departmentService.getEmployeeCountFromDepartmentByDegree(departmentName, Degree.ASSOCIATE_PROFESSOR);
        int professorNumber = departmentService.getEmployeeCountFromDepartmentByDegree(departmentName, Degree.PROFESSOR);
        
        out.println(String.format("assistants - %d", assistantsNumber));
        out.println(String.format("associate professors - %d", associateProfessorNumber));
        out.println(String.format("professors - %d", professorNumber));
    }
    
    private void printAverageSalaryFromDepartment(String command) {
        String departmentName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
        BigDecimal averageSalary = departmentService.getAverageSalaryFromDepartment(departmentName);
        out.println(String.format("The average salary of %s is %s", departmentName, averageSalary));
    }
    
    private void printEmployeeCountFromDepartment(String command) {
        String departmentName = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
        int employeeNumber = departmentService.getEmployeeCountFromDepartment(departmentName);
        out.println(employeeNumber);
    }
    
    private void printSearchResultLectorsByTemplate(String command) {
        String template = command.substring(command.indexOf("{") + 1, command.indexOf("}"));
        Set<Lector> resultList = lectorService.searchLectorsByTemplate(template);
        StringJoiner resultText = new StringJoiner(", ");
        for (Lector lector : resultList) {
            resultText.add(lector.getFirstName() + " " + lector.getLastName());
        }
        if(resultText.length() > 0) {
            out.println(resultText);
        } else {
            out.println("There is no result for typed template");
        }
    }
}
