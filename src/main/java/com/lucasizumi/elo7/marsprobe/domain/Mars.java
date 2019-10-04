package com.lucasizumi.elo7.marsprobe.domain;

import java.util.ArrayList;
import java.util.List;

public class Mars {

	private Position surfaceLimits;
	
	private List<Probe> probes;

	public Mars(Position surfaceLimits) {
		this.surfaceLimits = surfaceLimits;
		probes = new ArrayList<Probe>();
	}

	public Position getSurfaceLimits() {
		return surfaceLimits;
	}

	public void setSurfaceLimits(Position surfaceLimits) {
		this.surfaceLimits = surfaceLimits;
	}

	public List<Probe> getProbes() {
		return probes;
	}

	public void setProbes(List<Probe> probes) {
		this.probes = probes;
	}
}
