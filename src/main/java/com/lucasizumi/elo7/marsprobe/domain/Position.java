package com.lucasizumi.elo7.marsprobe.domain;

public class Position {

	private int xAxis;

	private int yAxis;

	public Position() {
	}

	public Position(int xAxis, int yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public int getxAxis() {
		return xAxis;
	}

	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}

	@Override
	public String toString() {
		return "(" + this.xAxis + "," + this.yAxis + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xAxis;
		result = prime * result + yAxis;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (xAxis != other.xAxis)
			return false;
		if (yAxis != other.yAxis)
			return false;
		return true;
	}

}
