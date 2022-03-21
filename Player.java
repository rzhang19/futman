import java.util.Date;

public class Player {
   private static int ID_COUNTER = 1;
   
   // Personally identifiable information
   private int m_id;
   private String m_firstName;
   private String m_lastName;
   private String m_nickName;
   private Date m_dob;
   
   public Player() {
      this("","","",new Date(1900,1,1));
   }
   
   public Player(String firstName, String lastName, String nickName, Date dob) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_firstName = firstName;
      m_lastName = lastName;
      m_nickName = nickName;
      
      m_dob = dob;
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
}