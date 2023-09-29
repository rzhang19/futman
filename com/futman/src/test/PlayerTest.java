package com.futman.src.test;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Player;
import com.futman.src.main.Player.Position;
import com.futman.src.main.Team;
import com.futman.src.main.Country;

class PlayerTest {
	static Player player1 = new Player("test", "player", "", LocalDate.of(1999, 12, 1), Position.ATT, 80, 85, 65, 75, 70, 55 );
	static Player player2 = new Player();
	static Player player3 = new Player("test", "player", "tests", LocalDate.of(1989, 2, 3));
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		assert(player1.resetGoals());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test_getID() {
		assert(player1.getID() == 1);
		assert(player2.getID() == 2);
	}
	
	@Test
	void test_equals() {
		Player copy = player1;
		
		assert(player1.equals(copy));
		assert(!player2.equals(copy));
	}
	
	@Test
	void test_toString() {
		assert player1.toString().equals("test player (ATT)");
		assert player2.toString().equals("  (NONE)");
		assert player3.toString().equals("tests (NONE)");
	}

	@Test
	void test_getFirstName() {
		assert player1.getFirstName().equals("test");
		assert player2.getFirstName().equals("");
	}
	
	@Test
	void test_getLastName() {
		assert player1.getLastName().equals("player");
		assert player2.getLastName().equals("");
	}
	
	@Test
	void test_getNickName() {
		assert player1.getNickName().equals("");
		assert player2.getNickName().equals("");
		assert player3.getNickName().equals("tests");
	}
	
	@Test
	void test_getName() {
		assert player1.getName().equals("test player");
		assert player2.getName().equals(" ");
		assert player3.getName().equals("tests");
	}
	
	@Test
	void test_getDOB() {
		assert player1.getDOB().equals(LocalDate.of(1999, 12, 1));
		assert player2.getDOB().equals(LocalDate.of(1900, 1, 1));
		assert player3.getDOB().equals(LocalDate.of(1989, 2, 3));
	}
	
	@Test
	void test_getPositionEnum() {
		assert player1.getPositionEnum() == Position.ATT;
		assert player2.getPositionEnum() == Position.NONE;
		assert player3.getPositionEnum() == Position.NONE;
	}
	
	@Test
	void test_getPosition() {
		assert player1.getPosition().equals("ATT");
		assert player2.getPosition().equals("NONE");
		assert player3.getPosition().equals("NONE");
	}
	
	@Test
	void test_getSeasonGoals() {
		assert player1.getSeasonGoals() == 0;
	}
	
	@Test
	void test_getSeasonAssists() {
		assert player1.getSeasonAssists() == 0;
	}
	
	@Test
	void test_getSeasonSaves() {
		assert player1.getSeasonSaves() == 0;
	}
	
	@Test
	void test_getSeasonTackles() {
		assert player1.getSeasonTackles() == 0;
	}
	
	@Test
	void test_getSpeed() {
		assert player1.getSpeed() == 80;
		assert player2.getSpeed() == 50;
		assert player3.getSpeed() == 50;
	}
	
	@Test
	void test_getShooting() {
		assert player1.getShooting() == 85;
		assert player2.getShooting() == 50;
	}
	
	@Test
	void test_getTackling() {
		assert player1.getTackling() == 65;
		assert player2.getTackling() == 50;
	}
	
	@Test
	void test_getPassing() {
		assert player1.getPassing() == 75;
		assert player2.getPassing() == 50;
	}
	
	@Test
	void test_getReactions() {
		assert player1.getReactions() == 70;
		assert player2.getReactions() == 50;
	}
	
	@Test
	void test_getBlocking() {
		assert player1.getBlocking() == 55;
		assert player2.getBlocking() == 50;
	}
	
	@Test
	void test_getOverall() {
		assert player1.getOverall() == 77;
		assert player2.getOverall() == 50;
	}
	
	@Test
	void test_addGoals() {
		assert(player1.getSeasonGoals() == 0);
		assert(player1.addGoals(1));
		assert(player1.getSeasonGoals() == 1);
		assert(player1.addGoals(5));
		assert(player1.getSeasonGoals() == 6);
		assert(player1.resetGoals());
		assert(player1.getSeasonGoals() == 0);
	}
	
	@Test
	void test_teamMethods() {
		assert player2.getTeam() == null;
		assert player2.isFreeAgent();
	}
	
	@Test
	void test_isOnTeam() {
		Team team1 = new Team("Test", "TE", new Country("United States", "USA"));
		Team team2 = new Team("Test2", "TE2", new Country("United States", "USA"));
		assert team1.addPlayer(player1);
		
		assert player1.isOnTeam(team1);
		assert !player1.isOnTeam(team2);
		
		assert !player2.isOnTeam(team1);
		assert !player2.isOnTeam(team2);
	}
	
	@Test
	void test_playerValue() {
		assert player1.calculateValue();
		assert player1.getValue() == 0;
		
		assert player1.setValue(5000);
		assert player1.getValue() == 5000;
	}
}
