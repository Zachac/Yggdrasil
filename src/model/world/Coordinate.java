package model.world;

import java.io.Serializable;

public final class Coordinate implements Serializable, Comparable<Coordinate> {

	public enum Direction {
		N(new Coordinate(1,0,0)),
		E(new Coordinate(0,1,0)),
		S(new Coordinate(-1,0,0)),
		W(new Coordinate(0,-1,0)),
		U(new Coordinate(0,0,1)),
		D(new Coordinate(0,0,-1));
		
		public static final int COUNT = Coordinate.Direction.values().length;
		
		public final Coordinate direction;
		
		private Direction(Coordinate d) {
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
	
	private static final long serialVersionUID = -7679965990604777695L;

	public final int x, y, z;

	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate(int x, int y) {
		this(x, y, 0);
	}

	public Coordinate get(Direction next) {
		return new Coordinate(
				this.x + next.direction.x,
				this.y + next.direction.y,
				this.z + next.direction.z);
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
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public int compareTo(Coordinate o) {
		int result = this.x - o.x;

		if (result == 0) {
			result = this.y - o.y;
			
			if (result == 0) {
				result = this.z - o.z;
			}
		}
		
		return result;
	}
}