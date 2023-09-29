package com.futman.src.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.futman.src.main.Country;

class CountryTest {
	static Country country1 = new Country ("United States", "USA");
	static Country country2 = new Country ("Canada", "CAN");
	
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
		assert(country1.getID() == 1);
		assert(country2.getID() == 2);
	}
	
	@Test
	void test_equals() {
		Country country1dupe = country1;
		
		assert(country1dupe.equals(country1));
		assert(!country1.equals(country2));
	}
	
	@Test
	void test_toString() {
		assert(country1.toString().equals("United States (USA)"));
		assert(country2.toString().equals("Canada (CAN)"));
	}
	
	@Test
	void test_getName() {
		assert(country1.getName().equals("United States"));
		assert(country2.getName().equals("Canada"));
	}
	
	@Test
	void test_getCode() {
		assert(country1.getCode().equals("USA"));
		assert(country2.getCode().equals("CAN"));
	}
}
