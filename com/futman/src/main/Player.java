package com.futman.src.main;


import java.time.*;

public class Player {
   private static int ID_COUNTER = 1;
   
   // default attribute values if not set through constructor
   private static final int DEFAULT_ATTR_VALUES = 50;
   
   // weights for each position
   private static final int ATT_SPEED_WEIGHT = 4;
   private static final int ATT_SHOOT_WEIGHT = 5;
   private static final int ATT_TACKL_WEIGHT = 1;
   private static final int ATT_PASS_WEIGHT = 2;
   private static final int ATT_REACT_WEIGHT = 1;
   private static final int ATT_BLOCK_WEIGHT = 1;
   
   private static final int MID_SPEED_WEIGHT = 1;
   private static final int MID_SHOOT_WEIGHT = 2;
   private static final int MID_TACKL_WEIGHT = 3;
   private static final int MID_PASS_WEIGHT = 5;
   private static final int MID_REACT_WEIGHT = 4;
   private static final int MID_BLOCK_WEIGHT = 1;
   
   private static final int DEF_SPEED_WEIGHT = 1;
   private static final int DEF_SHOOT_WEIGHT = 1;
   private static final int DEF_TACKL_WEIGHT = 5;
   private static final int DEF_PASS_WEIGHT = 2;
   private static final int DEF_REACT_WEIGHT = 3;
   private static final int DEF_BLOCK_WEIGHT = 5;
   
   private static final int GK_SPEED_WEIGHT = 1;
   private static final int GK_SHOOT_WEIGHT = 1;
   private static final int GK_TACKL_WEIGHT = 2;
   private static final int GK_PASS_WEIGHT = 4;
   private static final int GK_REACT_WEIGHT = 5;
   private static final int GK_BLOCK_WEIGHT = 5;
   
   private static final int NONE_SPEED_WEIGHT = 1;
   private static final int NONE_SHOOT_WEIGHT = 1;
   private static final int NONE_TACKL_WEIGHT = 1;
   private static final int NONE_PASS_WEIGHT = 1;
   private static final int NONE_REACT_WEIGHT = 1;
   private static final int NONE_BLOCK_WEIGHT = 1;
   
   // public enumeration of all Positions for a Player
   public enum Position {
      ATT,
      MID,
      DEF,
      GK,
      NONE;
   }
   
   // Personally identifiable information
   private int m_id;
   private String m_firstName;
   private String m_lastName;
   private String m_nickName;
   private LocalDate m_dob;
   
   private Position m_position;
   
   // Seasonal statistics
   private int m_seasonGoals = 0;
   private int m_seasonAssists = 0;
   private int m_seasonSaves = 0;
   private int m_seasonTackles = 0;
   
   // Game attributes
   private int m_overall;
   
   private int m_speed;
   private int m_shooting;
   private int m_tackling;
   private int m_passing;
   private int m_reactions;
   private int m_blocking;
   
   public Player() {
      this("","","",LocalDate.of(1900,1,1));
   }
   
   public Player(String firstName, String lastName, LocalDate dob) {
	   this (firstName, lastName, "", dob);
   }
   
   public Player(String firstName, String lastName, String nickName, LocalDate dob) {
      this(firstName, lastName, nickName, dob, Position.NONE);
   }
   
   public Player(String firstName, String lastName, String nickName, LocalDate dob, Position position) {
      this(firstName, lastName, nickName, dob, Position.NONE, DEFAULT_ATTR_VALUES, DEFAULT_ATTR_VALUES, DEFAULT_ATTR_VALUES, DEFAULT_ATTR_VALUES, DEFAULT_ATTR_VALUES, DEFAULT_ATTR_VALUES);
   }
   
   public Player(String firstName, String lastName, String nickName, LocalDate dob, Position position, int speed, int shooting, int tackling, int passing, int reactions, int blocking) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_firstName = firstName;
      m_lastName = lastName;
      
      if (nickName != null && nickName.length() > 0)
         m_nickName = nickName;
      
      m_dob = dob;
      
      m_position = position;
      
