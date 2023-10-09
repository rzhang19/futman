package com.futman.src.main;

import java.util.Random;

public abstract class Match {
   protected static Random rand = new Random();
   
   private static int ID_COUNTER = 1;
   
   // maximum interval between events
   protected static final int EVENT_MAX = 10;
   
   // length of a half
   private static final int[] PERIOD_LENGTH = new int[] {45,45};
   private static final int NUM_PERIODS = 2;
   protected static final int[] OVERTIME_LENGTH = new int[] {15,15};
   protected static final int NUM_OVERTIME = 2;
   
   // Match identifier information
   private int m_id;
   
   // basic Match contents
   private Country m_country;
   
   protected Team m_team1;
   protected Team m_team2;
   protected int m_score1 = 0;
   protected int m_score2 = 0;
   
   // meta Match runner helpers
   private boolean m_started = false;
   private boolean m_finished = false;
   private boolean m_processed1 = false;
   private boolean m_processed2 = false;
   
   // match runner helpers
   protected int m_halfMinute = 0;
   protected int m_minute = 0;
   private int m_period = 0;
   protected boolean m_needOvertime;	// default will be false
   protected boolean m_team1Poss;
   
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
      m_team1Poss = rand.nextBoolean();
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
   
   public boolean runMatch() {
	  m_started = true;
      while (m_period < NUM_PERIODS) {
    	  while (m_halfMinute <= PERIOD_LENGTH[m_period]) {
    		  // set proper minutes
    		  int addMin = rand.nextInt(EVENT_MAX) + 1;
    		  m_halfMinute += addMin;
    		  
    		  if (m_halfMinute >= PERIOD_LENGTH[m_period]) {
    			  m_halfMinute = PERIOD_LENGTH[m_period] + 1;
    		  }
    		  
    		  m_minute = m_halfMinute;
    		  for (int x = 0; x < m_period; x++) {
    			  m_minute += PERIOD_LENGTH[x];
    		  }
    		  
    		  boolean poss = m_team1Poss;
    		  
    		  // need to replace all getOverall() with its proper fields, as dictated by the comments below
    		  // midfield vs midfield
    		  if (poss == m_team1Poss) {
    			  int attTeam;
    			  int defTeam;
    			  
    			  if (m_team1Poss) {
    				  attTeam = m_team1.getMidOverall() + 1;
    				  defTeam = m_team2.getMidOverall() + 1;
    			  }
    			  
    			  else {
    				  attTeam = m_team2.getMidOverall() + 1;
    				  defTeam = m_team1.getMidOverall() + 1;
    			  }
    			  
    			  int randomAtt = rand.nextInt(attTeam);
    			  int randomDef = rand.nextInt(defTeam);
    			  
    			  if (randomAtt <= randomDef) {
    				  m_team1Poss = !m_team1Poss;
    			  }
    		  }
    		  
    		  // att vs def
    		  if (poss == m_team1Poss) {
    			  int attTeam;
    			  int defTeam;
    			  
    			  if (m_team1Poss) {
    				  attTeam = m_team1.getAttOverall() + 1;
    				  defTeam = m_team2.getDefOverall() + 1;
    			  }
    			  
    			  else {
    				  attTeam = m_team2.getAttOverall() + 1;
    				  defTeam = m_team1.getDefOverall() + 1;
    			  }
    			  
    			  int randomAtt = rand.nextInt(attTeam);
    			  int randomDef = rand.nextInt(defTeam);
    			  
    			  if (randomAtt <= randomDef) {
    				  m_team1Poss = !m_team1Poss;
    			  }
    		  }
    		  
    		  // att vs gk
    		  if (poss == m_team1Poss) {
    			  int attTeam;
    			  int defTeam;
    			  
    			  if (m_team1Poss) {
    				  attTeam = m_team1.getAttOverall() + 1;
    				  defTeam = m_team2.getGKOverall() + 1;
    			  }
    			  
    			  else {
    				  attTeam = m_team2.getAttOverall() + 1;
    				  defTeam = m_team1.getGKOverall() + 1;
    			  }
    			  
    			  int randomAtt = rand.nextInt(attTeam);
    			  int randomDef = rand.nextInt(defTeam);
    			  
    			  if (randomAtt > randomDef) {
    				  if (m_team1Poss) {
    					  m_score1++;
    				  }
    				  
    				  else {
    					  m_score2++;
    				  }
    			  }
    			  
    			  m_team1Poss = !m_team1Poss;
    		  }
    	  }
    	  
    	  m_period++;
    	  m_halfMinute = 0;
      }
	  
      return true;
   }
   
   public boolean processMatch() {
      if (!m_finished) {
    	  System.err.println("src.main.Match Error: Match is not finished");
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
	   
	   else return null;
   }
   
   public boolean isValid() {
	   if (m_country == null) {
		   System.err.println("src.main.Match Error: country cannot be null");
		   return false;
	   }
	   
	   if (m_team1 == null) {
		   System.err.println("src.main.Match Error: team 1 cannot be null");
		   return false;
	   }
	   
	   if (m_team2 == null) {
		   System.err.println("src.main.Match Error: team 2 cannot be null");
		   return false;
	   }
	   
	   if (m_score1 < 0) {
		   System.err.println("src.main.Match Error: score 1 cannot be negative");
		   return false;
	   }
	   
	   if (m_score2 < 0) {
		   System.err.println("src.main.Match Error: score 2 cannot be negative");
		   return false;
	   }
	   
	   if (m_halfMinute < 0 || m_halfMinute > 45) {
		   System.err.println("src.main.Match Error: half minute must be between 0 and 45");
		   return false;
	   }
	   
	   if (m_minute < 0 || m_minute > 90) {
		   System.err.println("src.main.Match Error: minute must be between 0 and 90");
		   return false;
	   }
	   
	   if (m_period != 0 && m_period != 1) {
		   System.err.println("src.main.Match Error: period must be zero or one");
		   return false;
	   }
	   
	   if (m_round < 0) {
		   System.err.println("src.main.Match Error: round cannot be negative");
		   return false;
	   }
	   
	   return true;
   }
}