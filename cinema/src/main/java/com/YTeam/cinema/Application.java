package com.YTeam.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import postgresql.PSQLConnection;

import java.sql.*;
import java.util.logging.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
            PSQLConnection connetion = new PSQLConnection();
            SpringApplication.run(Application.class, args);




    }
}
