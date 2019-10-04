package com.lucasizumi.elo7.marsprobe.response;

import java.util.ArrayList;
import java.util.List;

import com.lucasizumi.elo7.marsprobe.domain.Probe;

public class MarsProbesResponse {

	private List<ProbeResponse> probesResponse;

	public MarsProbesResponse() {
		
	}
	
	public MarsProbesResponse(List<Probe> probes) {
		probesResponse = new ArrayList<ProbeResponse>();
		probes.forEach(probe -> this.getProbesResponse().add(new ProbeResponse(probe.getIdentification(), probe.getWindDirection(), probe.getPosition().toString())));
	}
	
	public List<ProbeResponse> getProbesResponse() {
		return probesResponse;
	}
	
}
