package org.ex.yggdrasil.model.world.chunks;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.ex.yggdrasil.model.Identifiable;
import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.glassfish.grizzly.http.server.util.Enumerator;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Chunk extends Identifiable implements Serializable {

	private static final long serialVersionUID = -6610624708583536538L;

	private static final int DEFAULT_X_SIZE = 16;
	private static final int DEFAULT_Y_SIZE = 16;

	private final Biome[][] tiles;
	private final boolean[][] walls;
	
	private final Set<Player> players;
	private final Multimap<Coordinate2D, Entity> contents;

	private final int xSize;
	private final int ySize;
	
	public Chunk() {
		this(null);
	}

	public Chunk(Long id) {
		this(id, DEFAULT_X_SIZE, DEFAULT_Y_SIZE);
	}
	
	private Chunk(Long id, int x, int y) {
		super(id);
		
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Cannot create chunk with negative size.");
		}
		
		this.xSize = x;
		this.ySize = y;
		this.tiles = new Biome[xSize][ySize];
		this.walls = new boolean[xSize][ySize];
		this.players = Collections.synchronizedSet(new HashSet<Player>());
		this.contents = HashMultimap.create();
	}

	public void setTile(Biome type, int x, int y) {
		tiles[x][y] = type;
		UpdateProcessor.update(this, type, x, y);
	}
	
	public void setWall(boolean isWall, int x, int y) {
		this.walls[x][y] = isWall;
	}
	
	public boolean isWall(int x, int y) {
		return this.walls[x][y];
	}
	
	public Biome getTile(int x, int y) {
		return tiles[x][y];
	}

	public boolean isLegalPosition(int x, int y) {
		return x >= 0 && x < xSize && y >= 0 && y < ySize;
	}
	
	public synchronized void move(Entity e, int x, int y) {
		if (!isLegalPosition(x, y)) {
			throw new IllegalArgumentException("Attempting to move entity to illegal position.");
		} else if (e.getChunk() != this) {
			throw new IllegalArgumentException("Cannot move entity in different chunk instance.");
		}
		
		this.contents.remove(e.position, e);
		
		e.position.setX(x);
		e.position.setY(y);
		
		this.contents.put(e.position, e);
	}

	public synchronized Entity add(Entity e) {
		if (e.getChunk() != null && e.getChunk() != this) {
			throw new IllegalArgumentException("Entity is already in another chunk");
		}
		
		if (e instanceof Player) {
			players.add((Player) e);
		} else {
			contents.put(e.position, e);
		}
		
		return e;
	}

	public synchronized void remove(Entity e) {
		contents.remove(e.position, e);
		
		if (e instanceof Player) {
			players.remove(e);
		}
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
	}

	public Enumerator<Player> getPlayers() {
		return new Enumerator<>(this.players);
	}

	public Collection<Entity> getEntities() {
		return this.contents.values();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chunk [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}


	public Collection<Entity> getEntities(Coordinate2D position) {
		return Collections.unmodifiableCollection(this.contents.get(position));
	}
	
}
