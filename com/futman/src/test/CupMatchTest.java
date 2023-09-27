package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Country;
import com.futman.src.main.CupMatch;
import com.futman.src.main.Match;
import com.futman.src.main.Team;

class CupMatchTest {
	static Country country = new Country("United States", "USA");
	static Team team1 = new Team("Team1", "T1", country);
	static Team team2 = new Team("Team2", "T2", country);
	static Match cupMatch;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cupMatch = new CupMatch(team1, team2, country);
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
	void test_runMatch() {
		assert cupMatch.runMatch();
	}

}
