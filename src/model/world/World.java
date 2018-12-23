package model.world;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import model.Entity;
import model.Time;
import model.charachters.Player;
import model.world.Coordinate.Direction;
import model.world.Tile.Biome;

public class World implements Serializable {

	private static final long serialVersionUID = 5566188866686329588L;
	private static World self = new World();
	private long maxId;
	
	public final Time time;

	private final TreeMap<Long,Entity> entities;
	private final Map<String,Player> players;
	private final Map<Coordinate,Tile> tiles; 
	private Tile root;
	
	private String loadFilename;
	
	private World() {
		maxId = 0;
		players = new TreeMap<String,Player>();
		entities = new TreeMap<Long, Entity>();
		tiles = new TreeMap<Coordinate,Tile>();
		root = addTile(new Coordinate(0, 0), Biome.GRASS);
		time = new Time();
	}

	public Tile addTile(Coordinate c, Biome type) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(type);
		
		if (tiles.containsKey(c)) {
			throw new IllegalArgumentException("Something already exists there!");
		}
		
		Tile t = new Tile(nextId(), c, false, type);
		
		tiles.put(c, t);
		
		linkTile(t);
		
		return t;
	}
	
	private void linkTile(Tile t) {
		for (Direction d : Direction.values()) {
			if (d != Direction.D && d != Direction.U) {
				Tile o = tiles.get(t.position.get(d));
				
				if (o != null) {
					t.links[d.ordinal()] = o;
					o.links[d.opposite().ordinal()] = t;					
				}
			}
		}
	}
	
	public Tile getTile(Coordinate c) {
		return tiles.get(c);
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
		
		Long id = this.maxId++;
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
