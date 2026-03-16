package com.futman.db;

import com.futman.model.Country;
import com.futman.model.Player;
import com.futman.model.Team;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class PlayerDAOTest {
    static DatabaseManager db;
    static PlayerDAO dao;
    static CountryDAO countryDAO;
    static TeamDAO teamDAO;
    static Country testCountry;
    static Team testTeam;
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        db = DatabaseManager.getInMemoryInstance();
        db.initializeSchema();
        dao = new PlayerDAO(db.getConnection());
        countryDAO = new CountryDAO(db.getConnection());
        teamDAO = new TeamDAO(db.getConnection());
        
        testCountry = new Country("Italy", "ITA");
        countryDAO.save(testCountry);
        
        testTeam = new Team("Juventus", "JUV", testCountry);
        teamDAO.save(testTeam);
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        DatabaseManager.resetInstances();
    }
    
    @BeforeEach
    void setUp() throws Exception {
    }
    
    @Test
    void test_save_validPlayer_returnsTrue() {
        Player player = new Player("Cristiano", "Ronaldo", "", LocalDate.of(1985, 2, 5), 
                                   Player.Position.ATT, 85, 90, 70, 80, 75, 65);
        player.addToTeam(testTeam);
        boolean result = dao.save(player);
        assert result;
        assert player.getID() > 0;
    }
    
    @Test
    void test_save_nullPlayer_returnsFalse() {
        boolean result = dao.save(null);
        assert !result;
    }
    
    @Test
    void test_findById_existingId_returnsPlayer() {
        Player player = new Player("Paolo", "Maldini", "", LocalDate.of(1968, 6, 26), 
                                   Player.Position.DEF, 70, 60, 90, 85, 80, 85);
        player.addToTeam(testTeam);
        dao.save(player);
        
        Player found = dao.findById(player.getID());
        assert found != null;
        assert found.getFirstName().equals("Paolo");
        assert found.getLastName().equals("Maldini");
    }
    
    @Test
    void test_findById_nonExistingId_returnsNull() {
        Player found = dao.findById(99999);
        assert found == null;
    }
    
    @Test
    void test_findAll_multiplePlayers_returnsAll() {
        Player p1 = new Player("Francesco", "Totti", "", LocalDate.of(1979, 9, 27), 
                               Player.Position.ATT, 75, 85, 65, 80, 85, 60);
        Player p2 = new Player("Andrea", "Pirlo", "", LocalDate.of(1979, 5, 19), 
                               Player.Position.MID, 65, 80, 70, 90, 80, 60);
        p1.addToTeam(testTeam);
        p2.addToTeam(testTeam);
        dao.save(p1);
        dao.save(p2);
        
        List<Player> players = dao.findAll();
        assert players != null;
        assert players.size() >= 2;
    }
    
    @Test
    void test_update_existingPlayer_returnsTrue() {
        Player player = new Player("Test", "Player", "", LocalDate.of(1990, 1, 1), 
                                   Player.Position.MID, 70, 70, 70, 70, 70, 70);
        player.addToTeam(testTeam);
        dao.save(player);
        
        Player updated = new Player("Updated", "Player", "", LocalDate.of(1990, 1, 1), 
                                    Player.Position.MID, 75, 75, 75, 75, 75, 75);
        updated.setID(player.getID());
        updated.addToTeam(testTeam);
        
        boolean result = dao.update(updated);
        assert result;
        
        Player found = dao.findById(player.getID());
        assert found.getFirstName().equals("Updated");
    }
    
    @Test
    void test_update_nonExistingPlayer_returnsFalse() {
        Player player = new Player("Unknown", "Player", "", LocalDate.of(1990, 1, 1), 
                                   Player.Position.GK, 70, 70, 70, 70, 70, 70);
        player.setID(99999);
        player.addToTeam(testTeam);
        
        boolean result = dao.update(player);
        assert !result;
    }
    
    @Test
    void test_delete_existingPlayer_returnsTrue() {
        Player player = new Player("ToDelete", "Player", "", LocalDate.of(1995, 6, 15), 
                                   Player.Position.GK, 70, 70, 70, 70, 70, 70);
        player.addToTeam(testTeam);
        dao.save(player);
        
        boolean result = dao.delete(player.getID());
        assert result;
        
        Player found = dao.findById(player.getID());
        assert found == null;
    }
    
    @Test
    void test_delete_nonExistingPlayer_returnsFalse() {
        boolean result = dao.delete(99999);
        assert !result;
    }
    
    @Test
    void test_findByTeamId_returnsPlayers() {
        Player player = new Player("Gianluigi", "Buffon", "", LocalDate.of(1978, 1, 28), 
                                   Player.Position.GK, 60, 60, 60, 80, 90, 90);
        player.addToTeam(testTeam);
        dao.save(player);
        
        List<Player> players = dao.findByTeamId(testTeam.getID());
        assert players != null;
        assert players.size() >= 1;
    }
}
