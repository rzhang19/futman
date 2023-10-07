package com.futman.src.main;

import java.util.Random;

public class Season {
   private static Random rand = new Random();
	
   private static int ID_COUNTER = 1;
   
   // identifier
   private int m_id;
   
   private Competition m_competition;
   private int m_year;
   
   private Team[] m_teams;
   private int m_currTeamsCount;
   private int m_maxTeamsCount;
   
   private Match[] m_matches;
   private int m_maxMatchCount;
   private int m_currMatchCount;
   
   private boolean m_started;
   private boolean m_completed;
   
   private int m_currRound;
   
   
   public Season(Competition competition) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_competition = competition;
      
      m_maxTeamsCount = competition.getMaxSize();
      m_teams = new Team[m_maxTeamsCount];
      m_currTeamsCount = 0;
      
      m_maxMatchCount = competition.getMaxMatchesPerSeason();
      m_matches = new Match[m_maxMatchCount];
      m_currMatchCount = 0;
      
      m_started = false;
      m_completed = false;
      
      m_currRound = 1;
      
      setYear();
      
      if (!fetchTeams()) {
    	  System.err.println("src.main.Season Error: error fetching teams from Competition");
      }
      
      if (!seed()) {
    	  System.err.println("src.main.Season Error: error seeding new Season");
      }
   }
   
   public boolean equals(Season other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return m_competition.getName() + " (" + m_year + ")";
   }
   
   public int getID() {
      return m_id;
   }
   
   public int getYear() {
      return m_year;
   }
   
   private boolean setYear() {
	   m_year = m_competition.getYear();
	   return true;
   }
   
   public Team[] getTeams() {
	   return m_teams;
   }
   
   public Match[] getMatches() {
	   return m_matches;
   }
   
   public int getTeamsCount() {
	   return m_currTeamsCount;
   }
   
   public int getMatchesCount() {
	   return m_currMatchCount;
   }
   
   public boolean getSeasonStarted() {
	   return m_started;
   }
   
   public boolean getSeasonCompleted() {
	   return m_completed;
   }
   
   public boolean startSeason() {
	   if (!setYear()) {
		   System.err.println("src.main.Season Error: unable to set year");
		   return false;
	   }
	   
	   if (m_completed) {
		   System.err.println("src.main.Season Error: season already completed");
		   return false;
	   }
	   
	   if (m_started) {
		   System.err.println("src.main.Season Error: season already started");
		   return false;
	   }
	   
	   m_started = true;
	   return true;
   }
   
   public boolean completeSeason() {
	   if (!m_started)
		   return false;
	   
	   if (m_completed)
		   return false;
	   
	   m_completed = true;
	   m_started = false;
	   return true;
   }
   
   public int getCurrentRound() {
	   return m_currRound;
   }
   
   public boolean completeCurrentRound () {
	   return true;
   }
   
   public boolean startNextRound() {
	   return true;
   }
   
   private boolean fetchTeams() {
	   m_currTeamsCount = 0;
	   
	   for (int x = 0; x < m_maxTeamsCount; x++) {
		   m_teams[x] = m_competition.getTeams()[x];
		   m_currTeamsCount++;
	   }
	   
	   return true;
   }
   
   private boolean seed() {
	   if (m_competition instanceof League) {
		   if (!generateAllMatches()) {
			   System.err.println("src.main.Season Error: Unable to generate all matches for League");
			   return false;
		   }
		   
		   return true;
	   }
	   
	   if (m_competition instanceof Cup) {
		   if (!generateFirstRound()) {
			   System.err.println("src.main.Season Error: unable to generate first round");
			   return false;
		   }
		   
		   return true;
	   }
	   
	   System.err.println("src.main.Season Error: invalid competition type");
	   return false;
   }
   
   private boolean generateAllMatches() {
	   Match[][] orderedMatches = getOrderedMatches();
	   
	   // current match index for m_matches
	   int matchNum = 0;
	   
	   // number of completed match selections for this faceTime
	   int completed = 0;
	   
	   for (int timesPlay = 0; timesPlay < m_competition.getFaceTimes(); timesPlay++) {
		   completed = 0;
		   boolean[] selected = new boolean[m_currTeamsCount - 1];
		   
		   while (completed < m_currTeamsCount - 1) {
			   int selection = rand.nextInt(m_currTeamsCount - 1);
			   
			   while (selected[selection]) {
				   selection = rand.nextInt(m_currTeamsCount - 1);
			   }
			   
			   selected[selection] = true;
			   
			   for (int x = 0; x < m_currTeamsCount / 2; x++) {
				   if (m_matches[matchNum] != null) {
					   System.err.println("src.main.Season Error: match at index is not null");
					   return false;
				   }
				   
				   if (orderedMatches[((timesPlay + 1) * (selection + 1)) - 1][x] == null) {
					   System.err.println("src.main.Season Error: match selected is null");
					   return false;
				   }
				   
				   if (orderedMatches[((timesPlay + 1) * (selection + 1)) - 1][x].getTeam1() == null) {
					   System.err.println("src.main.Season Error: match added has null Team 1");
					   return false;
				   }
				   
				   if (orderedMatches[((timesPlay + 1) * (selection + 1)) - 1][x].getTeam2() == null) {
					   System.err.println("src.main.Season Error: match added has null Team 2");
				   }
				   
				   m_matches[matchNum] = orderedMatches[((timesPlay + 1) * (selection + 1)) - 1][x];
				   m_currMatchCount++;
				   matchNum++;
			   }
			   
			   completed++;
		   }
	   }
	   
	   return true;
   }
   
   private Match[][] getOrderedMatches() {
	   if (!(m_competition instanceof League)) {
		   System.err.println("src.main.Season Error: can't generate schedule for non League");
		   return null;
	   }
	   
	   if (m_currTeamsCount <= 0) {
		   System.err.println("src.main.Season Error: need at least one team to seed League");
		   return null;
	   }
	   
	   int weeks = (m_currTeamsCount - 1) * m_competition.getFaceTimes();
	   int matches = m_currTeamsCount / 2;
	   
	   Team[] rotation = new Team[m_maxTeamsCount / 2 == 0 ? m_maxTeamsCount : m_maxTeamsCount + 1];
	   int rotNum;
	   
	   for (int x = 0; x < m_maxTeamsCount; x++) {
		   rotation[x] = m_teams[x];
	   }
	   
	   Match[][] matchesPerWeek = new Match[weeks][matches];
	   
	   for (int x = 0; x < weeks; x++) {
		   rotNum = 0;
		   for (int y = 0; y < matches; y++) {
			   if (matchesPerWeek[x][y] != null) {
				   System.err.println("src.main.Season Error: team already exists at " + x + ", " + y);
				   return null;
			   }
			   
			   if (rotation[rotNum] != null && rotation[rotNum + 1] != null) {
				   matchesPerWeek[x][y] = new LeagueMatch(rotation[rotNum], rotation[rotNum + 1], m_competition.getCountry());
				   rotNum += 2;
			   }
		   }
		   
		   rotation = rotate(rotation);
		   
		   if (rotation == null) {
			   System.err.println("src.main.Season Error: rotation is null");
			   return null;
		   }
	   }
	   
	   return matchesPerWeek;
   }
   
   // use a staggered shift to shift all teams but the first team in a counter-clockwise direction
   private Team[] rotate(Team[] oldRotation) {
	   Team[] newRotation = new Team[m_maxTeamsCount];
	   
	   if (oldRotation.length <= 2) {
		   return oldRotation;
	   }
	   
	   if (oldRotation[0] == null) {
		   System.err.println("src.main.Season Error: team at index 1 is null");
		   return null;
	   }
	   
	   newRotation[0] = oldRotation[0];
	   Team hold = oldRotation[1];
	   
	   int x;
	   
	   // shift all teams on right side of bracket down one
	   for (x = 1; x + 2 < newRotation.length; x += 2) {
		   newRotation[x] = oldRotation[x + 2];
	   }
	   
	   // shift last left side to last right side
	   newRotation[x] = oldRotation[x - 1];
	   
	   // shift all left side up one
	   for (x = x - 1; x > 2; x -= 2) {
		   newRotation[x] = oldRotation[x - 2];
	   }
	   
	   // shift the team originally first on right to the second on left
	   newRotation[2] = hold;
	   
	   return newRotation;
   }
   
   private boolean generateFirstRound() {
	   int byes = getLowerPowerOf2(m_currTeamsCount);
	   int firstRounds = m_currTeamsCount - byes;
	   
	   int matchCounter = 0;
	   
	   boolean[] selected = new boolean[m_currTeamsCount];
	   
	   // first round selections
	   while (matchCounter < firstRounds) {
		   int team1 = rand.nextInt(m_currTeamsCount);
		   while (selected[team1])
			   team1 = rand.nextInt(m_currTeamsCount);
		   
		   int team2 = rand.nextInt(m_currTeamsCount);
		   while (selected[team2])
			   team2 = rand.nextInt(m_currTeamsCount);
		   
		   m_matches[matchCounter] = new CupMatch(m_teams[team1], m_teams[team2], m_competition.getCountry());
		   m_currMatchCount++;
		   
		   selected[team1] = true;
		   selected[team2] = true;
		   
		   matchCounter++;
	   }
	   
	   // second round selections (byes)
	   while (matchCounter < m_currTeamsCount) {
		   int team1 = rand.nextInt(m_currTeamsCount);
		   while (selected[team1])
			   team1 = rand.nextInt(m_currTeamsCount);
		   
		   int team2 = rand.nextInt(m_currTeamsCount);
		   while (selected[team2])
			   team2 = rand.nextInt(m_currTeamsCount);
		   
		   m_matches[matchCounter] = new CupMatch(m_teams[team1], m_teams[team2], m_competition.getCountry());
		   selected[team1] = true;
		   selected[team2] = true;
		   
		   matchCounter++;
	   }
	   
	   return true;
   }
   
   private int getLowerPowerOf2(int size) {
	   int power = 1;
	   
	   while (power < size) {
		   power *= 2;
	   }
	   
	   if (power != size)
		   return power / 2;
	   else
		   return 0;
   }
}