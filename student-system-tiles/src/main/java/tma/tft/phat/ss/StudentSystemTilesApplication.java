package tma.tft.phat.ss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class StudentSystemTilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentSystemTilesApplication.class, args);
    }

}
