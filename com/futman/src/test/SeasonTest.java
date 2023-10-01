package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.League;
import com.futman.src.main.Season;
import com.futman.src.main.Competition;
import com.futman.src.main.Country;

class SeasonTest {
   static Country country = new Country("United States", "USA");
   static Competition comp = new League("Test League", country);
   static Season season1 = new Season(comp);

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
   void test_emptyTeams() {
	   assert season1.getTeams()[0] == null;
	   assert season1.getTeamsCount() == 0;
   }
   
   @Test
   void test_emptyMatches() {
	   assert season1.getMatches().length == 0 || season1.getMatches()[0] == null;
	   assert season1.getMatchesCount() == 0;
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
}
