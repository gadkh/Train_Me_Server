package TrainMe.TrainMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = {"TrainMe.layout","TrainMe.logic.JPA"})   
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"JasonFiles"}) 
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    	
    }
}
