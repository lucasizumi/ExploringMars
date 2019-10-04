package com.lucasizumi.elo7.marsprobe.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasizumi.elo7.marsprobe.domain.Mars;
import com.lucasizumi.elo7.marsprobe.domain.Position;
import com.lucasizumi.elo7.marsprobe.domain.Probe;
import com.lucasizumi.elo7.marsprobe.dto.MarsDto;
import com.lucasizumi.elo7.marsprobe.dto.ProbeDto;
import com.lucasizumi.elo7.marsprobe.exception.ItsNotValidWindDirection;
import com.lucasizumi.elo7.marsprobe.exception.ProbeNotFoundException;
import com.lucasizumi.elo7.marsprobe.response.MarsProbesResponse;
import com.lucasizumi.elo7.marsprobe.response.MarsResponse;
import com.lucasizumi.elo7.marsprobe.response.ProbeResponse;
import com.lucasizumi.elo7.marsprobe.service.ExplorationService;

@RestController
@RequestMapping("/exploration")
public class ExplorationController {

	private Mars mars;

	@Autowired
	private ExplorationService explorationService;

	@PostMapping("/mars")
	public ResponseEntity<?> createMars(@RequestBody @NotNull MarsDto marsDto) {

		if (!isMarsNull()) {
			return conflictResponse("Mars already exists.");
		}

		this.mars = new Mars(new Position(marsDto.getLimitAxisX(), marsDto.getLimitAxisY()));

		MarsResponse response = new MarsResponse(mars.getSurfaceLimits().toString(), mars.getProbes().size());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/mars/probe")
	public ResponseEntity<?> sendProbeToMars(@RequestBody ProbeDto probeDto) {
		Probe probe;

		try {

			if (isMarsNull()) {
				return conflictResponse("You need create mars before sending a probe.");
			}

			probe = this.explorationService.sendProbeToMars(mars, probeDto);
		} catch (Exception e) {
			return conflictResponse(e.getMessage());
		}
		ProbeResponse response = new ProbeResponse(probe.getIdentification(), probe.getWindDirection(),
				probe.getPosition().toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("probe/{probeIdentifier}/move")
	public ResponseEntity<?> moveProbe(@PathVariable String probeIdentifier) {
		Probe probe;

		try {
			probe = this.explorationService.moveProbe(mars, probeIdentifier);
		} catch (ProbeNotFoundException e) {
			return badRequestResponse(e.getMessage());
		} catch (ItsNotValidWindDirection e) {
			return badRequestResponse(e.getMessage());
		} catch (Exception e) {
			return conflictResponse(e.getMessage());
		}

		return ResponseEntity.ok(
				new ProbeResponse(probe.getIdentification(), probe.getWindDirection(), probe.getPosition().toString()));
	}

	@PatchMapping("probe/{probeIdentifier}/move/{command}")
	public ResponseEntity<?> moveProbe(@PathVariable String probeIdentifier, @PathVariable String command) {
		Probe probe;

		try {
			probe = this.explorationService.moveProbe(mars, probeIdentifier, command);
		} catch (ProbeNotFoundException e) {
			return badRequestResponse(e.getMessage());
		} catch (ItsNotValidWindDirection e) {
			return badRequestResponse(e.getMessage());
		} catch (Exception e) {
			return conflictResponse(e.getMessage());
		}

		return ResponseEntity.ok(
				new ProbeResponse(probe.getIdentification(), probe.getWindDirection(), probe.getPosition().toString()));
	}

	@PatchMapping("probe/{probeIdentifier}/rotate/{rotateDirection}")
	public ResponseEntity<?> rotateProbe(@PathVariable String probeIdentifier, @PathVariable char rotateDirection) {
		Probe probe;

		try {
			probe = this.explorationService.rotateProbe(mars, probeIdentifier, rotateDirection);
		} catch (Exception e) {
			return badRequestResponse(e.getMessage());
		}

		return ResponseEntity.ok(
				new ProbeResponse(probe.getIdentification(), probe.getWindDirection(), probe.getPosition().toString()));

	}

	@GetMapping("mars/probes")
	public ResponseEntity<?> getAllProbes() {

		if (isMarsNull()) {
			return conflictResponse("You need create mars before sending a probe.");
		}

		return ResponseEntity.ok(new MarsProbesResponse(this.mars.getProbes()));
	}

	private boolean isMarsNull() {
		return mars == null;
	}

	private ResponseEntity<String> conflictResponse(String message) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
	}

	private ResponseEntity<String> badRequestResponse(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

}
