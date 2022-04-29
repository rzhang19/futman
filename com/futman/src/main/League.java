package com.futman.src.main;

public class League extends Competition{
	public League(Country country) {
		this("", country);
	}
	
	public League(String name, Country country) {
		this(name, country, DEFAULT_MAX_TEAMS);
	}
	
	public League(String name, Country country, int maxTeams) {
		super(name, country, maxTeams);
	}

	@Override
	protected boolean calculateMaxMatches() {
		// TODO Auto-generated method stub
		return false;
	}
}
