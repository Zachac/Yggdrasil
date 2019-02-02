package org.ex.yggdrasil.model.world.chunks;

public enum Direction3D {
	N(new Coordinate3D(-1,0,0)),
	E(new Coordinate3D(0,1,0)),
	S(new Coordinate3D(1,0,0)),
	W(new Coordinate3D(0,-1,0)),
	U(new Coordinate3D(0,0,1)),
	D(new Coordinate3D(0,0,-1)),
	C(new Coordinate3D(0,0,0)),
	
	NE(N, E),
	SE(S, E),
	SW(S, W),
	NW(N, W);
	
	public static final int COUNT = Direction3D.values().length;
	
	public final Coordinate3D direction;
	
	/**
	 * If this direction is composite, it is made up of one or more directions.
	 */
	public final boolean isComposite;
	
	/**
	 * A list of directions making up this composite direction.
	 */
	public final Direction3D[] directions;

	private Direction3D(Coordinate3D d) {
		this.direction = d;
		this.directions = null;
		this.isComposite = false;
	}
	
	private Direction3D(Direction3D... directions) {
		int x = 0;
		int y = 0;
		int z = 0;
		
		for (Direction3D d : directions) {
			x += d.direction.getX();
			y += d.direction.getY();
			z += d.direction.getZ();
		}
		
		this.directions = directions;
		this.direction = new Coordinate3D(x, y, z);
		this.isComposite = true;
	}
	
	public Direction3D opposite() {
		switch (this) {
		case N: return S;
		case E: return W;
		case S: return N;
		case W: return E;
		case NE: return SW;
		case SW: return NE;
		case NW: return SE;
		case SE: return NW;
		case D: return U;
		case U: return D;
		case C: return C;
		default: return null;
		}
	}

	public static Direction3D valueOf(char direction) {
		switch (direction) {
		case 'N': case 'n': return N;
		case 'S': case 's': return S;
		case 'E': case 'e': return E;
		case 'W': case 'w': return W;
		case 'U': case 'u': return U;
		case 'D': case 'd': return D;
		case 'C': case 'c': return C;
		default: return null;
		}
	}

	public Direction3D combine(Direction3D d2) {
		switch(this) {
		case N: return d2 == E? NE : d2 == W? NW : N;
		case S: return d2 == E? SE : d2 == W? SW : S;
		case E: return d2 == N? NE : d2 == S? SE : E;
		case W: return d2 == N? NW : d2 == S? SW : W;
		default: return null;
		}
	}
}