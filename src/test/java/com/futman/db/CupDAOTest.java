package com.futman.db;

import com.futman.model.Country;
import com.futman.model.Cup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CupDAOTest {
    static DatabaseManager db;
    static CupDAO dao;
    static CountryDAO countryDAO;
    static Country testCountry;
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        db = DatabaseManager.getInMemoryInstance();
        db.initializeSchema();
        dao = new CupDAO(db.getConnection());
        countryDAO = new CountryDAO(db.getConnection());
        
        testCountry = new Country("Spain", "ESP");
        countryDAO.save(testCountry);
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        DatabaseManager.resetInstances();
    }
    
    @BeforeEach
    void setUp() throws Exception {
    }
    
    @Test
    void test_save_validCup_returnsTrue() {
        Cup cup = new Cup("Copa del Rey", testCountry, 64);
        boolean result = dao.save(cup);
        assert result;
        assert cup.getID() > 0;
    }
    
    @Test
    void test_save_nullCup_returnsFalse() {
        boolean result = dao.save(null);
        assert !result;
    }
    
    @Test
    void test_findById_existingId_returnsCup() {
        Cup cup = new Cup("FA Cup", testCountry, 64);
        dao.save(cup);
        
        Cup found = dao.findById(cup.getID());
        assert found != null;
        assert found.getName().equals("FA Cup");
    }
    
    @Test
    void test_findById_nonExistingId_returnsNull() {
        Cup found = dao.findById(99999);
        assert found == null;
    }
    
    @Test
    void test_findAll_multipleCups_returnsAll() {
        Cup c1 = new Cup("League Cup", testCountry, 32);
        Cup c2 = new Cup("Community Shield", testCountry, 2);
        dao.save(c1);
        dao.save(c2);
        
        List<Cup> cups = dao.findAll();
        assert cups != null;
        assert cups.size() >= 2;
    }
    
    @Test
    void test_update_existingCup_returnsTrue() {
        Cup cup = new Cup("Test Cup", testCountry, 16);
        dao.save(cup);
        
        Cup updated = new Cup("Updated Cup", testCountry, 16);
        updated.setID(cup.getID());
        
        boolean result = dao.update(updated);
        assert result;
        
        Cup found = dao.findById(cup.getID());
        assert found.getName().equals("Updated Cup");
    }
    
    @Test
    void test_update_nonExistingCup_returnsFalse() {
        Cup cup = new Cup("Unknown", testCountry, 8);
        cup.setID(99999);
        
        boolean result = dao.update(cup);
        assert !result;
    }
    
    @Test
    void test_delete_existingCup_returnsTrue() {
        Cup cup = new Cup("ToDelete", testCountry, 4);
        dao.save(cup);
        
        boolean result = dao.delete(cup.getID());
        assert result;
        
        Cup found = dao.findById(cup.getID());
        assert found == null;
    }
    
    @Test
    void test_delete_nonExistingCup_returnsFalse() {
        boolean result = dao.delete(99999);
        assert !result;
    }
    
    @Test
    void test_findByCountryId_returnsCups() {
        Cup cup = new Cup("Super Cup", testCountry, 2);
        dao.save(cup);
        
        List<Cup> cups = dao.findByCountryId(testCountry.getID());
        assert cups != null;
        assert cups.size() >= 1;
    }
}
