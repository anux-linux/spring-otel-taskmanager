package fairsource.demo.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Task Management application.
 * This class serves as the entry point for the Spring Boot application.
 * It contains the main method that starts the application.
 */
@SpringBootApplication
public class TaskManagementApplication {

    /**
     * Main method to run the Task Management application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }

}