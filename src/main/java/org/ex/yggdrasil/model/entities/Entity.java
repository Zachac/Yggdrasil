package org.ex.yggdrasil.model.entities;

import java.io.Serializable;
import java.util.Objects;

import org.ex.yggdrasil.model.Identifiable;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.chunks.Chunk;

/**
 * An entity that exists in the world... Has a position, chunk location, etc...
 * 
 * @author Zachary Chandler
 */
public abstract class Entity extends Identifiable implements Serializable {

	private static final long serialVersionUID = 7073890008845835852L;

	public final EntityPosition position;

	private Chunk chunk;
	private int x, y;
	private EntityMaterial material;
	
	public Entity(Chunk c) {
		this(null, c);
	}
	
	public Entity(Long id, Chunk c) {
		super(id);
		Objects.requireNonNull(c);
		this.position = new EntityPosition();
		this.chunk = c;
		this.material = EntityMaterial.MISSING_TEXTURE;
	}

	public EntityMaterial getMaterial() {
		return this.material;
	}

	public void setMaterial(EntityMaterial material) {
		this.material = material;
		UpdateProcessor.update(this);
	}
	
	public final void setChunk(Chunk c) {
		Objects.requireNonNull(c);
		this.chunk = c;
	}
	
	public final Chunk getChunk() {
		return this.chunk;
	}

	public synchronized void move(int x, int y) {
		if (!this.canMove(x, y)) {
			throw new IllegalArgumentException("Cannot move there!");
		}
		
		this.x = x;
		this.y = y;
		UpdateProcessor.update(this);
	}
	
	public synchronized void jumpto(Chunk c, int x, int y) {
		if (c == this.chunk) {
			throw new IllegalArgumentException("Cannot move to chunk already in!");
		} else if (this.canMove(c, x, y)) {
			throw new IllegalArgumentException("Cannot move to illegal position in chunk!");
		}

		this.chunk.remove(this);
		this.chunk = c;
		c.add(this);
		this.move(x, y);
		UpdateProcessor.update(this);
	}
	
	public boolean canMove(int x, int y) {
		return canMove(this.chunk, x, y);
	}
	
	public boolean canMove(Chunk c, int x, int y) {
		return c.isLegalPosition(x, y) && !c.isWall(x, y);
	}
	
	/**
	 * A view into the position inside this entity. Also accessible through Entity.position
	 * 
	 * @author Zachac
	 */
	public class EntityPosition implements Serializable {

		private static final long serialVersionUID = 5373178537392016408L;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(getX());
			builder.append(", ");
			builder.append(getY());
			return builder.toString();
		}
	}

	public EntityPosition getPosition() {
		return position;
	}
}
