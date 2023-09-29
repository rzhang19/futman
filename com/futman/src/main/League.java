package com.futman.src.main;

public class League extends Competition{
	private static final int DEFAULT_FACE_TIMES = 2;
	
	// number of times a team faces every other team during the League
	private int m_faceTimes;
	
	public League(Country country) {
		this("", country);
	}
	
	public League(String name, Country country) {
		this(name, country, DEFAULT_MAX_TEAMS);
	}
	
	public League(String name, Country country, int maxTeams) {
		this(name, country, maxTeams, DEFAULT_FACE_TIMES);
	}
	
	public League(String name, Country country, int maxTeams, int faceTimes) {
		super(name, country, maxTeams);
		m_faceTimes = faceTimes;
	}

	@Override
	protected boolean calculateMaxMatches() {
		if (m_faceTimes <= 0)
			return false;
		
		if (!this.setMaxMatches((m_teams.length - 1) * m_faceTimes))
			return false;
		
		return true;
	}
}
