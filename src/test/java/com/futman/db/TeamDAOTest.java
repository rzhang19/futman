package com.futman.db;

import com.futman.model.Country;
import com.futman.model.League;
import com.futman.model.Team;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TeamDAOTest {
    static DatabaseManager db;
    static TeamDAO dao;
    static CountryDAO countryDAO;
    static LeagueDAO leagueDAO;
    static Country testCountry;
    static League testLeague;
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        db = DatabaseManager.getInMemoryInstance();
        db.initializeSchema();
        dao = new TeamDAO(db.getConnection());
        countryDAO = new CountryDAO(db.getConnection());
        leagueDAO = new LeagueDAO(db.getConnection());
        
        testCountry = new Country("Germany", "GER");
        countryDAO.save(testCountry);
        
        testLeague = new League("Bundesliga", testCountry, 1, 18);
        leagueDAO.save(testLeague);
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        DatabaseManager.resetInstances();
    }
    
    @BeforeEach
    void setUp() throws Exception {
    }
    
    @Test
    void test_save_validTeam_returnsTrue() {
        Team team = new Team("Bayern Munich", "BAY", testCountry);
        boolean result = dao.save(team);
        assert result;
        assert team.getID() > 0;
    }
    
    @Test
    void test_save_nullTeam_returnsFalse() {
        boolean result = dao.save(null);
        assert !result;
    }
    
    @Test
    void test_findById_existingId_returnsTeam() {
        Team team = new Team("Borussia Dortmund", "BVB", testCountry);
        dao.save(team);
        
        Team found = dao.findById(team.getID());
        assert found != null;
        assert found.getName().equals("Borussia Dortmund");
        assert found.getShortName().equals("BVB");
    }
    
    @Test
    void test_findById_nonExistingId_returnsNull() {
        Team found = dao.findById(99999);
        assert found == null;
    }
    
    @Test
    void test_findAll_multipleTeams_returnsAll() {
        Team t1 = new Team("Schalke 04", "S04", testCountry);
        Team t2 = new Team("Hamburger SV", "HSV", testCountry);
        dao.save(t1);
        dao.save(t2);
        
        List<Team> teams = dao.findAll();
        assert teams != null;
        assert teams.size() >= 2;
    }
    
    @Test
    void test_update_existingTeam_returnsTrue() {
        Team team = new Team("Test Team", "TT", testCountry);
        dao.save(team);
        
        Team updated = new Team("Updated Team", "UT", testCountry);
        updated.setID(team.getID());
        
        boolean result = dao.update(updated);
        assert result;
        
        Team found = dao.findById(team.getID());
        assert found.getName().equals("Updated Team");
    }
    
    @Test
    void test_update_nonExistingTeam_returnsFalse() {
        Team team = new Team("Unknown", "UN", testCountry);
        team.setID(99999);
        
        boolean result = dao.update(team);
        assert !result;
    }
    
    @Test
    void test_delete_existingTeam_returnsTrue() {
        Team team = new Team("ToDelete FC", "TDF", testCountry);
        dao.save(team);
        
        boolean result = dao.delete(team.getID());
        assert result;
        
        Team found = dao.findById(team.getID());
        assert found == null;
    }
    
    @Test
    void test_delete_nonExistingTeam_returnsFalse() {
        boolean result = dao.delete(99999);
        assert !result;
    }
    
    @Test
    void test_findByCountryId_returnsTeams() {
        Team team = new Team("Werder Bremen", "SVW", testCountry);
        dao.save(team);
        
        List<Team> teams = dao.findByCountryId(testCountry.getID());
        assert teams != null;
        assert teams.size() >= 1;
    }
}
