public class Team {
   private static int ID_COUNTER = 1;
   
   // Identifiable values
   private int m_id;
   private String m_longName;
   private String m_shortName;
   
   // Seasonal values
   private int m_seasonWins = 0;
   private int m_seasonDraws = 0;
   private int m_seasonLosses = 0;
   
   // Attribute values
   private int m_attOverall = 0;
   private int m_midOverall = 0;
   private int m_defOverall = 0;
   private int m_gkOverall = 0;
   private int m_overall = 0;
   
   public Team() {
      this ("", "");
   }
   
   public Team(String longName, String shortName) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_longName = longName;
      m_shortName = shortName;
   }
   
   public int getID() {
      return m_id;
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
   
   public int getAttOverall() {
      return m_attOverall;
   }
   
   public int getMidOverall() {
      return m_midOverall;
   }
   
   public int getDefOverall() {
      return m_defOverall;
   }
   
   public int getGKOverall() {
      return m_gkOverall;
   }
   
   public int getOverall() {
      if (calculateOverall())
         return m_overall;
      System.err.println("Error calculating overall");
      return -1;
   }
   
   private boolean calculateOverall() {
      // to be done in FUTMAN-22
      return true;
   }
}
