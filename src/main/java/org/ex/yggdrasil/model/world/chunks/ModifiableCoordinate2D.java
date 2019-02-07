package org.ex.yggdrasil.model.world.chunks;

/** 
 * A modifiable coordinate.
 */
public class ModifiableCoordinate2D extends Coordinate2D {

	private static final long serialVersionUID = -6749495634057277169L;

	public ModifiableCoordinate2D(int x, int y) {
		super(x, y);
	}

	public ModifiableCoordinate2D(Coordinate2D position) {
		super(position.x, position.y);
	}

	protected void setX(int x) {
		this.x = x;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
}