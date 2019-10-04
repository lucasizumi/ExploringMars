package com.lucasizumi.elo7.marsprobe.enumeration;

public enum WindRose {

	N {
		@Override
		public WindRose rotate(Direction direction) {
			return this.rotate(direction, E, W);
		}
	},
	E {
		@Override
		public WindRose rotate(Direction direction) {
			return this.rotate(direction, S, N);
		}
	},
	S {
		@Override
		public WindRose rotate(Direction direction) {
			return this.rotate(direction, W, E);
		}
	},
	W {
		@Override
		public WindRose rotate(Direction direction) {
			return this.rotate(direction, N, S);
		}
	};

	public abstract WindRose rotate(Direction direction);
	
	protected WindRose rotate(Direction direction, WindRose right, WindRose left) {
		if(direction.equals(Direction.RIGHT)) {
			return right;
		} else if(direction.equals(Direction.LEFT)) {
			return left;
		} else {
			return this;
		}
	}
}