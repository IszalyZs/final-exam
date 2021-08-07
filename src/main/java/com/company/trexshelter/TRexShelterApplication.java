package com.company.trexshelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TRexShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TRexShelterApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("The program was terminated.");
            }
        });
    }
}
