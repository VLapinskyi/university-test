package com.lapinskyi.universitytest.console_menu.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lapinskyi.universitytest.services.exceptions.ServiceException;

@Aspect
@Component
public class MenuAspect {
    private final Logger logger = LoggerFactory.getLogger(MenuAspect.class);
    
    @Pointcut("execution (void com.lapinskyi.universitytest.console_menu.Menu.makeChoosenAction())")
    private void makeChoosenActionMethod() {
    }
    
    @Around("makeChoosenActionMethod()")
    void aroundMakeChoosenActionAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        
        if (logger.isDebugEnabled()) {
            logger.debug("Try to get command from console and return answer for user");
        }
        
        try {
            joinPoint.proceed();
            
            if(logger.isDebugEnabled()) {
                logger.debug("The command was succesfully executed");
            }
        } catch (ServiceException exception) {
            logger.error("There was an exception with a message {}", exception.getException().getMessage(), exception);
            System.out.println("Sorry, can't execute command. Check pointed data and try again.");
        }
    }
}
