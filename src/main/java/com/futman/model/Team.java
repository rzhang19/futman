package com.futman.model;

import com.futman.model.Player.Position;

public class Team {
	private static int ID_COUNTER = 1;
	private static final int MAX_PLAYERS = 50;
	private static final int STARTING_XI_SIZE = 11;

	private int m_id;
	private String m_longName;
	private String m_shortName;
	private Country m_country;
	private League m_league;

	private int m_seasonWins = 0;
	private int m_seasonDraws = 0;
	private int m_seasonLosses = 0;
	private int m_seasonGoals = 0;
	private int m_goalsConceded = 0;

	private int m_attOverall = 1;
	private int m_midOverall = 1;
	private int m_defOverall = 1;
	private int m_gkOverall = 1;
	private int m_overall = 1;

	private Player[] m_players = new Player[MAX_PLAYERS];
	private int m_playerCount = 0;

	private Formation m_formation = Formation.F_4_4_2;
	private Player[] m_startingXI = new Player[STARTING_XI_SIZE];
	private int m_starterCount = 0;

	public Team(String longName, String shortName, Country country) {
		m_id = ID_COUNTER;
		ID_COUNTER++;

		m_longName = longName;
		m_shortName = shortName;

		m_country = country;
	}

	public int getID() {
		return m_id;
	}

	public void setID(int id) {
		m_id = id;
	}

	public boolean equals(Team other) {
		return m_id == other.getID();
	}

	public String toString() {
		return m_longName + " (" + m_country.getCode() + ")";
	}

	public String getName() {
		return m_longName;
	}

	public String getShortName() {
		return m_shortName;
	}

	public Country getCountry() {
		return m_country;
	}

	public boolean addLeague(Competition league) {
		if (!(league instanceof League)) {
			System.err.println("model.Team Error: can't add a non-League to m_competition");
			return false;
		}

		m_league = (League) league;
		return true;
	}

	public League getLeague() {
		return m_league;
	}

	public int getSeasonWins() {
		return m_seasonWins;
	}

	public int getSeasonDraws() {
		return m_seasonDraws;
	}

	public int getSeasonLosses() {
		return m_seasonLosses;
	}

	public int getSeasonGoals() {
		return m_seasonGoals;
	}

	public int getGoalsConceded() {
		return m_goalsConceded;
	}

	public boolean addGoalsConceded(int goals) {
		m_goalsConceded += goals;
		return true;
	}

	public boolean addGoals(int goals) {
		m_seasonGoals += goals;
		return true;
	}

	public boolean resetGoals() {
		m_seasonGoals = 0;
		return true;
	}

	public int getAttOverall() {
		boolean calculate = calculateAttOverall();

		if (!calculate)
			System.err.println("Error calculating attack overall");

		return m_attOverall;
	}

	public int getMidOverall() {
		boolean calculate = calculateMidOverall();

		if (!calculate)
			System.err.println("Error calculating midfield overall");

		return m_midOverall;
	}

	public int getDefOverall() {
		boolean calculate = calculateDefOverall();

		if (!calculate)
			System.err.println("Error calculating defense overall");

		return m_defOverall;
	}

	public int getGKOverall() {
		boolean calculate = calculateGKOverall();

		if (!calculate)
			System.err.println("Error calculating goalkeeping overall");

		return m_gkOverall;
	}

	public int getOverall() {
		if (calculateOverall())
			return m_overall;
		System.err.println("Error calculating overall");
		return -1;
	}

	private boolean calculateOverall() {
		m_overall = (m_attOverall + m_midOverall + m_defOverall + m_gkOverall) / 4;
		return true;
	}

	private boolean calculateAttOverall() {
		int sum = 0;
		int count = 0;

		for (int x = 0; x < m_playerCount; x++) {
			if (m_players[x].getPositionEnum() == Position.ATT) {
				sum += m_players[x].getOverall();
				count++;
			}
		}

		m_attOverall = count == 0 ? 1 : sum / count;

		return true;
	}

