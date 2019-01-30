package org.ex.yggdrasil.model.world;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.ex.yggdrasil.model.Identifiable;
import org.ex.yggdrasil.model.Time;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.world.chunks.Chunk;

public class World implements Serializable {

	private static final long serialVersionUID = 5566188866686329588L;
	private static World self = new World();
	private long maxId;
	
	public final Time time;

	private final HashMap<Long,Identifiable> identifiables;
	private final Map<String,Player> players;
	private Chunk root;
	
	private String loadFilename;
	
	private World() {
		maxId = 0;
		players = new HashMap<>();
		identifiables = new HashMap<>();
		root = new Chunk(nextId());
		time = new Time();
	}
	
	public static void setRoot(Chunk root) {
		Objects.requireNonNull(root);
		self.root = root;
	}
	
	public static World get() {
		return self;
	}
	
	public static void set(World world) {
		self = world;
	}
	
	public Chunk getRoot() {
		return root;
	}

	public static Player getPlayer(String username) {
		return self.players.get(username.toLowerCase());
	}
	
	public static Player addPlayer(Player p) {
		return self.players.put(p.userName.toLowerCase(), p);
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


	public long addIdentifiable(Identifiable i) {
		if (i.id != 0) {
			throw new IllegalArgumentException();
		}
		
		Long id = nextId();
		this.identifiables.put(id, i);
		return id;
	}
}
