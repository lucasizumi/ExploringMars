package com.lucasizumi.elo7.marsprobe.enumeration;

public enum Direction {
	LEFT('L'), RIGHT('R'), FOWARD('M');

	private char alias;

	private Direction(char alias) {
		this.alias = alias;
	}

	public char getAlias() {
		return alias;
	}

	public void setAlias(char alias) {
		this.alias = alias;
	}

	public static Direction getByAlias(char alias) {
		switch (alias) {
		case 'L':
			return LEFT;
		case 'R':
			return RIGHT;
		case 'M':
			return FOWARD;
		default:
			throw new IllegalArgumentException("Direction not found " + String.valueOf(alias));
		}
	}

}