	private boolean calculateMidOverall() {
		int sum = 0;
		int count = 0;

		for (int x = 0; x < m_playerCount; x++) {
			if (m_players[x].getPositionEnum() == Position.MID) {
				sum += m_players[x].getOverall();
				count++;
			}
		}

		m_midOverall = count == 0 ? 1 : sum / count;

		return true;
	}

	private boolean calculateDefOverall() {
		int sum = 0;
		int count = 0;

		for (int x = 0; x < m_playerCount; x++) {
			if (m_players[x].getPositionEnum() == Position.DEF) {
				sum += m_players[x].getOverall();
				count++;
			}
		}

		m_defOverall = count == 0 ? 1 : sum / count;

		return true;
	}

	private boolean calculateGKOverall() {
		int sum = 0;
		int count = 0;

		for (int x = 0; x < m_playerCount; x++) {
			if (m_players[x].getPositionEnum() == Position.GK) {
				sum += m_players[x].getOverall();
				count++;
			}
		}

		m_gkOverall = count == 0 ? 1 : sum / count;

		return true;
	}

	public boolean processMatch(Match match) {
		int score1 = match.getScore1();
		int score2 = match.getScore2();
		if (match.getTeam1().equals(this)) {
			if (score1 > score2)
				m_seasonWins++;
			else if (score1 == score2)
				m_seasonDraws++;
			else
				m_seasonLosses++;
			m_goalsConceded += score2;
		}

		else {
			if (score2 > score1)
				m_seasonWins++;
			else if (score2 == score1)
				m_seasonDraws++;
			else
				m_seasonLosses++;
			m_goalsConceded += score1;
		}

		return true;
	}

	public boolean unprocessMatch(Match match) {
		return true;
	}

	public boolean addPlayers(Player[] players) {
		int addable = 0;
		Player[] toAdd = new Player[players.length];

		for (int x = 0; x < players.length; x++) {
			if (findPlayer(players[x]) < 0 && findPlayer(toAdd, players[x]) < 0) {
				toAdd[addable] = players[x];
				addable++;
			}
		}

		if (m_playerCount + addable > MAX_PLAYERS) {
			return false;
		}

		if (addable == 0) {
			return false;
		}

		for (int x = 0; x < toAdd.length; x++) {
			m_players[m_playerCount] = toAdd[x];
			m_playerCount++;
		}

		return true;
	}

	public int getPlayerCount() {
		return m_playerCount;
	}

	public Player[] getPlayers() {
		return m_players;
	}

	public boolean addPlayer(Player player) {
		if (m_playerCount < MAX_PLAYERS && findPlayer(player) < 0) {
			m_players[m_playerCount] = player;
			m_playerCount++;
			return player.addToTeam(this);
		}

		return false;
	}

	public int findPlayer(Player player) {
		for (int x = 0; x < m_playerCount; x++) {
			if (m_players[x].equals(player)) {
				return x;
			}
		}

		return -1;
	}

	public int findPlayer(Player[] players, Player findMe) {
		for (int x = 0; x < players.length; x++) {
			if (players[x] != null && players[x].equals(findMe))
				return x;
		}

		return -1;
	}

	public boolean removePlayer(Player player) {
		int index = findPlayer(player);
		if (index < 0)
			return false;

		m_players[index] = m_players[m_playerCount - 1];
		m_players[m_playerCount - 1] = null;
		m_playerCount--;

		return true;
	}

	public Formation getFormation() {
		return m_formation;
	}

	public boolean setFormation(Formation formation) {
		if (formation == null || !formation.isValid()) {
			return false;
		}
		m_formation = formation;
		return true;
	}

	public Player[] getStartingXI() {
		return m_startingXI;
	}

	public int getStarterCount() {
		return m_starterCount;
	}

	public Player getStarter(int slot) {
		if (slot < 0 || slot >= STARTING_XI_SIZE) {
			return null;
		}
		return m_startingXI[slot];
	}

	public boolean setStarter(Player player, int slot) {
		if (player == null) {
			return false;
		}
		if (slot < 0 || slot >= STARTING_XI_SIZE) {
			return false;
		}
		if (findPlayer(player) < 0) {
			return false;
		}

		if (m_startingXI[slot] == null) {
			m_starterCount++;
		}
		m_startingXI[slot] = player;
		return true;
	}

