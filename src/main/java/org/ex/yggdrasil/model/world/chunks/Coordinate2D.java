package org.ex.yggdrasil.model.world.chunks;

import java.io.Serializable;

public class Coordinate2D implements Serializable, Comparable<Coordinate2D> {

	private static final long serialVersionUID = -7679965990604777695L;

	protected int x, y;

	public Coordinate2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate2D get(Direction2D next) {
		return new Coordinate2D(
				this.x + next.direction.x,
				this.y + next.direction.y);
	}
	
	public int getDistance(Coordinate2D other) {
		int max = this.x - other.x;
		if (max < 0) max = -max;
		
		int max2 = this.y - other.y;
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
		
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		Coordinate2D other = (Coordinate2D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public int compareTo(Coordinate2D o) {
		int result = this.x - o.x;

		if (result == 0) {
			result = this.y - o.y;
		}
		
		return result;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}