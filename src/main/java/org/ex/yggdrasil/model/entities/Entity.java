package org.ex.yggdrasil.model.entities;

import java.io.Serializable;
import java.util.Objects;

import org.ex.yggdrasil.model.Identifiable;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.model.world.chunks.Coordinate2D;
import org.ex.yggdrasil.model.world.chunks.Direction2D;

/**
 * An entity that exists in the world... Has a position, chunk location, etc...
 * 
 * @author Zachary Chandler
 */
public abstract class Entity extends Identifiable implements Serializable {

	private static final long serialVersionUID = 7073890008845835852L;

	public final Coordinate2D position;

	private Chunk chunk;
	private EntityMaterial material;
	private Direction2D facing;
	
	public Entity(Chunk c) {
		this(null, c, EntityMaterial.MISSING_TEXTURE, 0, 0);
	}

	public Entity(Chunk c, EntityMaterial material, int x, int y) {
		this(null, c, material, x, y);
	}
	
	public Entity(Long id, Chunk c, EntityMaterial material, int x, int y) {
		super(id);
		Objects.requireNonNull(c);
		this.position = new Coordinate2D(x, y);
		this.chunk = c;
		this.material = material;
		this.facing = Direction2D.N;
	}

	public EntityMaterial getMaterial() {
		return this.material;
	}

	protected void setMaterial(EntityMaterial material) {
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
		this.chunk.move(this, x, y);
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

	public Coordinate2D getPosition() {
		return position;
	}
	
	public abstract String[] getActions(); 

	public Direction2D getFacing() {
		return facing;
	}

	public void setFacing(Direction2D facing) {
		Objects.requireNonNull(facing);
		this.facing = facing;
		UpdateProcessor.update(this);
	}
}
