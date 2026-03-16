package com.futman.model;

import java.util.Random;

public abstract class Match {
	protected static Random rand = new Random();

	private static int ID_COUNTER = 1;

	protected static final int EVENT_MAX = 10;
	protected static final double GOAL_SCALE_FACTOR = 0.25;

	private static final int[] PERIOD_LENGTH = new int[] { 45, 45 };
	private static final int NUM_PERIODS = 2;
	protected static final int[] OVERTIME_LENGTH = new int[] { 15, 15 };
	protected static final int NUM_OVERTIME = 2;

	private int m_id;

	private Country m_country;

	protected Team m_team1;
	protected Team m_team2;
	protected int m_score1 = 0;
	protected int m_score2 = 0;

	private int m_shotsOnTarget1 = 0;
	private int m_shotsOnTarget2 = 0;
	private int m_shotsOffTarget1 = 0;
	private int m_shotsOffTarget2 = 0;
	private int m_possessionMinutes1 = 0;
	private int m_possessionMinutes2 = 0;

	private int m_lastEventMinute = 0;
	private String m_lastEventDescription = "";

	private boolean m_started = false;
	private boolean m_finished = false;
	private boolean m_processed1 = false;
	private boolean m_processed2 = false;

	protected int m_halfMinute = 0;
	protected int m_minute = 0;
	private int m_period = 0;
	protected boolean m_needOvertime;
	protected Team m_teamInPossession;

	private int m_round;

	public Match(Country country) {
		this(new Team("", "", country), new Team("", "", country), country);
	}

	public Match(Team team1, Team team2, Country country) {
		this(team1, team2, country, false);
	}

	public Match(Team team1, Team team2, Country country, boolean needOvertime) {
		m_id = ID_COUNTER;
		ID_COUNTER++;

		m_country = country;

		m_team1 = team1;
		m_team2 = team2;

		m_needOvertime = needOvertime;
		m_teamInPossession = rand.nextBoolean() ? team1 : team2;
	}

	public int getID() {
		return m_id;
	}

	public boolean equals(Match other) {
		return m_id == other.getID();
	}

	public String toString() {
		if (m_finished)
			return m_team1.getName() + ": " + m_score1 + "\n" + m_team2.getName() + ": " + m_score2;
		else
			return m_team1.getName() + " vs " + m_team2.getName();
	}

	public boolean setTeam1(Team team1) {
		if (!m_started && !m_finished && m_team1 == null && team1 != null) {
			m_team1 = team1;
			return true;
		}

		return false;
	}

	public boolean setTeam2(Team team2) {
		if (!m_started && !m_finished && m_team2 == null && team2 != null) {
			m_team2 = team2;
			return true;
		}

		return false;
	}

	public Team getTeam1() {
		return m_team1;
	}

	public Team getTeam2() {
		return m_team2;
	}

	public int getScore1() {
		return m_score1;
	}

	public int getScore2() {
		return m_score2;
	}

	public int getShotsOnTarget1() {
		return m_shotsOnTarget1;
	}

	public int getShotsOnTarget2() {
		return m_shotsOnTarget2;
	}

	public int getShotsOffTarget1() {
		return m_shotsOffTarget1;
	}

	public int getShotsOffTarget2() {
		return m_shotsOffTarget2;
	}

	public double getPossession1() {
		int total = m_possessionMinutes1 + m_possessionMinutes2;
		if (total == 0)
			return 50.0;
		return (m_possessionMinutes1 * 100.0) / total;
	}

	public double getPossession2() {
		int total = m_possessionMinutes1 + m_possessionMinutes2;
		if (total == 0)
			return 50.0;
		return (m_possessionMinutes2 * 100.0) / total;
	}

	public int getLastEventMinute() {
		return m_lastEventMinute;
	}

	public String getLastEventDescription() {
		return m_lastEventDescription;
	}

	public Team getTeamInPossession() {
		return m_teamInPossession;
	}

	protected void recordEvent(int minute, String description) {
		m_lastEventMinute = minute;
		m_lastEventDescription = description;
	}

	protected void updatePossession(int minutes, Team team) {
		if (team.equals(m_team1)) {
			m_possessionMinutes1 += minutes;
		} else {
			m_possessionMinutes2 += minutes;
		}
	}

	protected void incrementShotsOnTarget(Team team) {
		if (team.equals(m_team1)) {
			m_shotsOnTarget1++;
		} else {
			m_shotsOnTarget2++;
		}
	}

	protected void incrementShotsOffTarget(Team team) {
		if (team.equals(m_team1)) {
			m_shotsOffTarget1++;
		} else {
			m_shotsOffTarget2++;
		}
	}

