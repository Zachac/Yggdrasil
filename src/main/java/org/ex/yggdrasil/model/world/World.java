package org.ex.yggdrasil.model.world;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.Time;
import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.world.Chunk.ChunkCoordinate;
import org.ex.yggdrasil.model.world.Tile.Biome;

public class World implements Serializable {

	private static final long serialVersionUID = 5566188866686329588L;
	private static World self = new World();
	private long maxId;
	
	public final Time time;

	private final TreeMap<Long,Entity> entities;
	private final Map<String,Player> players;
	private final Map<ChunkCoordinate,Chunk> chunks; 
	private Tile root;
	
	private String loadFilename;
	
	private World() {
		maxId = 0;
		players = new TreeMap<String,Player>();
		entities = new TreeMap<Long, Entity>();
		chunks = new HashMap<>();
		root = addTile(new Coordinate3D(0, 0, 0), Biome.GRASS, nextId());
		time = new Time();
	}


	public Tile addTile(Coordinate3D c, Biome type) {
		return addTile(c, type, null);
	}
	
	private Tile addTile(Coordinate3D c, Biome type, Long id) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(type);
		
		ChunkCoordinate cc = new ChunkCoordinate(c.getX(), c.getY());
		
		Chunk ch = chunks.get(cc);
		
		if (ch == null) {
			ch = new Chunk(cc);
			chunks.put(cc, ch);
		}

		return ch.addTile(c, type, id);
	}
	
	public Tile getTile(Coordinate3D c) {
		Objects.requireNonNull(c);
		ChunkCoordinate cc = new ChunkCoordinate(c.getX(), c.getY());

		Chunk ch = chunks.get(cc);
		
		if (ch == null) {
			return null;
		}

		return ch.getTile(c.getX(), c.getY(), c.getZ());
	}
	
	public static void setRoot(Tile root) {
		Objects.requireNonNull(root);
		self.root = root;
	}
	
	public static World get() {
		return self;
	}
	
	public static void set(World world) {
		self = world;
	}
	
	public Tile getRoot() {
		return root;
	}

	public static Player getPlayer(String username) {
		return self.players.get(username.toLowerCase());
	}
	
	public static Player addPlayer(Player p) {
		return self.players.put(p.userName.toLowerCase(), p);
	}
	
	public long addEntity(Entity e) {
		if (e.getId() != 0) {
			throw new IllegalArgumentException();
		}
		
		Long id = nextId();
		this.entities.put(id, e);
		return id;
	}
	
	private synchronized long nextId() {
		return this.maxId++;
	}

	public String getFilename() {
		return this.loadFilename;
	}
	
	public void setFilename(String filename) {
		this.loadFilename = filename;
	}
}
