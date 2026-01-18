package com.upb.agripos.config;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    // constructor PRIVATE
    private DatabaseConnection() {
        System.out.println("Database Connected");
    }

    // akses global
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }        
        return instance;
    }
}
