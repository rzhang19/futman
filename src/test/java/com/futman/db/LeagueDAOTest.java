package com.futman.db;

import com.futman.model.Country;
import com.futman.model.League;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class LeagueDAOTest {
    static DatabaseManager db;
    static LeagueDAO dao;
    static CountryDAO countryDAO;
    static Country testCountry;
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        db = DatabaseManager.getInMemoryInstance();
        db.initializeSchema();
        dao = new LeagueDAO(db.getConnection());
        countryDAO = new CountryDAO(db.getConnection());
        
        testCountry = new Country("England", "ENG");
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
    void test_save_validLeague_returnsTrue() {
        League league = new League("Premier League", testCountry, 1, 20);
        boolean result = dao.save(league);
        assert result;
        assert league.getID() > 0;
    }
    
    @Test
    void test_save_nullLeague_returnsFalse() {
        boolean result = dao.save(null);
        assert !result;
    }
    
    @Test
    void test_findById_existingId_returnsLeague() {
        League league = new League("Championship", testCountry, 2, 24);
        dao.save(league);
        
        League found = dao.findById(league.getID());
        assert found != null;
        assert found.getName().equals("Championship");
        assert found.getTier() == 2;
    }
    
    @Test
    void test_findById_nonExistingId_returnsNull() {
        League found = dao.findById(99999);
        assert found == null;
    }
    
    @Test
    void test_findAll_multipleLeagues_returnsAll() {
        League l1 = new League("League One", testCountry, 3, 24);
        League l2 = new League("League Two", testCountry, 4, 24);
        dao.save(l1);
        dao.save(l2);
        
        List<League> leagues = dao.findAll();
        assert leagues != null;
        assert leagues.size() >= 2;
    }
    
    @Test
    void test_update_existingLeague_returnsTrue() {
        League league = new League("Test League", testCountry, 5, 20);
        dao.save(league);
        
        League updated = new League("Updated League", testCountry, 5, 20);
        updated.setID(league.getID());
        
        boolean result = dao.update(updated);
        assert result;
        
        League found = dao.findById(league.getID());
        assert found.getName().equals("Updated League");
    }
    
    @Test
    void test_update_nonExistingLeague_returnsFalse() {
        League league = new League("Unknown", testCountry, 99, 20);
        league.setID(99999);
        
        boolean result = dao.update(league);
        assert !result;
    }
    
    @Test
    void test_delete_existingLeague_returnsTrue() {
        League league = new League("ToDelete", testCountry, 6, 16);
        dao.save(league);
        
        boolean result = dao.delete(league.getID());
        assert result;
        
        League found = dao.findById(league.getID());
        assert found == null;
    }
    
    @Test
    void test_delete_nonExistingLeague_returnsFalse() {
        boolean result = dao.delete(99999);
        assert !result;
    }
    
    @Test
    void test_findByCountryId_returnsLeagues() {
        League league = new League("National League", testCountry, 7, 24);
        dao.save(league);
        
        List<League> leagues = dao.findByCountryId(testCountry.getID());
        assert leagues != null;
        assert leagues.size() >= 1;
    }
}
