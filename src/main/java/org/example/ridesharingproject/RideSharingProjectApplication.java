package org.example.ridesharingproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class    RideSharingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideSharingProjectApplication.class, args);
    }

}
