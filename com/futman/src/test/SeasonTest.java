package com.futman.src.test;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.League;
import com.futman.src.main.Season;
import com.futman.src.main.Competition;
import com.futman.src.main.Country;
import com.futman.src.main.Team;

class SeasonTest {
   static Country country = new Country("United States", "USA");
   static Competition comp;;
   static Season season1;
   static Team[] teams = new Team[4];

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		comp = new League("Test League", country, 4);
		
		teams[0] = new Team("Team 1", "T1", country);
		teams[1] = new Team("Team 2", "T2", country);
		teams[2] = new Team("Team 3", "T3", country);
		teams[3] = new Team("Team 4", "T4", country);
		
		assert comp.addTeam(teams[0]);
		assert comp.addTeam(teams[1]);
		assert comp.addTeam(teams[2]);
		assert comp.addTeam(teams[3]);
		
		season1 = new Season(comp);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test_getID() {
		assert season1.getID() > 0;
	}

   @Test
   void test_equals() {
      Season copy = season1;
      assert season1.equals(copy);
   }
   
   @Test
   void test_toString() {
	   assert season1.toString().equals("Test League (" + comp.getYear() + ")");
   }
   
   @Test
   void test_getYear() {
	   assert season1.getYear() == Competition.getStartingYear();
   }
   
   @Test
   void test_emptyMatches() {
	   assert season1.getMatches().length == 6 || season1.getMatches()[0] != null;
	   assert season1.getMatchesCount() == 12;
   }
   
   @Test
   void test_seasonStarted() {
	   Season season3 = new Season(comp);
	   
	   assert !season3.getSeasonStarted();
	   assert !season3.getSeasonCompleted();
	   
	   assert season3.startSeason();
	   assert season3.getSeasonStarted();
	   assert !season3.getSeasonCompleted();
	   
	   assert season3.completeSeason();
	   assert !season3.getSeasonStarted();
	   assert season3.getSeasonCompleted();
   }
   
   @Test
   void test_seasonStartedFailTests() {
	   Season season4 = new Season(comp);
	   
	   assert !season4.getSeasonStarted();
	   assert !season4.getSeasonCompleted();
	   
	   assert !season4.completeSeason();
	   assert !season4.getSeasonStarted();
	   assert !season4.getSeasonCompleted();
	   
	   assert season4.startSeason();
	   assert !season4.startSeason();
	   assert season4.getSeasonStarted();
	   
	   assert !season4.startSeason();
	   assert season4.getSeasonStarted();
	   assert !season4.getSeasonCompleted();
	   
	   assert season4.completeSeason();
	   assert !season4.getSeasonStarted();
	   assert season4.getSeasonCompleted();
	   
	   assert !season4.completeSeason();
	   assert !season4.getSeasonStarted();
	   assert season4.getSeasonCompleted();
   }
   
   @Test
   void test_currentRounds() {
	   assert season1.getCurrentRound() == 1;
   }
}
