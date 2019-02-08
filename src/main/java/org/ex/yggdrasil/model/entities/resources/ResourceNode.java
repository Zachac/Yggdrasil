package org.ex.yggdrasil.model.entities.resources;

import java.io.Serializable;
import java.util.Random;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.model.world.chunks.Chunk;

public class ResourceNode extends Entity {

	private static final Random R = new Random();

	private static final long serialVersionUID = -3186233866694900032L;
	private final ResourceNodeType type;
	private final Runnable reset;
	private boolean depleted;

	public ResourceNode(ResourceNodeType type, Chunk c, int x, int y) {
		super(c, type.activeMaterial, x, y);
		this.type = type;
		this.depleted = false;
		this.reset = new Reset();
	}

	/**
	 * Utility method to only consume this resource node based on a statistical
	 * chance. Where 1.0f always succeeds and 0.0f never succeeds.
	 * 
	 * @param chance that this this call should succeed, ranging from 0-1 
	 * @return the item if it was consumed, or null if it wasn't
	 */
	public Item consume(float chance) {
		return chance < R.nextFloat() ? null : this.consume();
	}

	public synchronized Item consume() {
		if (this.depleted) {
			return null;
		}
		
		deplete();
		
		return this.type.item;
	}
	
	private void deplete() {
		this.depleted = true;
		this.setMaterial(type.depletedMaterial);
		UpdateProcessor.update(this);
		long respawnTime = System.currentTimeMillis() + this.type.respawn_time + (int) (R.nextFloat() * this.type.respawn_variance);
		World.get().time.scheduleEvent(this.reset, respawnTime);
	}
	
	private class Reset implements Runnable, Serializable {
		
		private static final long serialVersionUID = -8036149760684415950L;

		@Override
		public void run() {
			ResourceNode.this.depleted = false;
			ResourceNode.this.setMaterial(ResourceNode.this.type.activeMaterial);
			UpdateProcessor.update(ResourceNode.this);
		}
	}

	@Override
	public String[] getActions() {
		return this.type.actions;
	}

	@Override
	public String toString() {
		return this.type.name();
	}

	public ResourceNodeType getType() {
		return this.type;
	}

	public boolean isDepleted() {
		return depleted;
	}
}
