package com.lapinskyi.universitytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lapinskyi.universitytest.console_menu.Menu;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		
	    ConfigurableApplicationContext context =  SpringApplication.run(Main.class, args);
	    
	    Menu menu = context.getBean(Menu.class);
	    System.out.println("Hello!");
	    
	    boolean isContinue = true;
	    
	    while(isContinue) {
	        menu.getMenu();
	        menu.makeChoosenAction();
	        isContinue = menu.isContinue();
	    }
	    
	    System.out.println("Good bye");
	}

}
