package com.futman.model;

public class CupMatch extends Match {
	public CupMatch(Country country) {
		super(country);
	}

	public CupMatch(Team team1, Team team2, Country country) {
		super(team1, team2, country, true);
	}
}
