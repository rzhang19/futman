package com.futman.model;

import org.junit.jupiter.api.Test;

class FormationTest {

	@Test
	void test_getDefenders_4_4_2_returns4() {
		assert Formation.F_4_4_2.getDefenders() == 4;
	}

	@Test
	void test_getDefenders_4_3_3_returns4() {
		assert Formation.F_4_3_3.getDefenders() == 4;
	}

	@Test
	void test_getMidfielders_4_4_2_returns4() {
		assert Formation.F_4_4_2.getMidfielders() == 4;
	}

	@Test
	void test_getMidfielders_4_3_3_returns3() {
		assert Formation.F_4_3_3.getMidfielders() == 3;
	}

	@Test
	void test_getAttackers_4_4_2_returns2() {
		assert Formation.F_4_4_2.getAttackers() == 2;
	}

	@Test
	void test_getAttackers_3_5_2_returns2() {
		assert Formation.F_3_5_2.getAttackers() == 2;
	}

	@Test
	void test_getTotalOutfield_4_4_2_returns10() {
		assert Formation.F_4_4_2.getTotalOutfield() == 10;
	}

	@Test
	void test_getTotalOutfield_4_3_3_returns10() {
		assert Formation.F_4_3_3.getTotalOutfield() == 10;
	}

	@Test
	void test_getDisplayName_4_4_2_returnsCorrectString() {
		assert Formation.F_4_4_2.getDisplayName().equals("4-4-2");
	}

	@Test
	void test_getDisplayName_4_3_3_returnsCorrectString() {
		assert Formation.F_4_3_3.getDisplayName().equals("4-3-3");
	}

	@Test
	void test_isValid_4_4_2_returnsTrue() {
		assert Formation.F_4_4_2.isValid();
	}

	@Test
	void test_isValid_4_3_3_returnsTrue() {
		assert Formation.F_4_3_3.isValid();
	}

	@Test
	void test_isValid_3_5_2_returnsTrue() {
		assert Formation.F_3_5_2.isValid();
	}

	@Test
	void test_isValid_3_4_3_returnsTrue() {
		assert Formation.F_3_4_3.isValid();
	}

	@Test
	void test_isValid_5_3_2_returnsTrue() {
		assert Formation.F_5_3_2.isValid();
	}

	@Test
	void test_isValid_5_2_3_returnsTrue() {
		assert Formation.F_5_2_3.isValid();
	}

	@Test
	void test_isValid_4_5_1_returnsTrue() {
		assert Formation.F_4_5_1.isValid();
	}
}
