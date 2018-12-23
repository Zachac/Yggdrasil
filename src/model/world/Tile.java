package model.world;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Tile extends Entity implements Serializable {

	private static final long serialVersionUID = -7676772836100313611L;
	
	public final Biome type;
	public final boolean isWall;
	public final Coordinate position;
	
	public Tile[] links;
	
	public final List<Entity> contents;
	
	public Tile(Coordinate coordinate, boolean isWall, Biome type) {
		this(null, coordinate, isWall, type);
	}
	
	public Tile(Long id, Coordinate coordinate, boolean isWall, Biome type) {
		super(id);
		this.type = type;
		this.isWall = isWall;
		this.contents = new LinkedList<>();
		this.position = coordinate;
		
		links = new Tile[Coordinate.Direction.COUNT];
	}

	public enum Biome {
		GRASS, SAND, STONE, WATER, ROAD, TRAIL, NONE
	}

}
