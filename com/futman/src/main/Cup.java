package com.futman.src.main;

public class Cup extends Competition {
	private int m_elimTimes;
	private boolean m_includeThirdPlace;
	
	public Cup(Country country) {
		this("", country);
	}
	
	public Cup(String name, Country country) {
		this(name, country, DEFAULT_MAX_TEAMS);
	}
	
	public Cup(String name, Country country, int maxTeams) {
		this(name, country, maxTeams, 1, true);	// default to single elimination, with third place
	}
	
	public Cup(String name, Country country, int maxTeams, int elimTimes, boolean includeThirdPlace) {
		super(name, country, maxTeams);
		
		m_elimTimes = elimTimes;
		m_includeThirdPlace = includeThirdPlace;
	}

	@Override
	protected boolean calculateMaxMatches() {
		m_maxMatchesSeason = MAX_SIZE * m_elimTimes;
		
		if (!m_includeThirdPlace)
			m_maxMatchesSeason--;
		
		return true;
	}
}
