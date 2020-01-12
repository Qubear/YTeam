package com.YTeam.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.YTeam.cinema.postgresql.PSQLConnection;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
            PSQLConnection connetion = new PSQLConnection();
            SpringApplication.run(Application.class, args);




    }
}
