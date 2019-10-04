package com.lucasizumi.elo7.marsprobe.dto;

public class MarsDto {

	private int limitAxisX;
	
	private int limitAxisY;
	
	public MarsDto() {
		
	}
	
	public MarsDto(int limitAxisX, int limitAxisY) {
		this.limitAxisX = limitAxisX;
		this.limitAxisY = limitAxisY;
	}

	public int getLimitAxisX() {
		return limitAxisX;
	}

	public int getLimitAxisY() {
		return limitAxisY;
	}
	
}
