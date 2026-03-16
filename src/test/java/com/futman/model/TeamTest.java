package com.futman.model;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class TeamTest {
	static Country country = new Country("United States", "USA");
	static Team team1 = new Team("Test", "TE", country);
	static Player player1 = new Player("Player", "1", "", LocalDate.of(2000, 1, 1), Player.Position.ATT, 80, 80, 80,
			80, 80, 80);
	static Player player2 = new Player("Player", "2", "", LocalDate.of(2001, 1, 1), Player.Position.MID, 75, 75, 75,
			75, 75, 75);
	static Player player3 = new Player("Player", "3", "", LocalDate.of(2002, 3, 1), Player.Position.DEF, 70, 70, 70,
			70, 70, 70);
	static Player player4 = new Player("Player", "4", "", LocalDate.of(2003, 1, 2), Player.Position.GK, 65, 65, 65,
			65, 65, 65);
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
		team1.clearStartingXI();
		team1.setFormation(Formation.F_4_4_2);
	}

	@Test
	void test_getID() {
		assert team1.getID() > 0;
		assert team2.getID() > 0;
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
		team1.removePlayer(player1);
		team1.removePlayer(player2);
		team1.removePlayer(player3);
		team1.removePlayer(player4);

		assert team1.getAttOverall() == 1;
		assert team1.getMidOverall() == 1;
		assert team1.getDefOverall() == 1;
		assert team1.getGKOverall() == 1;
		assert team1.getOverall() == 1;

		team1.addPlayer(player1);

		assert team1.getAttOverall() == 80;
		assert team1.getMidOverall() == 1;
		assert team1.getDefOverall() == 1;
		assert team1.getGKOverall() == 1;
	}

	@Test
	@Order(1)
	void test_findPlayer() {
		assert team1.findPlayer(player1) >= 0;
		assert team2.findPlayer(player1) < 0;
	}

	@Test
	@Order(2)
	void test_addPlayer() {
		assert team1.getPlayerCount() >= 1;
		assert !(team1.addPlayer(player1));
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

	@Test
	void test_goals() {
		assert team1.getSeasonGoals() == 0;
		assert team1.addGoals(5);
		assert team1.getSeasonGoals() == 5;
		assert team1.resetGoals();
		assert team1.getSeasonGoals() == 0;
	}

	@Test
	void test_goalsConceded() {
		assert team1.getGoalsConceded() == 0;
		assert team1.addGoalsConceded(3);
		assert team1.getGoalsConceded() == 3;
		assert team1.addGoalsConceded(2);
		assert team1.getGoalsConceded() == 5;
	}

	@Test
	void test_isValid() {
		assert team1.isValid();
	}

	@Test
	void test_getFormation_default_returns4_4_2() {
		assert team1.getFormation() == Formation.F_4_4_2;
	}

	@Test
	void test_setFormation_valid_setsFormation() {
		assert team1.setFormation(Formation.F_4_3_3);
		assert team1.getFormation() == Formation.F_4_3_3;
		team1.setFormation(Formation.F_4_4_2);
	}

	@Test
	void test_setFormation_null_returnsFalse() {
		assert !team1.setFormation(null);
	}

	@Test
	void test_getStartingXI_initiallyEmpty() {
		Team team3 = new Team("Test3", "TE3", country);
		assert team3.getStarterCount() == 0;
	}

	@Test
	void test_setStarter_validPlayer_addsToSlot() {
		assert team1.setStarter(player1, 0);
		assert team1.getStarter(0).equals(player1);
		assert team1.getStarterCount() == 1;
	}

	@Test
	void test_setStarter_nullPlayer_returnsFalse() {
		assert !team1.setStarter(null, 0);
	}

	@Test
	void test_setStarter_invalidSlot_returnsFalse() {
		assert !team1.setStarter(player1, -1);
		assert !team1.setStarter(player1, 11);
		assert !team1.setStarter(player1, 100);
	}

	@Test
	void test_setStarter_playerNotOnTeam_returnsFalse() {
		Player outsider = new Player("Not", "OnTeam", "", LocalDate.of(2000, 1, 1), Player.Position.ATT, 50, 50, 50,
				50, 50, 50);
		assert !team1.setStarter(outsider, 0);
	}

	@Test
	void test_removeStarter_validSlot_removesPlayer() {
		team1.setStarter(player1, 0);
		assert team1.removeStarter(0);
		assert team1.getStarter(0) == null;
		assert team1.getStarterCount() == 0;
	}

	@Test
	void test_removeStarter_emptySlot_returnsFalse() {
		assert !team1.removeStarter(0);
	}

	@Test
	void test_removeStarter_invalidSlot_returnsFalse() {
		assert !team1.removeStarter(-1);
		assert !team1.removeStarter(11);
	}

	@Test
	void test_clearStartingXI_clearsAllSlots() {
		team1.setStarter(player1, 0);
		team1.setStarter(player1, 1);
		assert team1.getStarterCount() == 2;
		assert team1.clearStartingXI();
		assert team1.getStarterCount() == 0;
		for (int i = 0; i < 11; i++) {
			assert team1.getStarter(i) == null;
		}
	}

	@Test
	void test_countStartersByPosition_countsCorrectly() {
		team1.addPlayer(player2);
		team1.addPlayer(player3);
		team1.addPlayer(player4);

		team1.setStarter(player1, 0);
		team1.setStarter(player2, 1);
		team1.setStarter(player3, 2);
		team1.setStarter(player4, 3);

		assert team1.countStartersByPosition(Player.Position.ATT) == 1;
		assert team1.countStartersByPosition(Player.Position.MID) == 1;
		assert team1.countStartersByPosition(Player.Position.DEF) == 1;
		assert team1.countStartersByPosition(Player.Position.GK) == 1;
	}

	@Test
	void test_isValidLineup_only10Players_returnsFalse() {
		for (int i = 0; i < 10; i++) {
			team1.setStarter(player1, i);
		}
		assert !team1.isValidLineup();
	}

	@Test
	void test_isValidLineup_11PlayersNoGK_returnsFalse() {
		team1.addPlayer(player2);
		team1.addPlayer(player3);

		for (int i = 0; i < 11; i++) {
			if (i < 4)
				team1.setStarter(player1, i);
			else if (i < 8)
				team1.setStarter(player2, i);
			else
				team1.setStarter(player3, i);
		}
		assert !team1.isValidLineup();
	}

	@Test
	void test_isValidLineup_11PlayersWithGK_wrongFormation_returnsFalse() {
		team1.addPlayer(player2);
		team1.addPlayer(player3);
		team1.addPlayer(player4);

		team1.setFormation(Formation.F_4_3_3);

		team1.setStarter(player4, 0);
		for (int i = 0; i < 4; i++) {
			team1.setStarter(player3, i + 1);
		}
		for (int i = 0; i < 4; i++) {
			team1.setStarter(player2, i + 5);
		}
		team1.setStarter(player1, 9);
		team1.setStarter(player1, 10);

		assert !team1.isValidLineup();
	}

	@Test
	void test_isValidLineup_validLineup_returnsTrue() {
		Player att1 = new Player("Att", "1", "", LocalDate.of(2000, 1, 1), Player.Position.ATT, 70, 70, 70, 70, 70, 70);
		Player att2 = new Player("Att", "2", "", LocalDate.of(2000, 1, 1), Player.Position.ATT, 70, 70, 70, 70, 70, 70);
		Player mid1 = new Player("Mid", "1", "", LocalDate.of(2000, 1, 1), Player.Position.MID, 70, 70, 70, 70, 70, 70);
		Player mid2 = new Player("Mid", "2", "", LocalDate.of(2000, 1, 1), Player.Position.MID, 70, 70, 70, 70, 70, 70);
		Player mid3 = new Player("Mid", "3", "", LocalDate.of(2000, 1, 1), Player.Position.MID, 70, 70, 70, 70, 70, 70);
		Player mid4 = new Player("Mid", "4", "", LocalDate.of(2000, 1, 1), Player.Position.MID, 70, 70, 70, 70, 70, 70);
		Player def1 = new Player("Def", "1", "", LocalDate.of(2000, 1, 1), Player.Position.DEF, 70, 70, 70, 70, 70, 70);
		Player def2 = new Player("Def", "2", "", LocalDate.of(2000, 1, 1), Player.Position.DEF, 70, 70, 70, 70, 70, 70);
		Player def3 = new Player("Def", "3", "", LocalDate.of(2000, 1, 1), Player.Position.DEF, 70, 70, 70, 70, 70, 70);
		Player def4 = new Player("Def", "4", "", LocalDate.of(2000, 1, 1), Player.Position.DEF, 70, 70, 70, 70, 70, 70);
		Player gk1 = new Player("GK", "1", "", LocalDate.of(2000, 1, 1), Player.Position.GK, 70, 70, 70, 70, 70, 70);

		Team team3 = new Team("Test3", "TE3", country);
		team3.addPlayer(att1);
		team3.addPlayer(att2);
		team3.addPlayer(mid1);
		team3.addPlayer(mid2);
		team3.addPlayer(mid3);
		team3.addPlayer(mid4);
		team3.addPlayer(def1);
		team3.addPlayer(def2);
		team3.addPlayer(def3);
		team3.addPlayer(def4);
		team3.addPlayer(gk1);

		team3.setFormation(Formation.F_4_4_2);

		team3.setStarter(gk1, 0);
		team3.setStarter(def1, 1);
		team3.setStarter(def2, 2);
		team3.setStarter(def3, 3);
		team3.setStarter(def4, 4);
		team3.setStarter(mid1, 5);
		team3.setStarter(mid2, 6);
		team3.setStarter(mid3, 7);
		team3.setStarter(mid4, 8);
		team3.setStarter(att1, 9);
		team3.setStarter(att2, 10);

		assert team3.isValidLineup();
	}
}
