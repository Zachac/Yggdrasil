package org.ex.yggdrasil.model.updates;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.server.serialization.Serializer;

public class NetworkUpdate implements Serializable {

	private static final long serialVersionUID = -4875831612778534546L;
	
	private final Set<Entity> entities;
	public Player localPlayer;
	
	private boolean shouldSend;
	
	public NetworkUpdate() {
		this.entities = new TreeSet<>();
		shouldSend = false;
	}
	
	public void setLocalPlayer(Player p) {
		this.localPlayer = p;
		shouldSend = true;
	}

	public void addEntities(List<Entity> ents) {
		shouldSend = true;
		entities.addAll(ents);
	}

	public void addTiles(List<Tile> tiles) {
		shouldSend = true;
		entities.addAll(tiles);
	}
	
	public void addEntity(Entity e) {
		shouldSend = true;
		entities.add(e);
	}
	
	public Set<Entity> getEntities() {
		return entities;
	}
	
	public boolean shouldSend() {
		return shouldSend;
	}
	
	public void clear() {
		localPlayer = null;
		shouldSend = false;
		entities.clear();
	}

	/**
	 * Serializes this object into a string and clears out the updates in this object.
	 * @return the serialization of this object.
	 */
	public String consume() {
		String result = Serializer.prepare(this);
		this.clear();
		return result;
	}
}
