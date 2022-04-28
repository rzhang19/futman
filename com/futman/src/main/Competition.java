package com.futman.src.main;



import java.util.ArrayList;

public abstract class Competition {
   private static int ID_COUNTER = 1;
   
   private static final int DEFAULT_MAX_TEAMS = 64;
   
   // identifiers
   private int m_id;
   private String m_name;
   
   private static int m_currentYear = 2022;
   
   private Country m_country;

   private int MAX_SIZE;	// maximum possible size of the teams
   private Team[] m_teams;
   private int m_teamCount;
   
   private int m_maxMatchesSeason; // maximum number of matches per season
   
   private ArrayList<Season> m_seasons;
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
   }
   
   public int getID() {
      return m_id;
   }
   
   public String getName() {
      return m_name;
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
      Team[] temp = new Team[m_teams.length + teams.length];
      int added = 0;
      
      for (int x = 0; x < m_teams.length; x++) {
         temp[x] = m_teams[x];
      }
      
      for (int x = 0; x < teams.length; x++) {
         if (findTeam(teams[x]) == -1) {
            temp[m_teamCount + x] = teams[x];
            added++;
         }
      }
      
      if (m_teamCount + added > MAX_SIZE) {
         return false;
      }
      
      else {
         for (int x = m_teamCount; x < added; x++) {
            m_teams[x] = temp[x];
         }
         
         m_teamCount += added;
         
         return true;
      }
   }
   
   public boolean removeTeam(Team team) {
      int index = findTeam(team);
      
      if (index >= 0) {
         m_teams[index] = m_teams[m_teamCount];
         m_teamCount--;
         return true;
      }
      
      else
         return false;
   }
   
   public int findTeam(Team team) {
      for (int x = 0; x < m_teams.length; x++) {
         if (m_teams[x].equals(team))
            return x;
      }
      
      return -1;
   }
   
   public static int findTeam(Team[] teams, Team team) {
      for (int x = 0; x < teams.length; x++) {
         if (teams[x].equals(team))
            return x;
      }
      
      return -1;
   }
   
   public Team[] getTeams() {
	   return m_teams;
   }
   
   public int getMaxSize() {
	   	return MAX_SIZE;
   }
   
   protected int getMaxMatchesPerSeason() {
	   if (calculateMaxMatches())
		   return m_maxMatchesSeason;
	   
	   System.err.println("Error calculating number of matches for competition");
	   return m_maxMatchesSeason;
   }
   
   protected abstract boolean calculateMaxMatches();
}