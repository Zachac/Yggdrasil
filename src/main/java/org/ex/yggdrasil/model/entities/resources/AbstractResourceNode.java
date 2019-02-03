package org.ex.yggdrasil.model.entities.resources;

import java.util.Random;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.EntityMaterial;
import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.world.chunks.Chunk;

public abstract class AbstractResourceNode extends Entity {

	private static final Random R = new Random();
	
	private static final long serialVersionUID = -3186233866694900032L;
	private boolean depleted;
	
	public AbstractResourceNode(Chunk c) {
		super(c);
		this.setMaterial(getActiveMaterial());
		this.depleted = false;
	}

	public abstract EntityMaterial getActiveMaterial();
	public abstract EntityMaterial getDepletedMaterial();
	public abstract String getDepletedMessage();
	public abstract String getRejectedMessage();
	public abstract Item getItem();
	
	/**
	 * Check if a given player can consume this resource.
	 * @param p the player to check.
	 * @return if they can consume this resource.
	 */
	public abstract boolean canConsume(Player p);
	
	/**
	 * How likely is it for this player to consume this node? Where 0.0 is never consumed and 1.0 always get's consumed.
	 * @param p the player to check.
	 * @return there chance to consume this node.
	 */
	public abstract float getConsumeChance(Player p);
	
	public Item consume(Player p) {
		if (!this.depleted) {
			p.messages.add(getDepletedMessage());
			return null;
		} else if (!this.canConsume(p)) {
			p.messages.add(getRejectedMessage());
			return null;
		}
		
		return this.getConsumeChance(p) < R.nextFloat() ? null : this.getItem();
	}
}
