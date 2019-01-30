package org.ex.yggdrasil.model;

import java.io.Serializable;

import org.ex.yggdrasil.model.world.World;

/**
 * Represents a unique instance of some class.
 * 
 * @author Zachary Chandler
 */
public  class Identifiable implements Comparable<Identifiable>, Serializable {

	private static final long serialVersionUID = -3549487374783162817L;
	
	public final long id;

	public Identifiable(Long id) {
		if (id == null) {
			this.id = World.get().addIdentifiable(this);
		} else if (World.get() == null) {
			this.id = id;
		} else {
			throw new UnsupportedOperationException("Cannot create entitiy with set ID while world exists.");
		}
	}
	
	public Identifiable() {
		this.id = World.get().addIdentifiable(this);
	}
	
	@Override
	public int compareTo(Identifiable o) {
		long result = this.id - o.id; 
		return result > 0? 1 : result < 0 ? -1 : 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identifiable other = (Identifiable) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public long getId() {
		return id;
	}
}
