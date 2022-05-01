package com.futman.src.main;

public class Cup extends Competition {
	public Cup(Country country) {
		this("", country);
	}
	
	public Cup(String name, Country country) {
		this(name, country, DEFAULT_MAX_TEAMS);
	}
	
	public Cup(String name, Country country, int maxTeams) {
		super(name, country, maxTeams);
	}

	@Override
	protected boolean calculateMaxMatches() {
		setMaxMatches(0);
		return true;
	}
}
