public class Season {
   private static int ID_COUNTER = 1;
   
   private int m_id;
   
   private int m_year;
   
   private boolean m_completed;
   
   public Season() {
      
   }
   
   public int getYear() {
      return m_year;
   }
   
   public boolean getCompleted() {
      return m_completed;
   }
}