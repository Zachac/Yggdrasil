package model;

import java.io.Serializable;

import model.world.Coordinate3D;
import model.world.World;

public abstract class Entity implements Comparable<Entity>, Serializable {

	private static final long serialVersionUID = 7073890008845835852L;
	
	public final long id;
	
	public Entity() {
		this(null);
	}
	
	public Entity(Long id) {
		if (id == null) {
			this.id = World.get().addEntity(this);
		} else if (World.get() == null) {
			this.id = id;
		} else {
			throw new UnsupportedOperationException("Cannot create entitiy with set ID while world exists.");
		}
	}
	
	public long getId() {
		return id;
	}

	@Override
	public int compareTo(Entity arg0) {
		return (int) (this.id - arg0.id);
	}
	
	public abstract Coordinate3D getPosition();
}
