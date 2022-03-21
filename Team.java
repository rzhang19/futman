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
   
   public int getID() {
      return m_ID;
   }
   
   public String getLongName() {
      return m_longName;
   }
   
   public String getShortName() {
      return m_shortName;
   }
   
   public int getSeasonWins() {
      return m_seasonWins;
   }
   
   public int getSeasonDraws() {
      return m_seasonDraws;
   }
   
   public int getSeasonLosses() {
      return m_seasonLosses;
   }
}