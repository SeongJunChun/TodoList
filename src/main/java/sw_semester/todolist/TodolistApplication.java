package sw_semester.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class TodolistApplication {
	public static void main(String[] args) {

		SpringApplication.run(TodolistApplication.class, args);
	}
}
