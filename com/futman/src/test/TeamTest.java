package com.futman.src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Country;
import com.futman.src.main.Player;
import com.futman.src.main.Player.Position;
import com.futman.src.main.Team;

class TeamTest {
	static Country country = new Country("United States", "USA");
	static Team team1 = new Team("Test", "TE", country);
	static Player player1 = new Player("Player", "1", "", LocalDate.of(2000, 1, 1), Position.ATT, 80, 80, 80, 80, 80, 80);
	static Player player2 = new Player("Player", "2", "", LocalDate.of(2001, 1, 1), Position.MID, 75, 75, 75, 75, 75, 75);
	static Player player3 = new Player("Player", "3", "", LocalDate.of(2002, 3, 1), Position.DEF, 70, 70, 70, 70, 70, 70);
	static Player player4 = new Player("Player", "4", "", LocalDate.of(2003, 1, 2), Position.GK, 65, 65, 65, 65, 65, 65);
	static Team team2 = new Team("Test2", "TE2", country);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		team1.addPlayer(player1);
	}

	@AfterEach
	void tearDown() throws Exception {
		team1.removePlayer(player1);
	}

	@Test
	void test_getID() {
		assert team1.getID() == 1;
		assert team2.getID() == 2;
	}
	
	@Test
	void test_equals() {
		Team copy = team1;
		assert team1.equals(copy);
	}
	
	@Test
	void test_toString() {
		assert team1.toString().equals("Test (USA)");
		assert team2.toString().equals("Test2 (USA)");
	}
	
	@Test
	void test_getName() {
		assert team1.getName().equals("Test");
		assert team2.getName().equals("Test2");
	}
	
	@Test
	void test_getShortName() {
		assert team1.getShortName().equals("TE");
		assert team2.getShortName().equals("TE2");
	}
	
	@Test
	void test_seasonalStats() {
		assert team1.getSeasonWins() == 0;
		assert team1.getSeasonDraws() == 0;
		assert team1.getSeasonLosses() == 0;
	}
	
	@Test
	void test_overalls() {
		assert team1.getAttOverall() == 0;
		assert team1.getMidOverall() == 0;
		assert team1.getDefOverall() == 0;
		assert team1.getGKOverall() == 0;
		assert team1.getOverall() == 0;
	}
	
	@Test
	@Order(1)
	void test_findPlayer() {
		assert team1.findPlayer(player1) >= 0;
		assert team1.findPlayer(team1.getPlayers(), player1) >= 0;
	}
	
	@Test
	@Order(2)
	void test_addPlayers() {
		assert team1.addPlayers(new Player[] {player2, player3});
		assert !(team1.addPlayers(new Player[] {player1, player2}));
	}
	
	@Test
	@Order(3)
	void test_getPlayerCount() {
		assert team1.getPlayerCount() == 1;
		assert team2.getPlayerCount() == 0;
	}
	
	@Test
	@Order(4)
	void test_getPlayers() {
		assert team1.getPlayers() != null;
	}
	
	@Test
	@Order(5)
	void test_removePlayer() {
		assert team1.removePlayer(player1);
		assert team1.getPlayerCount() == 0;
		assert !(team1.removePlayer(player1));
		assert team1.getPlayerCount() == 0;
	}
}
