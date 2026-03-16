package com.futman.model;

public class League extends Competition {
	private static final int DEFAULT_FACE_TIMES = 2;

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

		if (!this.setMaxMatches((MAX_SIZE - 1) * m_faceTimes * MAX_SIZE / 2))
			return false;

		return true;
	}

	@Override
	protected int getFaceTimes() {
		return m_faceTimes;
	}

	public int getFaceTimesPublic() {
		return m_faceTimes;
	}

	public int getTier() {
		return m_tier;
	}

	public boolean isValid() {
		if (!(super.isValid())) {
			return false;
		}

		if (m_faceTimes <= 0) {
			System.err.println("model.League Error: must play each team at least once");
			return false;
		}

		if (m_tier <= 0) {
			System.err.println("model.League Error: tier must be positive");
			return false;
		}

		return true;
	}
}
