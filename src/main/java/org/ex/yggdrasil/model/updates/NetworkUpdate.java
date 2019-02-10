package org.ex.yggdrasil.model.updates;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.server.serialization.Serializer;

public class NetworkUpdate implements Serializable {

	private static final long serialVersionUID = -4875831612778534546L;

	private final Set<Entity> entities;
	private final Set<BiomeUpdate> biomeUpdates;
	private final List<InventoryUpdate> inventoryUpdates;
	private Entity followEntity;
	
	private boolean shouldSend;

	private Chunk chunk;
	
	public NetworkUpdate() {
		this.entities = new TreeSet<>();
		this.biomeUpdates = new TreeSet<>();
		this.inventoryUpdates = new LinkedList<>();
		shouldSend = false;
	}
	
	public void setFollowEntity(Entity p) {
		this.followEntity = p;
		shouldSend = true;
	}

	public Entity getFollowEntity() {
		return this.followEntity;
	}
	
	public void setChunk(Chunk c) {
		shouldSend = true;
		this.chunk = c;
	}
	
	public Chunk getChunk() {
		return chunk;
	}

	public void addEntities(List<Entity> ents) {
		shouldSend = true;
		entities.addAll(ents);
	}

	public void addEntity(Entity e) {
		shouldSend = true;
		entities.add(e);
	}
	
	public Set<Entity> getEntities() {
		return entities;
	}

	public void addBiomeUpdate(Biome b, int x, int y) {
		addBiomeUpdate(new BiomeUpdate(b, x, y));
	}
	
	public void addBiomeUpdate(BiomeUpdate b) {
		shouldSend = true;
		biomeUpdates.add(b);
	}
	
	public Set<BiomeUpdate> getBiomeUpdates() {
		return biomeUpdates;
	}

	public void addInventoryUpdate(InventoryUpdate inventoryUpdate) {
		inventoryUpdates.add(inventoryUpdate);
		shouldSend = true;
	}

	public Collection<InventoryUpdate> getInventoryUpdates() {
		return inventoryUpdates;
	}
	
	public boolean shouldSend() {
		return shouldSend;
	}
	
	public void clear() {
		followEntity = null;
		chunk = null;
		shouldSend = false;
		entities.clear();
		biomeUpdates.clear();
		inventoryUpdates.clear();
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
