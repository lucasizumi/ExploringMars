package com.lucasizumi.elo7.marsprobe.domain;

import com.lucasizumi.elo7.marsprobe.enumeration.WindRose;

public class Probe {

	private String identification;

	private WindRose windDirection;
	
	private Position position;

	public Probe() {

	}

	public Probe(String identificacao, Position position) {
		this(identificacao, WindRose.N, position);
	}

	public Probe(String identification, WindRose windDirection, Position position) {
		this.identification = identification;
		this.windDirection = windDirection;
		this.position = position;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public WindRose getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(WindRose windDirection) {
		this.windDirection = windDirection;
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
