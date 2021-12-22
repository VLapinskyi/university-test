package com.lapinskyi.universitytest.repositories.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lapinskyi.universitytest.domain.Department;
import com.lapinskyi.universitytest.repositories.exceptions.RepositoryException;

@Aspect
@Component
public class DepartmentRepositoryAspect {
    private final Logger logger = LoggerFactory.getLogger(DepartmentRepositoryAspect.class);
    
    @Pointcut("execution (com.lapinskyi.universitytest.domain.Department com.lapinskyi.universitytest.repositories.DepartmentRepository.findByNameIgnoreCase(String))")
    private void findByNameIgnoreCaseMethod() {
    }
    
    @Around("findByNameIgnoreCaseMethod()")
    Department aroundFindByNameIgnoreCaseAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = (String) joinPoint.getArgs()[0];
        
        if (logger.isDebugEnabled()) {
            logger.debug("Try to find department by name {} ignoring case", name);
        }
        
        try {
            Department targetMethod = (Department) joinPoint.proceed();
            
            if (targetMethod == null) {
                throw new NullPointerException("There is no department with name " + name);
            }
            
            if (logger.isDebugEnabled()) {
                logger.debug("By name {} was founded department {}", name, targetMethod);
            }
            return targetMethod;
        } catch (NullPointerException nullPointerException) {
            logger.error("There is no department with name {}", name, nullPointerException);
            throw new RepositoryException("The entity is not found when get department by name", nullPointerException);
        }
    }
}
