package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Country;
import com.futman.src.main.League;

class LeagueTest {
	static Country country = new Country("United States", "USA");
	static League league1;
	static League league2;
	static League league3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		league1 = new League("test", country, 20);
		league2 = new League("test", country, 18);
		league3 = new League("test", country, 20, 1);
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
	void test_calculateMaxMatches() {
		assert league1.getMaxMatchesPerSeason() == 380;
		assert league2.getMaxMatchesPerSeason() == 306;
		assert league3.getMaxMatchesPerSeason() == 190;
	}

}
