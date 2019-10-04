package com.lucasizumi.elo7.marsprobe.dto;

public class ProbeDto {

	private String identification;

	private int initialAxisX;

	private int initialAxisY;

	public ProbeDto() {
		
	}
	
	public ProbeDto(String identification, int initialAxisX, int initialAxisY) {
		this.identification = identification;
		this.initialAxisX = initialAxisX;
		this.initialAxisY = initialAxisY;
	}

	public String getIdentification() {
		return identification;
	}

	public int getInitialAxisX() {
		return initialAxisX;
	}

	public int getInitialAxisY() {
		return initialAxisY;
	}

}
