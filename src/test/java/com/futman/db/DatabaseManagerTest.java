package com.futman.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DatabaseManagerTest {
    
    @AfterEach
    void tearDown() throws Exception {
        DatabaseManager.resetInstances();
    }
    
    @Test
    void test_getInstance_returnsNonNull() {
        DatabaseManager db = DatabaseManager.getInstance();
        assert db != null;
    }
    
    @Test
    void test_getInMemoryInstance_returnsNonNull() {
        DatabaseManager db = DatabaseManager.getInMemoryInstance();
        assert db != null;
    }
    
    @Test
    void test_getInMemoryInstance_isInMemoryReturnsTrue() {
        DatabaseManager db = DatabaseManager.getInMemoryInstance();
        assert db.isInMemory();
    }
    
    @Test
    void test_getInstance_isInMemoryReturnsFalse() {
        DatabaseManager db = DatabaseManager.getInstance();
        assert !db.isInMemory();
    }
    
    @Test
    void test_getConnection_returnsNonNull() {
        DatabaseManager db = DatabaseManager.getInMemoryInstance();
        Connection conn = db.getConnection();
        assert conn != null;
    }
    
    @Test
    void test_initializeSchema_returnsTrue() {
        DatabaseManager db = DatabaseManager.getInMemoryInstance();
        boolean result = db.initializeSchema();
        assert result;
    }
    
    @Test
    void test_close_closesConnection() {
        DatabaseManager db = DatabaseManager.getInMemoryInstance();
        db.close();
        try {
            assert db.getConnection().isClosed();
        } catch (Exception e) {
            assert true;
        }
    }
}
