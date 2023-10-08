package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Country;
import com.futman.src.main.Cup;

class CupTest {
	static Country country = new Country("United States", "USA");
	static Cup cup1;
	static Cup cup2;
	static Cup cup3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cup1 = new Cup("test", country, 20);
		cup2 = new Cup("test 2", country, 40);
		cup3 = new Cup("test 3", country, 32, 2, false);
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
		assert cup1.getMaxMatchesPerSeason() == 20;
		assert cup2.getMaxMatchesPerSeason() == 40;
		assert cup3.getMaxMatchesPerSeason() == 63;
	}
	
	@Test
	void test_isValid() {
		assert cup1.isValid();
		assert cup2.isValid();
		assert cup3.isValid();
		
		Cup cup4 = new Cup("another test", country, 1, -1, false);
		assert !(cup4.isValid());
	}
}
