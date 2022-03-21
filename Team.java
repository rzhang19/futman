public class Team {
   private static int ID_COUNTER = 1;
   
   private int m_ID;
   private String m_longName;
   private String m_shortName;
   
   public Team() {
      this ("", "");
   }
   
   public Team(String longName, String shortName) {
      m_ID = ID_COUNTER;
      ID_COUNTER++;
      
      m_longName = longName;
      m_shortName = shortName;
   }
}