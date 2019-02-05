package org.ex.yggdrasil.model.entities.resources;

import java.io.Serializable;
import java.util.Random;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.model.world.time.Time;

public abstract class ResourceNode extends Entity {

	private static final Random R = new Random();

	private static final long serialVersionUID = -3186233866694900032L;
	private final ResourceNodeType type;
	private final Runnable reset;
	private boolean depleted;

	public ResourceNode(Chunk c, ResourceNodeType type) {
		super(c);
		this.setMaterial(type.activeMaterial);
		this.depleted = false;
		this.type = type;
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
		
		this.depleted = true;
		this.setMaterial(type.depletedMaterial);
		UpdateProcessor.update(this);
		
		long respawnTime = System.currentTimeMillis() + this.type.respawn_time + (int) (R.nextFloat() * this.type.respawn_variance);
		
		Time.scheduleEvent(this.reset, respawnTime);
		
		return this.type.item;
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
}