      m_speed = speed;
      m_shooting = shooting;
      m_tackling = tackling;
      m_passing = passing;
      m_reactions = reactions;
      m_blocking = blocking;
   }
   
   public int getID() {
      return m_id;
   }
   
   public boolean equals(Player other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return getName() + " (" + getPosition() + ")";
   }
   
   public String getFirstName() {
      return m_firstName;
   }
   
   public String getLastName() {
      return m_lastName;
   }
   
   public String getNickName() {
	  if (m_nickName != null && m_nickName.length() > 0)
		  return m_nickName;
	  else
		  return "";
   }
   
   public String getName() {
      if (m_nickName != null && m_nickName.length() > 0)
         return m_nickName;
      else
         return m_firstName + " " + m_lastName;
   }
   
   public LocalDate getDOB() {
      return m_dob;
   }
   
   public Position getPositionEnum() {
      return m_position;
   }
   
   public String getPosition() {
	   if (m_position == Position.ATT)
		   return "ATT";
	   else if (m_position == Position.MID)
		   return "MID";
	   else if (m_position == Position.DEF)
		   return "DEF";
	   else if (m_position == Position.GK)
		   return "GK";
	   else
		   return "NONE";
   }
   
   public int getSeasonGoals() {
      return m_seasonGoals;
   }
   
   public int getSeasonAssists() {
      return m_seasonAssists;
   }
   
   public int getSeasonSaves() {
      return m_seasonSaves;
   }
   
   public int getSeasonTackles() {
      return m_seasonTackles;
   }
   
   public int getSpeed() {
      return m_speed;
   }
   
   public int getShooting() {
      return m_shooting;
   }
   
   public int getTackling() {
      return m_tackling;
   }
   
   public int getPassing() {
      return m_passing;
   }
   
   public int getReactions() {
      return m_reactions;
   }
   
   public int getBlocking() {
      return m_blocking;
   }
   
   public int getOverall() {
	  if (!calculateOverall())
		  System.err.println("Error calculating overall!");
	  
	  return m_overall;
   }
   
   private boolean calculateOverall() {
	   int m_speedWeighted;
	   int m_shootWeighted;
	   int m_tacklWeighted;
	   int m_passWeighted;
	   int m_reactWeighted;
	   int m_blockWeighted;
	   
	   int weightSum = 6;
	   
	   if (getPositionEnum() == Position.ATT) {
		   m_speedWeighted = ATT_SPEED_WEIGHT * m_speed;
		   m_shootWeighted = ATT_SHOOT_WEIGHT * m_shooting;
		   m_tacklWeighted = ATT_TACKL_WEIGHT * m_tackling;
		   m_passWeighted = ATT_PASS_WEIGHT * m_passing;
		   m_reactWeighted = ATT_REACT_WEIGHT * m_reactions;
		   m_blockWeighted = ATT_BLOCK_WEIGHT * m_blocking;
		   
		   weightSum = ATT_SPEED_WEIGHT + ATT_SHOOT_WEIGHT + ATT_TACKL_WEIGHT + ATT_PASS_WEIGHT + ATT_REACT_WEIGHT + ATT_BLOCK_WEIGHT;
	   }
	   
	   else if (getPositionEnum() == Position.MID) {
		   m_speedWeighted = MID_SPEED_WEIGHT * m_speed;
		   m_shootWeighted = MID_SHOOT_WEIGHT * m_shooting;
		   m_tacklWeighted = MID_TACKL_WEIGHT * m_tackling;
		   m_passWeighted = MID_PASS_WEIGHT * m_passing;
		   m_reactWeighted = MID_REACT_WEIGHT * m_reactions;
		   m_blockWeighted = MID_BLOCK_WEIGHT * m_blocking;
		   
		   weightSum = MID_SPEED_WEIGHT + MID_SHOOT_WEIGHT + MID_TACKL_WEIGHT + MID_PASS_WEIGHT + MID_REACT_WEIGHT + MID_BLOCK_WEIGHT;
	   }
	   
	   else if (getPositionEnum() == Position.DEF) {
		   m_speedWeighted = DEF_SPEED_WEIGHT * m_speed;
		   m_shootWeighted = DEF_SHOOT_WEIGHT * m_shooting;
		   m_tacklWeighted = DEF_TACKL_WEIGHT * m_tackling;
		   m_passWeighted = DEF_PASS_WEIGHT * m_passing;
		   m_reactWeighted = DEF_REACT_WEIGHT * m_reactions;
		   m_blockWeighted = DEF_BLOCK_WEIGHT * m_blocking;
		   
		   weightSum = DEF_SPEED_WEIGHT + DEF_SHOOT_WEIGHT + DEF_TACKL_WEIGHT + DEF_PASS_WEIGHT + DEF_REACT_WEIGHT + DEF_BLOCK_WEIGHT;
	   }
	   
	   else if (getPositionEnum() == Position.GK) {
		   m_speedWeighted = GK_SPEED_WEIGHT * m_speed;
		   m_shootWeighted = GK_SHOOT_WEIGHT * m_shooting;
		   m_tacklWeighted = GK_TACKL_WEIGHT * m_tackling;
		   m_passWeighted = GK_PASS_WEIGHT * m_passing;
		   m_reactWeighted = GK_REACT_WEIGHT * m_reactions;
		   m_blockWeighted = GK_BLOCK_WEIGHT * m_blocking;
		   
		   weightSum = GK_SPEED_WEIGHT + GK_SHOOT_WEIGHT + GK_TACKL_WEIGHT + GK_PASS_WEIGHT + GK_REACT_WEIGHT + GK_BLOCK_WEIGHT;
	   }
	   
	   else {
		   m_speedWeighted = NONE_SPEED_WEIGHT * m_speed;
		   m_shootWeighted = NONE_SHOOT_WEIGHT * m_shooting;
		   m_tacklWeighted = NONE_TACKL_WEIGHT * m_tackling;
		   m_passWeighted = NONE_PASS_WEIGHT * m_passing;
		   m_reactWeighted = NONE_REACT_WEIGHT * m_reactions;
		   m_blockWeighted = NONE_BLOCK_WEIGHT * m_blocking;
		   
		   weightSum = NONE_SPEED_WEIGHT + NONE_SHOOT_WEIGHT + NONE_TACKL_WEIGHT + NONE_PASS_WEIGHT + NONE_REACT_WEIGHT + NONE_BLOCK_WEIGHT;
	   }
      
	   m_overall = (m_speedWeighted + m_shootWeighted + m_tacklWeighted + m_passWeighted + m_reactWeighted + m_blockWeighted) / weightSum;
	   
	   return true;
   	}
}