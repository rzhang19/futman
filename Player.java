import java.util.Date;

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
   private Date m_dob;
   
   private Position m_position;
   
   // Seasonal statistics
   private int m_seasonGoals = 0;
   private int m_seasonAssists = 0;
   private int m_seasonSaves = 0;
   private int m_seasonTackles = 0;
   
   public Player() {
      this("","","",new Date(1900,1,1));
   }
   
   public Player(String firstName, String lastName, String nickName, Date dob) {
      this(firstName, lastName, nickName, dob, Position.NONE);
   }
   
   public Player(String firstName, String lastName, String nickName, Date dob, Position position) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_firstName = firstName;
      m_lastName = lastName;
      m_nickName = nickName;
      
      m_dob = dob;
      
      m_position = position;
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
   
   public Date getDOB() {
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