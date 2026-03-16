package com.futman.model;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchTest {
	static Country country = new Country("United States", "USA");
	static Team team1;
	static Team team2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		team1 = createTestTeam("Team A", "TA", country, 75);
		team2 = createTestTeam("Team B", "TB", country, 70);
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

	private static Team createTestTeam(String name, String shortName, Country country, int overall) {
		Team team = new Team(name, shortName, country);

		Player gk = new Player("GK", name, "", LocalDate.of(2000, 1, 1), Player.Position.GK, overall, overall, overall,
				overall, overall, overall);
		Player def1 = new Player("DEF1", name, "", LocalDate.of(2000, 1, 1), Player.Position.DEF, overall, overall,
				overall, overall, overall, overall);
		Player def2 = new Player("DEF2", name, "", LocalDate.of(2000, 1, 1), Player.Position.DEF, overall, overall,
				overall, overall, overall, overall);
		Player def3 = new Player("DEF3", name, "", LocalDate.of(2000, 1, 1), Player.Position.DEF, overall, overall,
				overall, overall, overall, overall);
		Player def4 = new Player("DEF4", name, "", LocalDate.of(2000, 1, 1), Player.Position.DEF, overall, overall,
				overall, overall, overall, overall);
		Player mid1 = new Player("MID1", name, "", LocalDate.of(2000, 1, 1), Player.Position.MID, overall, overall,
				overall, overall, overall, overall);
		Player mid2 = new Player("MID2", name, "", LocalDate.of(2000, 1, 1), Player.Position.MID, overall, overall,
				overall, overall, overall, overall);
		Player mid3 = new Player("MID3", name, "", LocalDate.of(2000, 1, 1), Player.Position.MID, overall, overall,
				overall, overall, overall, overall);
		Player mid4 = new Player("MID4", name, "", LocalDate.of(2000, 1, 1), Player.Position.MID, overall, overall,
				overall, overall, overall, overall);
		Player att1 = new Player("ATT1", name, "", LocalDate.of(2000, 1, 1), Player.Position.ATT, overall, overall,
				overall, overall, overall, overall);
		Player att2 = new Player("ATT2", name, "", LocalDate.of(2000, 1, 1), Player.Position.ATT, overall, overall,
				overall, overall, overall, overall);

		team.addPlayer(gk);
		team.addPlayer(def1);
		team.addPlayer(def2);
		team.addPlayer(def3);
		team.addPlayer(def4);
		team.addPlayer(mid1);
		team.addPlayer(mid2);
		team.addPlayer(mid3);
		team.addPlayer(mid4);
		team.addPlayer(att1);
		team.addPlayer(att2);

		return team;
	}

	@Test
	void test_runMatch_producesValidScore() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		assert match.getScore1() >= 0;
		assert match.getScore2() >= 0;
	}

	@Test
	void test_runMatch_populatesStats() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		assert match.getShotsOnTarget1() >= 0;
		assert match.getShotsOnTarget2() >= 0;
		assert match.getShotsOffTarget1() >= 0;
		assert match.getShotsOffTarget2() >= 0;
	}

	@Test
	void test_runMatch_tracksPossession() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		double totalPossession = match.getPossession1() + match.getPossession2();
		assert totalPossession > 99.0 && totalPossession < 101.0;
	}

	@Test
	void test_runMatch_recordsLastEvent() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		assert match.getLastEventMinute() > 0;
		assert match.getLastEventDescription() != null;
		assert !match.getLastEventDescription().isEmpty();
	}

	@Test
	void test_getPossession1_plusPossession2_equals100() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		double total = match.getPossession1() + match.getPossession2();
		assert total > 99.0 && total < 101.0;
	}

	@Test
	void test_getShotsOnTarget_nonNegative() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		assert match.getShotsOnTarget1() >= 0;
		assert match.getShotsOnTarget2() >= 0;
	}

	@Test
	void test_getWinner_team1Wins_returnsTeam1() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		if (match.getScore1() > match.getScore2()) {
			assert match.getWinner().equals(team1);
		}
	}

	@Test
	void test_getWinner_draw_returnsNull() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		if (match.getScore1() == match.getScore2()) {
			assert match.getWinner() == null;
		}
	}

	@Test
	void test_getShotsOnTarget_greaterThanOrEqualGoals() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		assert match.getShotsOnTarget1() >= match.getScore1();
		assert match.getShotsOnTarget2() >= match.getScore2();
	}

	@Test
	void test_getLastEventMinute_afterEvent_returnsCorrectMinute() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		assert match.getLastEventMinute() >= 1 && match.getLastEventMinute() <= 91;
	}

	@Test
	void test_getLastEventDescription_containsValidText() {
		Match match = new LeagueMatch(team1, team2, country);
		match.runMatch();
		match.finishMatch();

		String desc = match.getLastEventDescription();
		boolean isValidEvent = desc.contains("goal") || desc.contains("saved") || desc.contains("defending")
				|| desc.contains("possession");
		assert isValidEvent;
	}

	@Test
	void test_isValid() {
		Match match = new LeagueMatch(team1, team2, country);
		assert match.isValid();
	}

	@Test
	void test_finishMatch_setsFinished() {
		Match match = new LeagueMatch(team1, team2, country);
		assert !match.isFinished();
		match.finishMatch();
		assert match.isFinished();
	}
}
