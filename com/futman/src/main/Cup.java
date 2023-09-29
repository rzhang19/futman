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
		if (!m_includeThirdPlace) {
			if (!this.setMaxMatches(MAX_SIZE * m_elimTimes - 1))
				return false;
		}
		
		else {
			if (!this.setMaxMatches(MAX_SIZE * m_elimTimes))
				return false;
		}
		
		return true;
	}
}
