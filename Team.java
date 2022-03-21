public class Team {
   private static int ID_COUNTER = 1;
   
   // Identifiable values
   private int m_ID;
   private String m_longName;
   private String m_shortName;
   
   // Seasonal values
   private int m_seasonWins = 0;
   private int m_seasonDraws = 0;
   private int m_seasonLosses = 0;
   
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
