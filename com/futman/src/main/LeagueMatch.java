package com.futman.src.main;

public class LeagueMatch extends Match {
	public LeagueMatch(Country country) {
		this (new Team("", "", country), new Team("", "", country), country);
	}
	
	public LeagueMatch(Team team1, Team team2, Country country) {
		super (team1, team2, country, false);
	}
	
	public boolean runMatch() {
		return super.runMatch();
	}
}
