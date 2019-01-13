package org.ex.yggdrasil.model.world;

import java.io.Serializable;

public class Coordinate3D implements Serializable, Comparable<Coordinate3D> {

	private static final long serialVersionUID = -7679965990604777695L;

	protected int x, y, z;

	public Coordinate3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate3D(int x, int y) {
		this(x, y, 0);
	}

	public Coordinate3D get(Direction next) {
		return new Coordinate3D(
				this.x + next.direction.x,
				this.y + next.direction.y,
				this.z + next.direction.z);
	}
	
	public int getDistance(Coordinate3D other) {
		int max = this.x - other.x;
		if (max < 0) max = -max;
		
		int max2 = this.y - other.y;
		if (max2 < 0) max2 = -max2;
		if (max < max2) max = max2;
		
		max2 = this.z - other.z;
		if (max2 < 0) max2 = -max2;
		if (max < max2) max = max2;
		
		return max;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(x);
		result.append(", ");
		result.append(y);

		if (z != 0) {
			result.append(", ");
			result.append(z);
		}

		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass().isAssignableFrom(getClass()))
			return false;
		Coordinate3D other = (Coordinate3D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public int compareTo(Coordinate3D o) {
		int result = this.x - o.x;

		if (result == 0) {
			result = this.y - o.y;
			
			if (result == 0) {
				result = this.z - o.z;
			}
		}
		
		return result;
	}
	
	public enum Direction {
		N(new Coordinate3D(1,0,0)),
		E(new Coordinate3D(0,1,0)),
		S(new Coordinate3D(-1,0,0)),
		W(new Coordinate3D(0,-1,0)),
		U(new Coordinate3D(0,0,1)),
		D(new Coordinate3D(0,0,-1));
		
		public static final int COUNT = Coordinate3D.Direction.values().length;
		
		public final Coordinate3D direction;
		
		private Direction(Coordinate3D d) {
			this.direction = d;
		}
		
		public Direction opposite() {
			switch (this) {
			case N: return S;
			case E: return W;
			case S: return N;
			case W: return E;
			case D: return U;
			case U: return D;
			default: return null;
			}
		}

		public static Direction valueOf(char direction) {
			switch (direction) {
			case 'N': case 'n': return N;
			case 'S': case 's': return S;
			case 'E': case 'e': return E;
			case 'W': case 'w': return W;
			case 'U': case 'u': return U;
			case 'D': case 'd': return D;
			default: return null;
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}