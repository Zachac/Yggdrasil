package org.ex.yggdrasil.model.entities;

import java.io.Serializable;
import java.util.Objects;

import org.ex.yggdrasil.model.Identifiable;
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
	
	public Entity(Chunk c) {
		this(null, c);
	}
	
	public Entity(Long id, Chunk c) {
		super(id);
		Objects.requireNonNull(c);
		this.position = new EntityPosition();
		this.chunk = c;
	}
	
	public final void setChunk(Chunk c) {
		Objects.requireNonNull(c);
		this.chunk = c;
	}
	
	public final Chunk getChunk() {
		return this.chunk;
	}

	public synchronized void move(int x, int y) {
		if (!chunk.legalPosition(x, y)) {
			throw new IllegalArgumentException("Cannot move to illegal position in chunk!");
		}
		
		this.chunk.moveEntity(this, x, y);
	}
	
	public synchronized void move(Chunk c, int x, int y) {
		if (c == this.chunk) {
			throw new IllegalArgumentException("Cannot move to chunk already in!");
		} else if (!c.legalPosition(x, y)) {
			throw new IllegalArgumentException("Cannot move to illegal position in chunk!");
		}

		this.chunk = null;
		c.add(this, x, y);
		this.chunk.remove(this);
		this.x = x;
		this.y = y;
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
