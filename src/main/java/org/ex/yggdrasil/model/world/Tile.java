package org.ex.yggdrasil.model.world;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.world.Chunk;

public class Tile extends Entity implements Serializable {

	public enum Biome { GRASS, SAND, STONE, WATER, NONE }

	private static final long serialVersionUID = -7676772836100313611L;
	
	private Biome type;
	public final Chunk chunk;
	private boolean isWall;
	public final Coordinate3D position;
	
	public transient List<Entity> contents;

	public Tile(Coordinate3D coordinate, Biome type, Chunk chunk) {
		this(null, coordinate, type, chunk);
	}
	
	public Tile(Long id, Coordinate3D coordinate, Biome type, Chunk chunk) {
		super(id);
		this.setType(type);
		this.isWall = false;
		this.position = coordinate;
		this.chunk = chunk;
		contents = new LinkedList<>();
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject(); 
		contents = new LinkedList<>();
	}

	@Override
	public Coordinate3D getPosition() {
		return this.position;
	}

	public Tile getTile(Direction3D d) {
		return World.get().getTile(position.get(d));
	}
	
	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public Biome getType() {
		return type;
	}

	public void setType(Biome type) {
		this.type = type;
	}
}