	protected void incrementScore(Team team) {
		if (team.equals(m_team1)) {
			m_score1++;
		} else {
			m_score2++;
		}
	}

	public boolean runMatch() {
		m_started = true;

		while (m_period < NUM_PERIODS) {
			while (m_halfMinute <= PERIOD_LENGTH[m_period]) {
				int addMin = rand.nextInt(EVENT_MAX) + 1;
				m_halfMinute += addMin;

				if (m_halfMinute >= PERIOD_LENGTH[m_period]) {
					m_halfMinute = PERIOD_LENGTH[m_period] + 1;
				}

				m_minute = m_halfMinute;
				for (int x = 0; x < m_period; x++) {
					m_minute += PERIOD_LENGTH[x];
				}

				updatePossession(addMin, m_teamInPossession);

				simulatePossessionPhase();
			}

			m_period++;
			m_halfMinute = 0;
		}

		return true;
	}

	protected void simulatePossessionPhase() {
		Team attacker = m_teamInPossession;
		Team defender = attacker.equals(m_team1) ? m_team2 : m_team1;

		int attMid = attacker.getMidOverall();
		int defMid = defender.getMidOverall();
		double midChance = attMid / (double) (attMid + defMid);

		if (rand.nextDouble() >= midChance) {
			recordEvent(m_minute, attacker.getName() + " loses possession in the middle of the pitch");
			m_teamInPossession = defender;
			return;
		}

		int attAtt = attacker.getAttOverall();
		int defDef = defender.getDefOverall();
		double defChance = defDef / (double) (attAtt + defDef);

		if (rand.nextDouble() < defChance) {
			recordEvent(m_minute, attacker.getName() + " shot on goal, great defending");
			incrementShotsOffTarget(attacker);
			m_teamInPossession = defender;
			return;
		}

		int defGK = defender.getGKOverall();
		double goalChance = (attAtt / (double) (attAtt + defGK)) * GOAL_SCALE_FACTOR;

		incrementShotsOnTarget(attacker);

		if (rand.nextDouble() < goalChance) {
			incrementScore(attacker);
			recordEvent(m_minute, attacker.getName() + " scored a goal!");
		} else {
			recordEvent(m_minute, attacker.getName() + " shot on goal, saved by GK");
		}

		m_teamInPossession = defender;
	}

	public boolean processMatch() {
		if (!m_finished) {
			System.err.println("model.Match Error: Match is not finished");
			return false;
		}

		if (m_team1.processMatch(this)) {
			m_processed1 = true;
		}

		else {
			return false;
		}

		if (m_team2.processMatch(this)) {
			m_processed2 = true;
		}

		else {
			return false;
		}

		return true;
	}

	public boolean unprocessMatch() {
		if (!m_finished || !(m_processed1 && m_processed2))
			return false;

		if (m_team1.unprocessMatch(this)) {
			m_processed1 = false;
		}

		else {
			return false;
		}

		if (m_team2.unprocessMatch(this)) {
			m_processed2 = false;
		}

		else {
			return false;
		}

		return true;
	}

	public boolean finishMatch() {
		m_finished = true;
		return true;
	}

	public boolean isFinished() {
		return m_finished;
	}

	public int getRound() {
		return m_round;
	}

	public boolean setRound(int round) {
		m_round = round;
		return true;
	}

	public Team getWinner() {
		if (m_score1 > m_score2) {
			return m_team1;
		}

		else if (m_score2 > m_score1) {
			return m_team2;
		}

		else
			return null;
	}

	public boolean isValid() {
		if (m_country == null) {
			System.err.println("model.Match Error: country cannot be null");
			return false;
		}

		if (m_team1 == null) {
			System.err.println("model.Match Error: team 1 cannot be null");
			return false;
		}

		if (m_team2 == null) {
			System.err.println("model.Match Error: team 2 cannot be null");
			return false;
		}

		if (m_score1 < 0) {
			System.err.println("model.Match Error: score 1 cannot be negative");
			return false;
		}

		if (m_score2 < 0) {
			System.err.println("model.Match Error: score 2 cannot be negative");
			return false;
		}

		if (m_halfMinute < 0 || m_halfMinute > 45) {
			System.err.println("model.Match Error: half minute must be between 0 and 45");
			return false;
		}

		if (m_minute < 0 || m_minute > 90) {
			System.err.println("model.Match Error: minute must be between 0 and 90");
			return false;
		}

		if (m_period != 0 && m_period != 1) {
			System.err.println("model.Match Error: period must be zero or one");
			return false;
		}

		if (m_round < 0) {
			System.err.println("model.Match Error: round cannot be negative");
			return false;
		}

		return true;
	}
}
