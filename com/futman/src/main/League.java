package com.futman.src.main;

public class League extends Competition{
	private static final int DEFAULT_FACE_TIMES = 2;
	
	// number of times a team faces every other team during the League
	private int m_faceTimes;
	
	private int m_tier;
	
	public League(String name, Country country, int tier) {
		this(name, country, tier, DEFAULT_MAX_TEAMS);
	}
	
	public League(String name, Country country, int tier, int maxTeams) {
		this(name, country, tier, maxTeams, DEFAULT_FACE_TIMES);
	}
	
	public League(String name, Country country, int tier, int maxTeams, int faceTimes) {
		super(name, country, maxTeams);
		m_faceTimes = faceTimes;
		m_tier = tier;
	}

	@Override
	protected boolean calculateMaxMatches() {
		if (m_faceTimes <= 0)
			return false;
		
		if (!this.setMaxMatches((m_teams.length - 1) * m_faceTimes * m_teams.length / 2))
			return false;
		
		return true;
	}
	
	public int getFaceTimes() {
		return m_faceTimes;
	}
	
	public int getTier() {
		return m_tier;
	}
}
