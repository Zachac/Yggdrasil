package org.ex.yggdrasil.model.world.chunks;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.ex.yggdrasil.model.Identifiable;
import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.glassfish.grizzly.http.server.util.Enumerator;

public class Chunk extends Identifiable implements Serializable {

	private static final long serialVersionUID = -6610624708583536538L;

	private static final int X_SIZE = 16;
	private static final int Y_SIZE = 16;

	private final Biome[][] tiles;
	private final Entity[][] entities;
	
	private final Set<Player> players;
	private final Set<Entity> contents;
	
	public Chunk() {
		this(null);
	}

	public Chunk(Long id) {
		super(id);
		this.tiles = new Biome[X_SIZE][Y_SIZE];
		this.entities = new Entity[X_SIZE][Y_SIZE];
		this.players = Collections.synchronizedSet(new HashSet<Player>());
		this.contents = Collections.synchronizedSet(new HashSet<Entity>());
	}

	public void setTile(Biome type, int x, int y) {
		tiles[x][y] = type;
		UpdateProcessor.update(this, type, x, y);
	}
	
	public Biome getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public synchronized void moveEntity(Entity e, int x, int y) {
		if (e.getChunk() != this) {
			throw new IllegalArgumentException();
		} else if (entities[x][y] != null && entities[x][y] != e) {
			throw new IllegalArgumentException("Something is already there");
		}
		
		entities[e.position.getX()][e.position.getY()] = null;
		entities[x][y] = e;
	}
	
	public synchronized void add(Entity e, int x, int y) {
		if (e.getChunk() != null) {
			throw new IllegalArgumentException("Cannot add entity in existing chunk");
		} else if (entities[e.position.getX()][e.position.getY()] != null) {
			throw new IllegalArgumentException("Cannot overwrite existing entity");
		}
		
		entities[x][y] = e;
		
		if (e instanceof Player) {
			players.remove(e);
		} else {
			contents.remove(e);
		}
	}
	
	public void remove(Entity e) {
		entities[e.position.getX()][e.position.getY()] = null;
		
		if (e instanceof Player) {
			players.remove(e);
		} else {
			contents.remove(e);
		}
	}

	public boolean legalPosition(int x, int y) {
		return x >= 0 && x < X_SIZE && y >= 0 && y < Y_SIZE;
	}

	public synchronized void add(Entity e) {
		if (e.getChunk() != null && e.getChunk() != this) {
			throw new IllegalArgumentException("Entity is already in another chunk");
		}
		
		Entity e2 = entities[e.position.getX()][e.position.getY()];
		
		if (e2 != null && e != e2) {
			throw new IllegalArgumentException("That position is blocked by another entity");
		}
		
		if (e instanceof Player) {
			players.add((Player) e);
		} else {
			contents.add(e);
		}
		
		entities[e.position.getX()][e.position.getY()] = e;
	}

	public int getXSize() {
		return X_SIZE;
	}
	
	public int getYSize() {
		return Y_SIZE;
	}

	public Enumerator<Player> getPlayers() {
		return new Enumerator<>(this.players);
	}

	public Enumerator<Entity> getEntities() {
		return new Enumerator<>(this.contents);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chunk [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
