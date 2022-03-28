public abstract class Competition {
   private static int ID_COUNTER = 1;
   
   // identifiers
   private int m_id;
   
   private static int m_currentYear = 2022;
   
   public Competition() {
      m_id = ID_COUNTER;
      ID_COUNTER++;
   }
   
   public int getID() {
      return m_id;
   }
   
   public boolean equals(Competition other) {
      return m_id == other.getID();
   }
}