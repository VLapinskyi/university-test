package com.lapinskyi.universitytest.services.aspects;

import java.math.BigDecimal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lapinskyi.universitytest.domain.Degree;
import com.lapinskyi.universitytest.domain.Lector;
import com.lapinskyi.universitytest.repositories.exceptions.RepositoryException;
import com.lapinskyi.universitytest.services.exceptions.ServiceException;

@Aspect
@Component
public class DepartmentServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceAspect.class);
    
    @Pointcut("execution (com.lapinskyi.universitytest.domain.Lector com.lapinskyi.universitytest.services.DepartmentService.getDepartmentHead(String))")
    private void getDeparmentHeadMethod() {
    }
    
    @Pointcut("execution (int com.lapinskyi.universitytest.services.DepartmentService.getEmployeeCountFromDepartment(String))")
    private void getEmployeeCountFromDepartmentMethod() {
    }
    
    @Pointcut("execution (int com.lapinskyi.universitytest.services.DepartmentService.getEmployeeCountFromDepartmentByDegree(String, "
            + "com.lapinskyi.universitytest.domain.Degree))")
    private void getEmployeeCountFromDepartmentByDegreeMethod() {
    }
    
    @Pointcut("execution (java.math.BigDecimal com.lapinskyi.universitytest.services.DepartmentService.getAverageSalaryFromDepartment(String))")
    private void getAverageSalaryFromDepartmentMethod() {
    }
    
    @Around("getDeparmentHeadMethod()")
    Lector aroundGetDeparmentHeadAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String departmentName = (String) joinPoint.getArgs()[0];
        if (logger.isDebugEnabled()) {
            logger.debug("Try to get a head of department by department name {}", departmentName);
        }
        
        try {
            Lector targetMethod = (Lector) joinPoint.proceed();
            if (targetMethod == null) {
                throw new NullPointerException("There is no head of department with name " + departmentName);
            }
            
            if (logger.isDebugEnabled()) {
                logger.debug("The head of department {} is {}", departmentName, targetMethod);
            }
            
            return targetMethod;
        } catch (NullPointerException nullPointerException) {
            logger.error("There is no head of department with name {}", departmentName, nullPointerException);
            throw new ServiceException("There is no the head of department", nullPointerException);
        } catch (RepositoryException repositoryException) {
            logger.error("There is some error in repositories when get head of department by name {}", departmentName, repositoryException);
            throw new ServiceException("There is some error in repositories when get head of department", repositoryException);
        }
    }
    
    @Around("getEmployeeCountFromDepartmentMethod()")
    int aroundGetEmployeeCountFromDepartmentAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String departmentName = (String) joinPoint.getArgs()[0];
        if (logger.isDebugEnabled()) {
            logger.debug("Try to get employee count from department by department name {}", departmentName);
        }
        
        try {
            int targetMethod = (int) joinPoint.proceed();
            
            if (logger.isDebugEnabled()) {
                logger.debug("There is {} employees work for department {}", targetMethod, departmentName);
            }
            
            return targetMethod;
        } catch (RepositoryException repositoryException) {
            logger.error("There is some error in repositories when get employee count from department by name {}", departmentName, repositoryException);
            throw new ServiceException("There is some error in repositories when get employee count from department", repositoryException);
        }
    }
    
    @Around("getEmployeeCountFromDepartmentByDegreeMethod()")
    int aroundGetEmployeeCountFromDepartmentByDegreeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String departmentName = (String) joinPoint.getArgs()[0];
        Degree degree = (Degree) joinPoint.getArgs()[1];
        
        if (logger.isDebugEnabled()) {
            logger.debug("Try to get employee count from department by department name {} with degree {}", departmentName, degree);
        }
        
        try {
            int targetMethod = (int) joinPoint.proceed();
            
            if (logger.isDebugEnabled()) {
                logger.debug("There is {} employees with degree {} work for department {}", targetMethod, degree, departmentName);
            }
            
            return targetMethod;
        } catch (RepositoryException repositoryException) {
            logger.error("There is some error in repositories when get employee count with degree {} from department {}", degree, departmentName, repositoryException);
            throw new ServiceException("There is some error in repositories when get employee count with some degree from department", repositoryException);
        }
    }
    
    @Around("getAverageSalaryFromDepartmentMethod()")
    BigDecimal aroundGetAverageSalaryFromDepartmentAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String departmentName = (String) joinPoint.getArgs()[0];
        if (logger.isDebugEnabled()) {
            logger.debug("Try to get employee's average salary of department by department name {}", departmentName);
        }
        
        try {
            BigDecimal targetMethod = (BigDecimal) joinPoint.proceed();
            
            if (logger.isDebugEnabled()) {
                logger.debug("The average salary of employees from department {} is {}", departmentName, targetMethod);
            }
            
            return targetMethod;
        } catch (RepositoryException repositoryException) {
            logger.error("There is some error in repositories when get average salary of department {}", departmentName, repositoryException);
            throw new ServiceException("There is some error in repositories when get average salary of department", repositoryException);
        }
    }
}
