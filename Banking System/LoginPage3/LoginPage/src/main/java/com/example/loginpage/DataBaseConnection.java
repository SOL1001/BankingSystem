package com.example.loginpage;
import java.sql.SQLException;
import  java.sql.*;
public class DataBaseConnection {
    Connection connection;
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            String dbName="firstdb";
            String usName="root";
            String password="";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,usName,password);
            return connection;
        }
    }

