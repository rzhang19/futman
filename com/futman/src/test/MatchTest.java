package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Country;
import com.futman.src.main.CupMatch;
import com.futman.src.main.LeagueMatch;
import com.futman.src.main.Match;
import com.futman.src.main.Team;

class MatchTest {
	static Country country = new Country("United States", "USA");
	static Team team1 = new Team("Team1", "t1", country);
	static Team team2 = new Team("Team2", "t2", country);
	static Match match1;
	static Match match2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		match1 = new LeagueMatch(team1, team2, country);
		match2 = new CupMatch(team2, team1, country, false);
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
	void test_getID() {
		assert match1.getID() > 0;
		assert match2.getID() > 0;
	}
	
	@Test
	void test_equals() {
		Match match3 = match1;
		assert match3.equals(match1);
		assert match1.equals(match3);
		assert !match1.equals(match2);
		assert !match2.equals(match3);
	}
	
	@Test
	void test_toString() {
		assert match1.toString().equals("Team1 vs Team2");
		assert match2.toString().equals("Team2 vs Team1");
	}
	
	@Test
	void test_getTeams() {
		assert match1.getTeam1().equals(team1);
		assert match1.getTeam2().equals(team2);
		assert match2.getTeam1().equals(team2);
		assert match2.getTeam2().equals(team1);
	}
	
	@Test
	void test_getScores() {
		assert match1.getScore1() == 0;
		assert match1.getScore2() == 0;
		assert match2.getScore1() == 0;
		assert match2.getScore2() == 0;
	}
	
	@Test
	void test_isValid() {
		assert match1.isValid();
		assert match2.isValid();
		
		Match match3 = new LeagueMatch(team1, team2, null);
		assert !(match3.isValid());
		
		Match match4 = new LeagueMatch(team1, null, country);
		assert !(match4.isValid());
		
		Match match5 = new LeagueMatch(null, team2, country);
		assert !(match5.isValid());
	}
}
