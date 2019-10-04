package com.lucasizumi.elo7.marsprobe.response;

import com.lucasizumi.elo7.marsprobe.enumeration.WindRose;

public class ProbeResponse {

	private String identification;
	
	private String direction;
	
	private String position;

	public ProbeResponse() {

	}
	
	public ProbeResponse(String identification, WindRose direction, String position) {
		this.identification = identification;
		this.direction = direction.name();
		this.position = position;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}
