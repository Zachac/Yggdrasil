package org.ex.yggdrasil.model.world;

public class Coordinate4D extends Coordinate3D {

	private static final long serialVersionUID = -6126527431981901485L;
	
	protected int w;

	public Coordinate4D(int x, int y, int z, int w) {
		super(x, y, z);
		this.setW(w);
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
	

	public Coordinate4D get(Direction3D next) {
		return new Coordinate4D(
				this.x + next.direction.x,
				this.y + next.direction.y,
				this.z + next.direction.z,
				this.w);
	}
}
