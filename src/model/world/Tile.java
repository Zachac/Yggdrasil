package model.world;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import model.Entity;
import model.world.Coordinate3D.Direction;

public class Tile extends Entity implements Serializable {

	public enum Biome { GRASS, SAND, STONE, WATER, NONE }

	private static final long serialVersionUID = -7676772836100313611L;
	
	public final Biome type;
	public final boolean isWall;
	public final Coordinate4D position;
	
	public transient List<Entity> contents;
	
	public Tile(Coordinate4D coordinate, boolean isWall, Biome type) {
		this(null, coordinate, isWall, type);
	}
	
	public Tile(Long id, Coordinate4D coordinate, boolean isWall, Biome type) {
		super(id);
		this.type = type;
		this.isWall = isWall;
		this.position = coordinate;
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

	public Tile getTile(Direction d) {
		return World.get().getTile(position.get(d));
	}
}
