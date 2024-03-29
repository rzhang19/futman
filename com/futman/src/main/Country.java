package com.futman.src.main;


public class Country {
   private static int ID_COUNTER = 1;
   private static String[] ALL_CODES = new String[200];
   
   // identifiers
   private int m_id;
   private String m_name;
   private String m_code;
   
   public Country(String name, String code) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
   
      m_name = name;
      m_code = code;
   }
   
   public int getID() {
      return m_id;
   }
   
   public boolean equals(Country other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return m_name + " (" + m_code + ")";
   }
   
   public String getName() {
      return m_name;
   }
   
   public String getCode() {
      return m_code;
   }
   
   public boolean isValid() {
	   if (m_name.length() <= 0) {
		   System.err.println("src.main.Country Error: invalid Country long name");
		   return false;
	   }
	   
	   if (m_code.length() < 2 || m_code.length() > 3) {
		   System.err.println("src.main.Country Error: invalid Country code");
		   return false;
	   }
	   
	   return true;
   }
}