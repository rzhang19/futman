public class Season {
   private static int ID_COUNTER = 1;
   
   // identifier
   private int m_id;
   
   private int m_year;
   
   private boolean m_completed;
   
   public Season() {
      m_id = ID_COUNTER;
      ID_COUNTER++;
   }
   
   public boolean equals(Season other) {
      return m_id == other.getID();
   }
   
   public int getID() {
      return m_id;
   }
   
   public int getYear() {
      return m_year;
   }
   
   public boolean getCompleted() {
      return m_completed;
   }
}