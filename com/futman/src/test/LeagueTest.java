package com.futman.src.test;

import static org.junit.jupiter.api.Assertions.*;

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

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		league1 = new League("test", country, 20);
		league2 = new League("test", country, 18);
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
	void test() {
		assert true;
	}

}
