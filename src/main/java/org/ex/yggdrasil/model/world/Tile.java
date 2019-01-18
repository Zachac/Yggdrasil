package org.ex.yggdrasil.model.world;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.Chunk;
import org.ex.yggdrasil.model.world.Coordinate3D.Direction;

public class Tile extends Entity implements Serializable {

	public enum Biome { GRASS, SAND, STONE, WATER, NONE }

	private static final long serialVersionUID = -7676772836100313611L;
	
	private Biome type;
	public final Chunk chunk;
	private boolean isWall;
	public final Coordinate4D position;
	
	public transient List<Entity> contents;

	/* 0 3
	 * 1 2
	 */
	private final float[] corners;


	public Tile(Coordinate4D coordinate, Biome type, Chunk chunk) {
		this(null, coordinate, type, chunk);
	}
	
	public Tile(Long id, Coordinate4D coordinate, Biome type, Chunk chunk) {
		super(id);
		this.setType(type);
		this.isWall = false;
		this.position = coordinate;
		this.chunk = chunk;
		contents = new LinkedList<>();
		corners = new float[] {0, 0, 0, 0};
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
	
	public void overwriteNearbyCorners() {
		Tile[][] tiles = getNearbyTiles();
		float[][] diffs = getDiffs(tiles);
		
		if (tiles[0][0] != null) {
			tiles[0][0].getCorners()[2] = diffs[0][0];
			UpdateProcessor.update(tiles[0][0]);
		}

		if (tiles[2][0] != null) {
			tiles[2][0].getCorners()[3] = diffs[2][0];
			UpdateProcessor.update(tiles[2][0]);
		}
		
		if (tiles[2][2] != null) {
			tiles[2][2].getCorners()[0] = diffs[2][2];
			UpdateProcessor.update(tiles[2][2]);
		}
		
		if (tiles[0][2] != null) {
			tiles[0][2].getCorners()[1] = diffs[0][2];
			UpdateProcessor.update(tiles[0][2]);
		}

		if (tiles[1][0] != null) {
			tiles[1][0].getCorners()[3] = diffs[1][0];
			tiles[1][0].getCorners()[2] = diffs[1][0];	
			UpdateProcessor.update(tiles[1][0]);		
		}

		if (tiles[2][1] != null) {
			tiles[2][1].getCorners()[0] = diffs[2][1];
			tiles[2][1].getCorners()[3] = diffs[2][1];
			UpdateProcessor.update(tiles[2][1]);
		}

		if (tiles[1][2] != null) {
			tiles[1][2].getCorners()[0] = diffs[1][2];
			tiles[1][2].getCorners()[1] = diffs[1][2];
			UpdateProcessor.update(tiles[1][2]);
		}
		
		if (tiles[0][1] != null) {
			tiles[0][1].getCorners()[1] = diffs[0][1];
			tiles[0][1].getCorners()[2] = diffs[0][1];
			UpdateProcessor.update(tiles[0][1]);
		}

		corners[0] = 0;
		corners[1] = 0;
		corners[2] = 0;
		corners[3] = 0;
	}

	private float[][] getDiffs(Tile[][] tiles) {
		float[][] diffs = new float[3][3];
		float thisw = this.position.getW();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (tiles[i+1][j+1] != null) {
					diffs[i+1][j+1] = thisw - tiles[i+1][j+1].position.getW();					
				}
			}
		}
		return diffs;
	}

	public Tile[][] getNearbyTiles() {
		Tile[][] tiles = new Tile[3][3];
		ModifiableCoordinate3D pos = new ModifiableCoordinate3D(this.position);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				pos.setX(this.position.getX() - i);
				pos.setY(this.position.getY() + j);
				tiles[i+1][j+1] = World.get().getTile(pos);
				
			}
		}
		return tiles;
	}
	
//	/**
//	 * Choose between the possible heights for the corner of this tile.
//	 * Currently using the minimum height of all tiles.
//	 * 
//	 * @param bs the bytes to choose from.
//	 * @return the result.
//	 */
//	private byte choose(byte... bs) {
//		byte b = bs[0];
//		
//		for (int i = 1; i < bs.length; i++) {
//			if (bs[i] < b) {
//				b = bs[i];
//			}
//		}
//		
//		return b;
//	}

	public float[] getCorners() {
		return corners;
	}

	public void setHeight(int w) {
		this.position.setW(w);
		this.overwriteNearbyCorners();
		UpdateProcessor.update(this);
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
