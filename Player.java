import java.time.*;

public class Player {
   private static int ID_COUNTER = 1;
   
   // default attribute values if not set through constructor
   private static final int DEFAULT_ATTR_VALUES = 50;
   
   // weights for each position
   private static final int ATT_SPEED_WEIGHT = 1;
   private static final int ATT_SHOOT_WEIGHT = 1;
   private static final int ATT_TACKL_WEIGHT = 1;
   private static final int ATT_PASS_WEIGHT = 1;
   private static final int ATT_REACT_WEIGHT = 1;
   private static final int ATT_BLOCK_WEIGHT = 1;
   
   private static final int MID_SPEED_WEIGHT = 1;
   private static final int MID_SHOOT_WEIGHT = 1;
   private static final int MID_TACKL_WEIGHT = 1;
   private static final int MID_PASS_WEIGHT = 1;
   private static final int MID_REACT_WEIGHT = 1;
   private static final int MID_BLOCK_WEIGHT = 1;
   
   private static final int DEF_SPEED_WEIGHT = 1;
   private static final int DEF_SHOOT_WEIGHT = 1;
   private static final int DEF_TACKL_WEIGHT = 1;
   private static final int DEF_PASS_WEIGHT = 1;
   private static final int DEF_REACT_WEIGHT = 1;
   private static final int DEF_BLOCK_WEIGHT = 1;
   
   private static final int GK_SPEED_WEIGHT = 1;
   private static final int GK_SHOOT_WEIGHT = 1;
   private static final int GK_TACKL_WEIGHT = 1;
   private static final int GK_PASS_WEIGHT = 1;
   private static final int GK_REACT_WEIGHT = 1;
   private static final int GK_BLOCK_WEIGHT = 1;
   
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
      
      if (m_nickName != null && m_nickName.length() > 0)
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
      return getName() + " (" + m_position + ")";
   }
   
   public String getFirstName() {
      return m_firstName;
   }
   
   public String getLastName() {
      return m_lastName;
   }
   
   public String getNickName() {
      return m_nickName;
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
   
   public Position getPosition() {
      return m_position;
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
      // needs to be implemented
      return m_overall;
   }
   
   private boolean calculateOverall() {
      // needs to be implemented
      return true;
   }
}