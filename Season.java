public class Season {
   private static int ID_COUNTER = 1;
   
   // identifier
   private int m_id;
   
   private Competition m_competition;
   private int m_year;
   
   private boolean m_completed;
   
   public Season(Competition competition) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_competition = competition;
   }
   
   public boolean equals(Season other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return m_competition.getName() + " (" + m_year + ")";
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