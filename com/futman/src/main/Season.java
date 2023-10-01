package com.futman.src.main;
public class Season {
   private static int ID_COUNTER = 1;
   
   // identifier
   private int m_id;
   
   private Competition m_competition;
   private int m_year;
   
   private Team[] m_teams;
   private int m_currTeamsCount;
   private int m_maxTeamCount;
   
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
      
      m_maxTeamCount = competition.getMaxSize();
      m_teams = new Team[m_maxTeamCount];
      m_currTeamsCount = 0;
      
      m_maxMatchCount = competition.getMaxMatchesPerSeason();
      m_matches = new Match[m_maxMatchCount];
      m_currMatchCount = 0;
      
      m_started = false;
      m_completed = false;
      
      m_currRound = 1;
      
      setYear();
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
}