	public boolean removeStarter(int slot) {
		if (slot < 0 || slot >= STARTING_XI_SIZE) {
			return false;
		}
		if (m_startingXI[slot] == null) {
			return false;
		}

		m_startingXI[slot] = null;
		m_starterCount--;
		return true;
	}

	public boolean clearStartingXI() {
		for (int i = 0; i < STARTING_XI_SIZE; i++) {
			m_startingXI[i] = null;
		}
		m_starterCount = 0;
		return true;
	}

	public int countStartersByPosition(Position position) {
		int count = 0;
		for (int i = 0; i < STARTING_XI_SIZE; i++) {
			if (m_startingXI[i] != null && m_startingXI[i].getPositionEnum() == position) {
				count++;
			}
		}
		return count;
	}

	public boolean isValidLineup() {
		if (m_starterCount != STARTING_XI_SIZE) {
			return false;
		}

		int gkCount = countStartersByPosition(Position.GK);
		if (gkCount != 1) {
			return false;
		}

		int defCount = countStartersByPosition(Position.DEF);
		int midCount = countStartersByPosition(Position.MID);
		int attCount = countStartersByPosition(Position.ATT);

		if (defCount != m_formation.getDefenders()) {
			return false;
		}
		if (midCount != m_formation.getMidfielders()) {
			return false;
		}
		if (attCount != m_formation.getAttackers()) {
			return false;
		}

		return true;
	}

	public boolean isValid() {
		if (m_longName.length() <= 0) {
			System.err.println("model.Team Error: Team long name cannot be empty");
			return false;
		}

		if (!(m_shortName.length() == 2 || m_shortName.length() == 3)) {
			System.err.println("model.Team Error: Team short name must be two or three characters");
			return false;
		}

		if (m_country == null) {
			System.err.println("model.Team Error: Team must contain a country");
			return false;
		}

		if (m_seasonWins < 0) {
			System.err.println("model.Team Error: Team season wins must be non-negative");
			return false;
		}

		if (m_seasonDraws < 0) {
			System.err.println("model.Team Error: Team season draws must be non-negative");
			return false;
		}

		if (m_seasonLosses < 0) {
			System.err.println("model.Team Error: Team season losses must be non-negative");
			return false;
		}

		if (m_seasonGoals < 0) {
			System.err.println("model.Team Error: Team season goals must be non-negative");
			return false;
		}

		if (m_goalsConceded < 0) {
			System.err.println("model.Team Error: Team goals conceded must be non-negative");
			return false;
		}

		if (m_attOverall < 1 || m_attOverall > 99) {
			System.err.println("model.Team Error: Team attack overall must be between 1 and 99 inclusive");
			return false;
		}

		if (m_midOverall < 1 || m_midOverall > 99) {
			System.err.println("model.Team Error: Team midfield overall must be between 1 and 99 inclusive");
			return false;
		}

		if (m_defOverall < 1 || m_defOverall > 99) {
			System.err.println("model.Team Error: Team defense overall must be between 1 and 99 inclusive");
			return false;
		}

		if (m_gkOverall < 1 || m_gkOverall > 99) {
			System.err.println("model.Team Error: Team gk overall must be between 1 and 99 inclusive");
			return false;
		}

		if (m_overall < 1 || m_overall > 99) {
			System.err.println("model.Team Error: Team overall must be between 1 and 99 inclusive");
			return false;
		}

		if (m_players == null) {
			System.err.println("model.Team Error: Player array cannot be null");
			return false;
		}

		if (m_playerCount < 0) {
			System.err.println("model.Team Error: m_playerCount must be non-negative");
			return false;
		}

		int x = 0;
		for (; x < m_playerCount; x++) {
			if (m_players[x] == null) {
				System.err.println("model.Team Error: Player at index " + x + " is null");
				return false;
			}
		}

		for (; x < MAX_PLAYERS; x++) {
			if (m_players[x] != null) {
				System.err.println("model.Team Error: non-null Player past count, at index " + x);
				return false;
			}
		}

		return true;
	}
}
