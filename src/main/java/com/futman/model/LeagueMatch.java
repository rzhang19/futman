package com.futman.model;

public class LeagueMatch extends Match {
	public LeagueMatch(Country country) {
		super(country);
	}

	public LeagueMatch(Team team1, Team team2, Country country) {
		super(team1, team2, country, false);
	}
}
