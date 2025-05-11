package com.example.studious;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    private final String url = "jdbc:mysql://localhost:3306/my_db";
    private final String username = "your_username";
    private final String password = "your_password";
    private Connection con;

    public MySQLConnection() {
        try {
            this.con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Connection getConnection() {
        return this.con;
    }


}
