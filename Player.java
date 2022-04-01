import java.time.*;

public class Player {
   private static int ID_COUNTER = 1;
   
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
   
   public Player() {
      this("","","",LocalDate.of(1900,1,1));
   }
   
   public Player(String firstName, String lastName, String nickName, LocalDate dob) {
      this(firstName, lastName, nickName, dob, Position.NONE);
   }
   
   public Player(String firstName, String lastName, String nickName, LocalDate dob, Position position) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_firstName = firstName;
      m_lastName = lastName;
      
      if (m_nickName != null && m_nickName.length() > 0)
         m_nickName = nickName;
      
      m_dob = dob;
      
      m_position = position;
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
}