package com.futman.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStateTest {
	static Country country;
	static League league;
	static Team team1;
	static Team team2;
	static Season season;
	static GameState gameState;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		country = new Country("Test", "USA");
		league = new League("Test League", country, 1, 8);
		team1 = new Team("Team1", "TE1", country);
		team2 = new Team("Team2", "TE2", country);
	}

	@BeforeEach
	void setUp() throws Exception {
		league.addTeam(team1);
		league.addTeam(team2);
		season = new Season(league);
		gameState = new GameState(team1, season);
	}

	@Test
	void test_constructor_setsManagedTeam() {
		assert gameState.getManagedTeam().equals(team1);
	}

	@Test
	void test_constructor_setsCurrentDayTo1() {
		assert gameState.getCurrentDay() == 1;
	}

	@Test
	void test_getCurrentSeason() {
		assert gameState.getCurrentSeason().equals(season);
	}

	@Test
	void test_getID() {
		assert gameState.getID() > 0;
	}

	@Test
	void test_advanceDay_incrementsDay() {
		int initialDay = gameState.getCurrentDay();
		gameState.advanceDay();
		assert gameState.getCurrentDay() == initialDay + 1;
	}

	@Test
	void test_hasMatchToday_noMatch_returnsFalse() {
		assert !gameState.hasMatchToday();
	}

	@Test
	void test_isSeasonComplete_notComplete_returnsFalse() {
		assert !gameState.isSeasonComplete();
	}

	@Test
	void test_isSeasonComplete_returnsTrue() {
		season.startSeason();
		season.completeSeason();
		assert gameState.isSeasonComplete();
	}

	@Test
	void test_getTodayMatch_noMatch_returnsNull() {
		assert gameState.getTodayMatch() == null;
	}

	@Test
	void test_isValid() {
		assert gameState.isValid();
	}
}
