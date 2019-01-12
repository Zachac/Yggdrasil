package model.world;

/** 
 * A modifiable coordinate.
 */
public class ModifiableCoordinate extends Coordinate {

	private static final long serialVersionUID = -6749495634057277169L;

	public ModifiableCoordinate(int x, int y, int z) {
		super(x, y, z);
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