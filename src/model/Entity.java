package model;

import java.io.Serializable;

import model.world.World;

public class Entity implements Comparable<Entity>, Serializable {

	private static final long serialVersionUID = 7073890008845835852L;
	
	public final long id;
	
	public Entity() {
		this(null);
	}
	
	public Entity(Long id) {
		if (id == null) {
			this.id = World.get().addEntity(this);
		} else {
			this.id = id;			
		}
		
	}
	
	public long getId() {
		return id;
	}

	@Override
	public int compareTo(Entity arg0) {
		return (int) (this.id - arg0.id);
	}
}
