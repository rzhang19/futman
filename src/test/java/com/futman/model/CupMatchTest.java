package com.futman.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CupMatchTest {
	static Country country = new Country("United States", "USA");
	static Team team1 = new Team("Test1", "TE1", country);
	static Team team2 = new Team("Test2", "TE2", country);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test_constructor_withTeams() {
		CupMatch match = new CupMatch(team1, team2, country);
		assert match.getTeam1().equals(team1);
		assert match.getTeam2().equals(team2);
	}

	@Test
	void test_isValid() {
		CupMatch match = new CupMatch(team1, team2, country);
		assert match.isValid();
	}
}
