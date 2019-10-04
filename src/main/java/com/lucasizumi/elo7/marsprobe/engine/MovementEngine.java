package com.lucasizumi.elo7.marsprobe.engine;

import org.springframework.stereotype.Component;

import com.lucasizumi.elo7.marsprobe.domain.Position;
import com.lucasizumi.elo7.marsprobe.enumeration.Direction;
import com.lucasizumi.elo7.marsprobe.enumeration.WindRose;
import com.lucasizumi.elo7.marsprobe.exception.ItsNotValidWindDirection;

@Component
public class MovementEngine {

	public Position move(Position position, WindRose windDirection) throws ItsNotValidWindDirection {
		switch (windDirection) {
		case N:
			return new Position(position.getxAxis(), position.getyAxis() + 1);
		case E:
			return new Position(position.getxAxis() + 1, position.getyAxis());
		case S:
			return new Position(position.getxAxis(), position.getyAxis() - 1);
		case W:
			return new Position(position.getxAxis() - 1, position.getyAxis());
		default:
			throw new ItsNotValidWindDirection("This wind direction does not exist.");
		}
	}

	public WindRose rotate(WindRose windDirection, Direction rotateDirection) {
		return windDirection.rotate(rotateDirection);
	}

}
