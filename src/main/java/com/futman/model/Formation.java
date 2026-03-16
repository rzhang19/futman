package com.futman.model;

public enum Formation {
	F_4_4_2(4, 4, 2),
	F_4_3_3(4, 3, 3),
	F_3_5_2(3, 5, 2),
	F_3_4_3(3, 4, 3),
	F_5_3_2(5, 3, 2),
	F_5_2_3(5, 2, 3),
	F_4_5_1(4, 5, 1);

	private static final int TOTAL_VALID_OUTFIELD = 10;

	private final int defenders;
	private final int midfielders;
	private final int attackers;

	Formation(int def, int mid, int att) {
		this.defenders = def;
		this.midfielders = mid;
		this.attackers = att;
	}

	public int getDefenders() {
		return defenders;
	}

	public int getMidfielders() {
		return midfielders;
	}

	public int getAttackers() {
		return attackers;
	}

	public int getTotalOutfield() {
		return defenders + midfielders + attackers;
	}

	public boolean isValid() {
		return getTotalOutfield() == TOTAL_VALID_OUTFIELD;
	}

	public String getDisplayName() {
		return defenders + "-" + midfielders + "-" + attackers;
	}
}
