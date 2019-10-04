package com.lucasizumi.elo7.marsprobe.response;

public class MarsResponse {

	private String surfaceLimits;
	
	private int numberOfProbes;

	public MarsResponse() {
		
	}
	
	public MarsResponse(String surfaceLimits, int numberOfProbes) {
		this.surfaceLimits = surfaceLimits;
		this.numberOfProbes = numberOfProbes;
	}
	
	public String getSurfaceLimits() {
		return surfaceLimits;
	}

	public void setSurfaceLimits(String surfaceLimits) {
		this.surfaceLimits = surfaceLimits;
	}

	public int getNumberOfProbes() {
		return numberOfProbes;
	}

	public void setNumberOfProbes(int numberOfProbes) {
		this.numberOfProbes = numberOfProbes;
	}
	
}
