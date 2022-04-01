public abstract class Competition {
   private static int ID_COUNTER = 1;
   
   // identifiers
   private int m_id;
   private String m_name;
   
   private static int m_currentYear = 2022;
   
   private Country m_country;
   
   public Competition(Country country) {
      this ("", country);
   }
   
   public Competition(String name, Country country) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_name = name;
      
      m_country = country;
   }
   
   public int getID() {
      return m_id;
   }
   
   public String getName() {
      return m_name;
   }
   
   public Country getCountry() {
      return m_country;
   }
   
   public boolean equals(Competition other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return m_name + " (" + m_country.getName() + ")";
   }
}