public class Team {
   private static int ID_COUNTER = 1;
   private static final int MAX_PLAYERS = 50;
   
   // Identifiable values
   private int m_id;
   private String m_longName;
   private String m_shortName;
   private Country m_country;
   
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
   
   // Players
   private Player[] m_players = new Player[MAX_PLAYERS];
   private int m_playerCount = 0;
   
   public Team(String longName, String shortName, Country country) {
      m_id = ID_COUNTER;
      ID_COUNTER++;
      
      m_longName = longName;
      m_shortName = shortName;
      
      m_country = country;
   }
   
   public int getID() {
      return m_id;
   }
   
   public boolean equals(Team other) {
      return m_id == other.getID();
   }
   
   public String toString() {
      return m_longName + " (" + m_country.getCode() + ")";
   }
   
   public String getName() {
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
      m_overall = (m_attOverall + m_midOverall + m_defOverall + m_gkOverall) / 4;
      return true;
   }
   
   public boolean processMatch(Match match) {
      int score1 = match.getScore1();
      int score2 = match.getScore2();
      if (match.getTeam1().equals(this)) {
         if (score1 > score2)
            m_seasonWins++;
         else if (score1 == score2)
            m_seasonDraws++;
         else
            m_seasonLosses++;
         
         // add score implementation here
      }
      
      else {
         if (score2 > score1)
            m_seasonWins++;
         else if (score2 == score1)
            m_seasonDraws++;
         else
            m_seasonLosses++;
         
         // add score implementation here
      }
      
      return true;
   }
   
   public boolean unprocessMatch(Match match) {
      // to be implemented
      return true;
   }
   
   public boolean addPlayers(Player[] players) {
      int addable = players.length;
      
      for (int x = 0; x < players.length; x++) {
         if (findPlayer(players[x])) {
            addable--;
         }
      }
      
      if (m_playerCount + addable > MAX_PLAYERS) {
         return false;
      }
      
      for (int x = 0; x < players.length; x++) {
         if (!findPlayer(players[x])) {
            players[m_playerCount] = players[x];
            m_playerCount++;
         }
      }
      
      return true;
   }
   
   public int getPlayerCount() {
      return m_playerCount;
   }
   
   public Player[] getPlayers() {
      return m_players;
   }
   
   public boolean addPlayer(Player player) {
      if (m_playerCount < MAX_PLAYERS) {
         m_players[m_playerCount] = player;
         m_playerCount++;
         
         return true;
      }
      
      return false;
   }
   
   public boolean findPlayer(Player player) {
      for (int x = 0; x < m_players.length; x++) {
         if (m_players[x].equals(player)) {
            return true;
         }
      }
      
      return false;
   }
   
   public boolean removePlayer(Player player) {
      if (!findPlayer(player))
         return false;
      
      int index = 0;
      boolean found = false;
      
      while (index < m_playerCount && !found) {
         if (m_players[index].equals(player)) {
            found = true;
         }
         
         else
            index++;
      }
      
      m_players[index] = m_players[m_playerCount - 1];
      m_playerCount--;
      
      return true;
   }
}
