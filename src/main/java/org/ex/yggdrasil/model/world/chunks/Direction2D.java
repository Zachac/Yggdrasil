package org.ex.yggdrasil.model.world.chunks;

public enum Direction2D {
	N(new Coordinate2D(-1,0)),
	E(new Coordinate2D(0,1)),
	S(new Coordinate2D(1,0)),
	W(new Coordinate2D(0,-1)),
	C(new Coordinate2D(0,0)),
	
	NE(N, E),
	SE(S, E),
	SW(S, W),
	NW(N, W);
	
	public static final int COUNT = Direction2D.values().length;
	
	public final Coordinate2D direction;
	
	/**
	 * If this direction is composite, it is made up of one or more directions.
	 */
	public final boolean isComposite;
	
	/**
	 * A list of directions making up this composite direction.
	 */
	public final Direction2D[] directions;

	private Direction2D(Coordinate2D d) {
		this.direction = d;
		this.directions = null;
		this.isComposite = false;
	}
	
	private Direction2D(Direction2D... directions) {
		int x = 0;
		int y = 0;
		
		for (Direction2D d : directions) {
			x += d.direction.getX();
			y += d.direction.getY();
		}
		
		this.directions = directions;
		this.direction = new Coordinate2D(x, y);
		this.isComposite = true;
	}
	
	public Direction2D opposite() {
		switch (this) {
		case N: return S;
		case E: return W;
		case S: return N;
		case W: return E;
		case NE: return SW;
		case SW: return NE;
		case NW: return SE;
		case SE: return NW;
		case C: return C;
		default: return null;
		}
	}

	public static Direction2D valueOf(char direction) {
		switch (direction) {
		case 'N': case 'n': return N;
		case 'S': case 's': return S;
		case 'E': case 'e': return E;
		case 'W': case 'w': return W;
		case 'C': case 'c': return C;
		default: return null;
		}
	}

	public Direction2D combine(Direction2D d2) {
		switch(this) {
		case N: return d2 == E? NE : d2 == W? NW : N;
		case S: return d2 == E? SE : d2 == W? SW : S;
		case E: return d2 == N? NE : d2 == S? SE : E;
		case W: return d2 == N? NW : d2 == S? SW : W;
		default: return null;
		}
	}
}