package org.ex.yggdrasil.model.entities.resources;

import org.ex.yggdrasil.model.entities.EntityMaterial;
import org.ex.yggdrasil.model.entities.items.Item;

public enum ResourceNodeType {

	BUSH(Item.STICK, EntityMaterial.BUSH, EntityMaterial.BUSH, 15000, 15000);
	
	public final EntityMaterial activeMaterial;
	public final EntityMaterial depletedMaterial;
	public final Item item;
	public final int respawn_time;
	public final int respawn_variance;

	/**
	 * Defines a resource node type.
	 * @param item the item this resource produces
	 * @param active the texture to show when this node type is ready to be consumed
	 * @param depleted the texture to show when this node is depleted
	 * @param respawn_time the minimum time it takes for this resource type to re-spawn
	 * @param respawn_variance the uniform distribution spread for re-spawn times of this node
	 */
	ResourceNodeType(Item item, EntityMaterial active, EntityMaterial depleted, int respawn_time, int respawn_variance) {
		this.item = item;
		this.activeMaterial = active;
		this.depletedMaterial = depleted;
		this.respawn_time = respawn_time;
		this.respawn_variance = respawn_variance;
	}
	
}
