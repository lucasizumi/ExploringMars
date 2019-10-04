package com.lucasizumi.elo7.marsprobe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasizumi.elo7.marsprobe.domain.Mars;
import com.lucasizumi.elo7.marsprobe.domain.Position;
import com.lucasizumi.elo7.marsprobe.domain.Probe;
import com.lucasizumi.elo7.marsprobe.dto.ProbeDto;
import com.lucasizumi.elo7.marsprobe.engine.MovementEngine;
import com.lucasizumi.elo7.marsprobe.enumeration.Direction;
import com.lucasizumi.elo7.marsprobe.enumeration.WindRose;
import com.lucasizumi.elo7.marsprobe.exception.CollisionException;
import com.lucasizumi.elo7.marsprobe.exception.ItsNotValidWindDirection;
import com.lucasizumi.elo7.marsprobe.exception.LimitViolationException;
import com.lucasizumi.elo7.marsprobe.exception.ProbeNotFoundException;
import com.lucasizumi.elo7.marsprobe.exception.UniqueKeyException;

@Service
public class ExplorationService {

	@Autowired
	private MovementEngine movementEngine;

	public Probe sendProbeToMars(Mars mars, ProbeDto probeDto) throws UniqueKeyException, LimitViolationException, CollisionException {

		Probe probe = new Probe(probeDto.getIdentification(),
				new Position(probeDto.getInitialAxisX(), probeDto.getInitialAxisY()));
		
		existsProbe(mars, probe);

		validatePosition(mars, probe.getPosition());
		
		mars.getProbes().add(probe);

		return probe;
	}

	public Probe moveProbe(Mars mars, String probeIdentifier)
			throws ProbeNotFoundException, ItsNotValidWindDirection, LimitViolationException, CollisionException {

		Probe probe = findProbe(mars, probeIdentifier);

		Position newPosition = movementEngine.move(probe.getPosition(), probe.getWindDirection());
		
		validatePosition(mars, newPosition);
		
		probe.setPosition(newPosition);

		return probe;
	}
	
	public Probe moveProbe(Mars mars, String probeIdentifier, String command)
			throws ProbeNotFoundException, ItsNotValidWindDirection, LimitViolationException, CollisionException {

		Probe probe = findProbe(mars, probeIdentifier);
		
		Position newPosition = probe.getPosition();
		for(char direction : command.toCharArray()) {
			
			Direction commandDirection = Direction.getByAlias(direction);
			
			WindRose newWindDirection = movementEngine.rotate(probe.getWindDirection(), commandDirection);
			
			probe.setWindDirection(newWindDirection);
			
			if(Direction.FOWARD.equals(commandDirection)) {
				newPosition = movementEngine.move(probe.getPosition(), newWindDirection);
				
				validatePosition(mars, newPosition);
				
				probe.setPosition(newPosition);		
			}
			
		}

		return probe;
	}
	
	public Probe rotateProbe(Mars mars, String probeIdentifier, char rotateDirection) throws ProbeNotFoundException, IllegalArgumentException {
		
		Probe probe = findProbe(mars, probeIdentifier);
		
		Direction commandDirection = Direction.getByAlias(rotateDirection);
		
		if(!(commandDirection.equals(Direction.LEFT)) && !(commandDirection.equals(Direction.RIGHT))) {
			throw new IllegalArgumentException("The probe can only rotate right (R) or left (L).");
		}
		
		WindRose windDirection = movementEngine.rotate(probe.getWindDirection(), Direction.getByAlias(rotateDirection));
		
		probe.setWindDirection(windDirection);
		
		return probe;
	}

	public void validatePosition(Mars mars, Position newPosition) throws LimitViolationException, CollisionException {
		checkLimits(mars, newPosition);
		checkOtherProbes(mars, newPosition);
	}

	private Probe findProbe(Mars mars, String probeIdentifier) throws ProbeNotFoundException {
		return findProbeByIdentifier(mars, probeIdentifier)
				.orElseThrow(() -> new ProbeNotFoundException("Probe not found."));
	}

	private Optional<Probe> findProbeByIdentifier(Mars mars, String probeIdentifier) {
		return mars.getProbes().stream().filter(probe -> probe.getIdentification().equals(probeIdentifier)).findFirst();
	}

	private void existsProbe(Mars mars, Probe probe) throws UniqueKeyException {
		if (mars.getProbes().stream().anyMatch(p -> p.getIdentification().equals(probe.getIdentification()))) {
			throw new UniqueKeyException("Probe " + probe.getIdentification() + " already exists.");
		}
	}

	private void checkLimits(Mars mars, Position newPosition) throws LimitViolationException {
		if (newPosition.getxAxis() > mars.getSurfaceLimits().getxAxis() || newPosition.getxAxis() < 0
				|| newPosition.getyAxis() > mars.getSurfaceLimits().getyAxis() || newPosition.getyAxis() < 0) {
			throw new LimitViolationException("Probe violated Mars surface limits.");
		}
	}

	private void checkOtherProbes(Mars mars, Position newPosition) throws CollisionException {
		if (!isEmpty(mars, newPosition)) {
			throw new CollisionException("Is already exists a probe in position " + newPosition + " try another way.");
		}
	}

	private boolean isEmpty(Mars mars, Position newPosition) {
		return !mars.getProbes().stream().anyMatch(probe -> probe.getPosition().equals(newPosition));
	}

}
