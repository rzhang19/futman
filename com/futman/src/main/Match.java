package com.futman.src.main;

public abstract class Match {
   private static int ID_COUNTER = 1;
   
   // Match identifier information
   private int m_id;
   
   // basic Match contents
   private Country m_country;
   
   private Team m_team1;
   private Team m_team2;
   private int m_score1 = 0;
   private int m_score2 = 0;
   
   // Match runner helpers
   private boolean m_finished = false;
   private boolean m_processed1 = false;
   private boolean m_processed2 = false;
   
   public Match(Country country) {
      this(new Team("", "", country), new Team("", "", country), country);
   }
   
   public Match(Team team1, Team team2, Country country) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_country = country;
      
      m_team1 = team1;
      m_team2 = team2;
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
      // needs to be implemented
      // don't forget to change m_finished to true
      return true;
   }
   
   public boolean processMatch() {
      if (!m_finished)
         return false;
      
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
}