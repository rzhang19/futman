package com.futman.src.main;
public class Season {
   private static int ID_COUNTER = 1;
   
   // identifier
   private int m_id;
   
   private Competition m_competition;
   private int m_year;
   
   private boolean m_completed;
   
   private Team[] m_teams;
   private int m_currTeamsCount;
   private int m_maxTeamCount;
   
   private Match[] m_matches;
   private int m_maxMatchCount;
   private int m_currMatchCount;
   
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
   
   public boolean getCompleted() {
      return m_completed;
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
}