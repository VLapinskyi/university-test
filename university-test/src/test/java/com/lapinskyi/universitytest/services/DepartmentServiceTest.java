package com.lapinskyi.universitytest.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.lapinskyi.universitytest.domain.Degree;
import com.lapinskyi.universitytest.domain.Department;
import com.lapinskyi.universitytest.domain.Lector;
import com.lapinskyi.universitytest.repositories.DepartmentRepository;
import com.lapinskyi.universitytest.repositories.exceptions.RepositoryException;
import com.lapinskyi.universitytest.services.aspects.DepartmentServiceAspect;
import com.lapinskyi.universitytest.services.exceptions.ServiceException;

@ExtendWith(SpringExtension.class)
@Import({AopAutoConfiguration.class, DepartmentRepository.class, DepartmentService.class, DepartmentServiceAspect.class})
class DepartmentServiceTest {
    
    @Autowired
    private DepartmentService departmentService;
    
    @MockBean
    private DepartmentRepository departmentRepository;
    
    private Lector lector;
    private Lector anotherLector;
    private String departmentName = "Department";
    private Department department;
    
    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(departmentService, "departmentRepository", departmentRepository);
        lector = new Lector();
        lector.setId(1);
        lector.setFirstName("Test");
        lector.setLastName("Test");
        lector.setDegree(Degree.PROFESSOR);
        lector.setSalary(1000);
        
        anotherLector = new Lector();
        anotherLector.setId(2);
        anotherLector.setFirstName("Ivan");
        anotherLector.setLastName("Romanov");
        anotherLector.setDegree(Degree.ASSISTANT);
        anotherLector.setSalary(1001);
        
        department = new Department();
        department.setId(1);
        department.setName(departmentName);
        department.setLectors(Arrays.asList(lector, anotherLector));
        department.setDepartmentHead(lector);
    }

    @Test
    void shouldGetDepartmentHead() {              
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenReturn(department);
        
        assertEquals(lector, departmentService.getDepartmentHead(departmentName));        
    }
    
    @Test
    void shouldGetEmployeeCountFromDepartmentByDegree() {
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenReturn(department);
        
        assertEquals(1, departmentService.getEmployeeCountFromDepartmentByDegree(departmentName, Degree.PROFESSOR));
    }
    
    @Test
    void shouldGetEmployeeCountFromDepartment() {
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenReturn(department);
        
        assertEquals(2, departmentService.getEmployeeCountFromDepartment(departmentName));
    }
    
    @Test
    void shouldGetAverageSalaryFromDepartment() {        
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenReturn(department);
        BigDecimal expectedResult = new BigDecimal("1000.50");
        assertEquals(expectedResult, departmentService.getAverageSalaryFromDepartment(departmentName));
    }
    
    @Test
    void shouldThrowServiceExceptionWhenHeadIsNullWhileGetDepartmentHead() {
        String testName = "Test";
        Department testDepartment =  new Department();
        testDepartment.setId(2);
        testDepartment.setName(testName);
        
        when(departmentRepository.findByNameIgnoreCase(testName)).thenReturn(testDepartment);
        
        assertThrows(ServiceException.class, () -> departmentService.getDepartmentHead(testName));
    }
    
    @Test
    void shouldThrowServiceExceptionWhenRepositoryExceptionWhileGetDepartmentHead() {
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenThrow(RepositoryException.class);
        
        assertThrows(ServiceException.class, () -> departmentService.getDepartmentHead(departmentName));
    }
    
    @Test
    void shouldThrowServiceExceptionWhenRepositoryExceptionWhileGetEmployeeCountFromDepartment() {
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenThrow(RepositoryException.class);
        
        assertThrows(ServiceException.class, () -> departmentService.getEmployeeCountFromDepartment(departmentName));
    }
    
    @Test
    void shouldThrowServiceExceptionWhenRepositoryExceptionWhileGetEmployeeCountFromDepartmentByDegree() {
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenThrow(RepositoryException.class);
        
        assertThrows(ServiceException.class, () -> departmentService.getEmployeeCountFromDepartmentByDegree(departmentName, Degree.ASSISTANT));
    }
    
    @Test
    void shouldThrowServiceExceptionWhenRepositoryExceptionWhileGetAverageSalaryFromDepartment() {
        when(departmentRepository.findByNameIgnoreCase(departmentName)).thenThrow(RepositoryException.class);
        
        assertThrows(ServiceException.class, () -> departmentService.getAverageSalaryFromDepartment(departmentName));
    }
}
