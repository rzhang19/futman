package com.futman.src.main;



import java.util.List;
import java.util.ArrayList;

public abstract class Competition {
   private static int ID_COUNTER = 1;
   
   protected static final int DEFAULT_MAX_TEAMS = 64;
   private static final int STARTING_YEAR = 2023;
   
   // identifiers
   private int m_id;
   private String m_name;
   
   private int m_currentYear = STARTING_YEAR;
   
   private Country m_country;

   protected int MAX_SIZE;	// maximum possible size of the teams
   protected Team[] m_teams;
   private int m_teamCount;
   
   private int m_maxMatchesSeason; // maximum number of matches per season
   
   private List<Season> m_seasons;
   private int m_seasonCount;
   
   public Competition(Country country) {
      this ("", country);
   }
   
   public Competition(String name, Country country) {
      this (name, country, DEFAULT_MAX_TEAMS);
   }
   
   public Competition(String name, Country country, int maxTeams) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_name = name;
      
      m_country = country;
      
      m_teams = new Team[maxTeams];
      MAX_SIZE = maxTeams;
      m_teamCount = 0;
      
      m_seasons = new ArrayList<Season>();
      m_seasonCount = 0;
      
      this.calculateMaxMatches();
   }
   
   public int getID() {
      return m_id;
   }
   
   public String getName() {
      return m_name;
   }
   
   public int getYear() {
	   return m_currentYear;
   }
   
   public boolean nextYear() {
	   m_currentYear++;
	   return true;
   }
   
   public Country getCountry() {
      return m_country;
   }
   
   public boolean equals(Competition other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return m_name + " (" + m_country.getName() + ")";
   }
   
   public boolean addTeams(Team[] teams) {
      Team[] toAdd = new Team[teams.length];
      int added = 0;
      
      for (int x = 0; x < teams.length; x++) {
         if (findTeam(teams[x]) < 0 && findTeam(toAdd, teams[x]) < 0) {
        	 toAdd[added] = teams[x];
        	 added++;
         }
      }
      
      if (m_teamCount + added > MAX_SIZE) {
         return false;
      }
      
      if (added == 0)
    	  return false;
      
      for (int x = 0; x < toAdd.length; x++) {
    	  m_teams[m_teamCount] = toAdd[x];
    	  m_teamCount++;
      }
      
      return true;
   }
   
   public boolean removeTeam(Team team) {
      int index = findTeam(team);
      
      if (index >= 0) {
         m_teams[index] = m_teams[m_teamCount - 1];
         m_teamCount--;
         return true;
      }
      
      else
         return false;
   }
   
   public int findTeam(Team team) {
      for (int x = 0; x < m_teamCount; x++) {
         if (m_teams[x].equals(team))
            return x;
      }
      
      return -1;
   }
   
   public static int findTeam(Team[] teams, Team team) {
      for (int x = 0; x < teams.length; x++) {
         if (teams[x] != null && teams[x].equals(team))
            return x;
      }
      
      return -1;
   }
   
   public Team[] getTeams() {
	   return m_teams;
   }
   
   public int getTeamCount() {
	   return m_teamCount;
   }
   
   public int getMaxSize() {
	   	return MAX_SIZE;
   }
   
   public int getMaxMatchesPerSeason() {
	   if (calculateMaxMatches())
		   return m_maxMatchesSeason;
	   
	   System.err.println("Error calculating number of matches for competition");
	   return m_maxMatchesSeason;
   }
   
   public boolean setMaxMatches(int value) {
	   m_maxMatchesSeason = value;
	   return true;
   }
   
   protected abstract boolean calculateMaxMatches();
   
   public List<Season> getSeasons() {
	   return m_seasons;
   }
   
   public Season getCurrentSeason() {
	   if (m_seasons == null || m_seasonCount <= 0)
		   return null;
	   
	   return m_seasons.get(m_seasonCount - 1);
   }
   
   public static int getStartingYear() {
      return STARTING_YEAR;
   }
   
   // should only ever be called outside of startNextSeason() once ever per Competition, at the very beginning, for the first season
   public boolean startNewSeason() {
	   Season newSeason = new Season(this);
	   m_seasons.add(newSeason);
	   m_seasonCount++;
	   
	   if (!(m_seasons.get(m_seasonCount - 1).startSeason())) {
		   System.err.println("src.main.Competition Error: unable to start season");
		   return false;
	   }
	   
	   return true;
   }
   
   public boolean endCurrentSeason() {
	   if (!m_seasons.get(m_seasonCount - 1).completeSeason())
		   return false;
	   
	   return true;
   }
   
   public boolean startNextSeason() {
	   if (!endCurrentSeason()) {
		   System.err.println("src.main.Competition Error: unable to end current season");
		   return false;
	   }
	   
	   m_currentYear++;
	   
	   if (!startNewSeason()) {
		   System.err.println("src.main.Competition Error: unable to start new season");
		   return false;
	   }
	   
	   return true;
   }
}