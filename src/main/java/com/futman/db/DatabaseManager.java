package com.futman.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static final String DEFAULT_DB = "jdbc:sqlite:futman.db";
    private static final String IN_MEMORY_DB = "jdbc:sqlite::memory:";
    
    private static DatabaseManager instance;
    private static DatabaseManager inMemoryInstance;
    
    private Connection connection;
    private boolean isInMemory;
    
    private DatabaseManager(boolean inMemory) {
        this.isInMemory = inMemory;
        try {
            String url = inMemory ? IN_MEMORY_DB : DEFAULT_DB;
            connection = DriverManager.getConnection(url);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            System.err.println("db.DatabaseManager Error: " + e.getMessage());
        }
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(false);
        }
        return instance;
    }
    
    public static synchronized DatabaseManager getInMemoryInstance() {
        if (inMemoryInstance == null) {
            inMemoryInstance = new DatabaseManager(true);
        }
        return inMemoryInstance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public boolean initializeSchema() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("schema.sql");
            if (is == null) {
                System.err.println("db.DatabaseManager Error: schema.sql not found");
                return false;
            }
            
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
            
            Statement stmt = connection.createStatement();
            for (String statement : sql.split(";")) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
            stmt.close();
            return true;
        } catch (Exception e) {
            System.err.println("db.DatabaseManager Error: " + e.getMessage());
            return false;
        }
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println("db.DatabaseManager Error: " + e.getMessage());
        }
    }
    
    public boolean isInMemory() {
        return isInMemory;
    }
    
    public static void resetInstances() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
        if (inMemoryInstance != null) {
            inMemoryInstance.close();
            inMemoryInstance = null;
        }
    }
}
