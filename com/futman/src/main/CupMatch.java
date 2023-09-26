package com.futman.src.main;

public class CupMatch extends Match {
	
	public CupMatch(Team team1, Team team2, Country country) {
		this (team1, team2, country, true);
	}
	
	public CupMatch(Team team1, Team team2, Country country, boolean needOvertime) {
		super (team1, team2, country, needOvertime);
	}
}
