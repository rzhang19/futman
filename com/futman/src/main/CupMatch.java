package com.futman.src.main;

public class CupMatch extends Match {
	private int m_overtimePeriod = 0;
	private CupMatch m_nextCupMatch;
	
	public CupMatch(Team team1, Team team2, Country country) {
		this (team1, team2, country, true);
	}
	
	public CupMatch(Team team1, Team team2, Country country, boolean needOvertime) {
		super (team1, team2, country, needOvertime);
	}
	
	public CupMatch(Team team1, Team team2, Country country, boolean needOvertime, CupMatch next) {
		super(team1, team2, country, needOvertime);
		m_nextCupMatch = next;
	}
	
	public boolean setTeam1(Team team1) {
		return super.setTeam1(team1);
	}
	
	public boolean setTeam2(Team team2) {
		return super.setTeam2(team2);
	}
	
	public boolean runMatch() {
		if (!super.runMatch())
			return false;
		
	    while (m_overtimePeriod < NUM_OVERTIME) {
	    	while (m_halfMinute <= OVERTIME_LENGTH[m_overtimePeriod]) {
	    		// set proper minutes
	    		int addMin = rand.nextInt(EVENT_MAX) + 1;
	    		m_halfMinute += addMin;
	    		
	    		if (m_halfMinute >= OVERTIME_LENGTH[m_overtimePeriod]) {
	    			m_halfMinute = OVERTIME_LENGTH[m_overtimePeriod] + 1;
	    		}
	    		
	    		m_minute = m_halfMinute;
	    		for (int x = 0; x < m_overtimePeriod; x++) {
	    			m_minute += OVERTIME_LENGTH[x];
	    		}
	    		
	    		boolean poss = m_team1Poss;
	    		
	    		// need to replace all getOverall() with its proper fields, as dictated by the comments below
	    		// midfield vs midfield
	    		if (poss == m_team1Poss) {
	    			int attTeam;
	    			int defTeam;
	    			
	    			if (m_team1Poss) {
	    				attTeam = m_team1.getMidOverall() + 1;
	    				defTeam = m_team2.getMidOverall() + 1;
	    			}
	    			
	    			else {
	    				attTeam = m_team2.getMidOverall() + 1;
	    				defTeam = m_team1.getMidOverall() + 1;
	    			}
	    			
	    			int randomAtt = rand.nextInt(attTeam);
	    			int randomDef = rand.nextInt(defTeam);
	    			
	    			if (randomAtt <= randomDef) {
	    				m_team1Poss = !m_team1Poss;
	    			}
	    		}
	    		
	    		// att vs def
	    		if (poss == m_team1Poss) {
	    			int attTeam;
	    			int defTeam;
	    			
	    			if (m_team1Poss) {
	    				attTeam = m_team1.getAttOverall() + 1;
	    				defTeam = m_team2.getDefOverall() + 1;
	    			}
				  
	    			else {
	    				attTeam = m_team2.getAttOverall() + 1;
	    				defTeam = m_team1.getDefOverall() + 1;
	    			}
				  
	    			int randomAtt = rand.nextInt(attTeam);
	    			int randomDef = rand.nextInt(defTeam);
	    			
	    			if (randomAtt <= randomDef) {
	    				m_team1Poss = !m_team1Poss;
	    			}
	    		}
			  
	    		// att vs gk
	    		if (poss == m_team1Poss) {
	    			int attTeam;
	    			int defTeam;
	    			
	    			if (m_team1Poss) {
	    				attTeam = m_team1.getAttOverall() + 1;
	    				defTeam = m_team2.getGKOverall() + 1;
	    			}
	    			
	    			else {
	    				attTeam = m_team2.getAttOverall() + 1;
	    				defTeam = m_team1.getGKOverall() + 1;
	    			}
	    			
	    			int randomAtt = rand.nextInt(attTeam);
	    			int randomDef = rand.nextInt(defTeam);
	    			
	    			if (randomAtt > randomDef) {
	    				if (m_team1Poss) {
	    					m_score1++;
	    				}
	    				
	    				else {
	    					m_score2++;
	    				}
	    			}
	    			
	    			m_team1Poss = !m_team1Poss;
	    		}
	    	}
	    	
	    	m_overtimePeriod++;
	    	m_halfMinute = 0;
	    }
	    
	    if (m_score1 == m_score2) {
	    	if (!runPenalties())
	    		return false;
	    }
	    
	    if (!this.finishMatch()) {
	    	System.err.println("src.main.CupMatch Error: unable to finish CupMatch");
	    	return false;
	    }
	    
	    return true;
	}

	private boolean runPenalties() {
		return true;
	}
	
	public boolean processMatch() {
		if (!super.processMatch()) {
			System.err.println("src.main.CupMatch Error: unable to process CupMatch");
			return false;
		}
		Team winner = super.getWinner();
		if (winner == null) {
			System.err.println("src.main.CupMatch Error: no winner returned in CupMatch");
			return false;
		}
		
		if (m_nextCupMatch.getTeam1() == null) {
			return m_nextCupMatch.setTeam1(winner);
		}
		
		else if (m_nextCupMatch.getTeam2() == null) {
			return m_nextCupMatch.setTeam2(winner);
		}
		
		else {
			System.err.println("src.main.CupMatch Error: all teams on next cup match already filled");
			return false;
		}
	}
}
