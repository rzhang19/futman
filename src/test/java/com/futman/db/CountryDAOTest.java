package com.futman.db;

import com.futman.model.Country;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CountryDAOTest {
    static DatabaseManager db;
    static CountryDAO dao;
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        db = DatabaseManager.getInMemoryInstance();
        db.initializeSchema();
        dao = new CountryDAO(db.getConnection());
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        DatabaseManager.resetInstances();
    }
    
    @BeforeEach
    void setUp() throws Exception {
    }
    
    @Test
    void test_save_validCountry_returnsTrue() {
        Country country = new Country("England", "ENG");
        boolean result = dao.save(country);
        assert result;
        assert country.getID() > 0;
    }
    
    @Test
    void test_save_nullCountry_returnsFalse() {
        boolean result = dao.save(null);
        assert !result;
    }
    
    @Test
    void test_findById_existingId_returnsCountry() {
        Country country = new Country("Spain", "ESP");
        dao.save(country);
        
        Country found = dao.findById(country.getID());
        assert found != null;
        assert found.getName().equals("Spain");
        assert found.getCode().equals("ESP");
    }
    
    @Test
    void test_findById_nonExistingId_returnsNull() {
        Country found = dao.findById(99999);
        assert found == null;
    }
    
    @Test
    void test_findAll_multipleCountries_returnsAll() {
        Country c1 = new Country("Germany", "GER");
        Country c2 = new Country("France", "FRA");
        dao.save(c1);
        dao.save(c2);
        
        List<Country> countries = dao.findAll();
        assert countries != null;
        assert countries.size() >= 2;
    }
    
    @Test
    void test_update_existingCountry_returnsTrue() {
        Country country = new Country("Italy", "ITA");
        dao.save(country);
        
        Country updated = new Country("Italia", "ITA");
        updated.setID(country.getID());
        
        boolean result = dao.update(updated);
        assert result;
        
        Country found = dao.findById(country.getID());
        assert found.getName().equals("Italia");
    }
    
    @Test
    void test_update_nonExistingCountry_returnsFalse() {
        Country country = new Country("Unknown", "UNK");
        country.setID(99999);
        
        boolean result = dao.update(country);
        assert !result;
    }
    
    @Test
    void test_delete_existingCountry_returnsTrue() {
        Country country = new Country("Portugal", "POR");
        dao.save(country);
        
        boolean result = dao.delete(country.getID());
        assert result;
        
        Country found = dao.findById(country.getID());
        assert found == null;
    }
    
    @Test
    void test_delete_nonExistingCountry_returnsFalse() {
        boolean result = dao.delete(99999);
        assert !result;
    }
    
    @Test
    void test_findByCode_existingCode_returnsCountry() {
        Country country = new Country("Netherlands", "NED");
        dao.save(country);
        
        Country found = dao.findByCode("NED");
        assert found != null;
        assert found.getName().equals("Netherlands");
    }
    
    @Test
    void test_findByCode_nonExistingCode_returnsNull() {
        Country found = dao.findByCode("XXX");
        assert found == null;
    }
}
