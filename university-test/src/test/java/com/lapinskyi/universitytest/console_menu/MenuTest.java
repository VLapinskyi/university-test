package com.lapinskyi.universitytest.console_menu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringJoiner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.lapinskyi.universitytest.Main;
import com.lapinskyi.universitytest.domain.Degree;
import com.lapinskyi.universitytest.domain.Lector;
import com.lapinskyi.universitytest.services.DepartmentService;
import com.lapinskyi.universitytest.services.LectorService;
import com.lapinskyi.universitytest.services.exceptions.ServiceException;
@ExtendWith(SpringExtension.class)
@Import(AopAutoConfiguration.class)
@ContextConfiguration(classes = Main.class, initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(locations = "/application-test.properties")
class MenuTest {

    private final PrintStream standardOut = out;
    private ByteArrayOutputStream outputStreamCaptor;
    private StringJoiner expectedText;
    private StringJoiner actualText;
    
    @Autowired
    private Menu menu;
    
    @MockBean
    private DepartmentService departmentService;
    
    @MockBean
    private LectorService lectorService;
    
    @MockBean
    private BufferedReader reader;
    
    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(menu, "reader", reader);
        ReflectionTestUtils.setField(menu, "lectorService", lectorService);
        ReflectionTestUtils.setField(menu, "departmentService", departmentService);
        outputStreamCaptor = new ByteArrayOutputStream();
        setOut(new PrintStream(outputStreamCaptor));
        expectedText = new StringJoiner(lineSeparator(), "", lineSeparator());
        actualText = new StringJoiner(lineSeparator());
    }
    
    @AfterEach
    void tearDown() {
        setOut(standardOut);
    }
    
    @Test
    void shouldGetMenu() {
        expectedText.add("Please, type a command.");
        expectedText.add("List of available commands:");
        expectedText.add("Who is head of department {department_name}");
        expectedText.add("Show {department_name} statistics");
        expectedText.add("Show the average salary for the department {department_name}");
        expectedText.add("Show count of employee for {department_name}");
        expectedText.add("Global search by {template}");
        menu.getMenu();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }
    
    @Test
    void shouldReturnTrueWhenIsContinue() throws IOException {
        boolean expectedResult = true;
        when(reader.readLine()).thenReturn("1");
        assertEquals(expectedResult, menu.isContinue());
    }
    
    @Test
    void shouldReturnFalseWhenIsContinue() throws IOException {
        boolean expectedResult = false;
        when(reader.readLine()).thenReturn("0");
        assertEquals(expectedResult, menu.isContinue());
    }
    
    @Test
    void shouldPrintExpectedTextWhenIsContinueCorrect() throws IOException {
        String correctOption = "0";
        when(reader.readLine()).thenReturn(correctOption);
        expectedText.add("Do you want to do something else?");
        expectedText.add("1 - Yes.");
        expectedText.add("0 - No.");
        menu.isContinue();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldPrintExpectedTextWhenIsContinueIncorrect() throws IOException {
        String incorrectOption = "3";
        String correctOption = "0";
        when(reader.readLine()).thenReturn(incorrectOption).thenReturn(correctOption);
        expectedText.add("Do you want to do something else?");
        expectedText.add("1 - Yes.");
        expectedText.add("0 - No.");
        expectedText.add("You type incorrect data");
        expectedText.add("Please, make your decision");
        menu.isContinue();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
    
    @Test
    void shouldPrintHeadOfDepartment() throws IOException {
        String department = "department";
        String command = "Who is head of department {" + department + "}";
        when(reader.readLine()).thenReturn(command);
        
        Lector lector = new Lector();
        lector.setId(1);
        lector.setFirstName("Ivan");
        lector.setLastName("Ivanov");
        
        when(departmentService.getDepartmentHead(department)).thenReturn(lector);
        
        expectedText.add(String.format("Head of %s department is %s", department, lector.getFirstName() + " " + lector.getLastName()));
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
        
    }
    
    @Test
    void shouldPrintDepartmentStatics() throws IOException {
        String department = "department";
        String command = "Show {" + department + "} statistics";
        when(reader.readLine()).thenReturn(command);
        
        when(departmentService.getEmployeeCountFromDepartmentByDegree(department, Degree.ASSISTANT)).thenReturn(1);
        when(departmentService.getEmployeeCountFromDepartmentByDegree(department, Degree.ASSOCIATE_PROFESSOR)).thenReturn(2);
        when(departmentService.getEmployeeCountFromDepartmentByDegree(department, Degree.PROFESSOR)).thenReturn(3);
        
        expectedText.add(String.format("assistants - %d", 1));
        expectedText.add(String.format("associate professors - %d", 2));
        expectedText.add(String.format("professors - %d", 3));
        
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
    
    @Test
    void shouldPrintAverageSalaryFromDepartment() throws IOException {
        String department = "department";
        String command = "Show the average salary for the department {" + department + "}";
        when(reader.readLine()).thenReturn(command);
        
        when(departmentService.getAverageSalaryFromDepartment(department)).thenReturn(new BigDecimal("1000.00"));
        
        expectedText.add(String.format("The average salary of %s is %s", department, "1000.00"));
        
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
    
    @Test
    void shouldPrintEmployeeCountFromDepartment() throws IOException {
        String department = "department";
        String command = "Show count of employee for {" + department + "}";
        when(reader.readLine()).thenReturn(command);
        
        int expectedNumber = 10;
        
        when(departmentService.getEmployeeCountFromDepartment(department)).thenReturn(expectedNumber);
        
        expectedText.add(Integer.toString(expectedNumber));
        
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
    
    @Test
    void shouldPrintSearchResultLectorsByTemplate() throws IOException {
        String template = "man";
        String command = "Global search by {" + template + "}";
        when(reader.readLine()).thenReturn(command);
        
        Lector lector = new Lector();
        lector.setId(1);
        lector.setFirstName("Roman");
        lector.setLastName("Romanov");
        
        when(lectorService.searchLectorsByTemplate(template)).thenReturn(new HashSet<Lector>(Arrays.asList(lector)));
        
        expectedText.add(lector.getFirstName() + " " + lector.getLastName());
        
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
    
    @Test
    void shouldPrintEmptyResultLectorsByTemplate() throws IOException {
        String template = "man";
        String command = "Global search by {" + template + "}";
        when(reader.readLine()).thenReturn(command);
        
        when(lectorService.searchLectorsByTemplate(template)).thenReturn(new HashSet<Lector>());
        
        expectedText.add("There is no result for typed template");
        
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
    
    @Test
    void shouldPrintAnswerWhenServiceException() throws IOException {
        String department = "department";
        String command = "Show count of employee for {" + department + "}";
        when(reader.readLine()).thenReturn(command);
        
        when(departmentService.getEmployeeCountFromDepartment(department)).thenThrow(new ServiceException("service exception", new NullPointerException("exception")));
        
        expectedText.add("Sorry, can't execute command. Check pointed data and try again.");
        
        menu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }
}
