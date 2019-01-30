package org.ex.yggdrasil.model.world.chunks;

/** 
 * A modifiable coordinate.
 */
public class ModifiableCoordinate3D extends Coordinate3D {

	private static final long serialVersionUID = -6749495634057277169L;

	public ModifiableCoordinate3D(int x, int y, int z) {
		super(x, y, z);
	}

	public ModifiableCoordinate3D(Coordinate3D position) {
		super(position.x, position.y, position.z);
	}

	protected void setX(int x) {
		this.x = x;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
	
	protected void setZ(int z) {
		this.z = z;
	}
}