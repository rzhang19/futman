package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Competition;
import com.futman.src.main.Country;
import com.futman.src.main.Cup;
import com.futman.src.main.League;
import com.futman.src.main.Team;

class CompetitionTest {
	static Country country = new Country("United States", "USA");
	static Competition comp1 = new Cup("cup1", country, 64);
	static Competition comp2 = new League("league1", country, 20);
	static Competition comp3 = new Cup("cup2", country, 32);
	static Competition comp4 = new League("league2", country, 18);
	static Team team1 = new Team("test1", "TE", country);
	static Team team2 = new Team("test2", "TE2", country);

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
	void test_getID() {
		assert comp1.getID() == 1;
		assert comp2.getID() == 2;
		assert comp3.getID() == 3;
		assert comp4.getID() == 4;
	}
	
	@Test
	void test_getName() {
		assert comp1.getName().equals("cup1");
		assert comp2.getName().equals("league1");
		assert comp3.getName().equals("cup2");
		assert comp4.getName().equals("league2");
	}
	
	@Test
	void test_getYear() {
		assert comp1.getYear() == Competition.getStartingYear();
		assert comp1.nextYear();
		assert comp1.getYear() == Competition.getStartingYear() + 1;
	}
	
	@Test
	void test_getCountry() {
		assert comp1.getCountry().equals(country);
		assert comp2.getCountry().equals(country);
		assert comp3.getCountry().equals(country);
		assert comp4.getCountry().equals(country);
	}
	
	@Test
	void test_equals() {
		Competition newComp = comp1;
		assert newComp.equals(comp1);
		assert comp1.equals(newComp);
		assert !comp1.equals(comp2);
		assert !comp1.equals(comp3);
		assert !comp1.equals(comp4);
	}
	
	@Test
	void test_toString() {
		assert comp1.toString().equals("cup1 (United States)");
	}
	
	@Test
	void test_maxMatches() {
		assert comp1.getMaxMatchesPerSeason() == 0;
		assert comp2.getMaxMatchesPerSeason() == 0;
		assert comp3.getMaxMatchesPerSeason() == 0;
		assert comp4.getMaxMatchesPerSeason() == 0;
	}
	
	@Test
	void test_getEmptySeasons() {
		assert comp1.getSeasons().size() == 0;
	}
	
	@Test
	void test_teams() {
		assert comp1.getTeamCount() == 0;
		
		assert comp1.addTeams(new Team[] {team1, team2});
		assert comp1.findTeam(team1) == 0;
		assert comp1.findTeam(team2) == 1;
		assert comp1.getTeamCount() == 2;
		
		assert comp1.removeTeam(team1);
		assert comp1.getTeamCount() == 1;
		assert comp1.findTeam(team1) == -1;
		assert comp1.findTeam(team2) == 0;
	}
}
