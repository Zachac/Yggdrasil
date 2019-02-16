package org.ex.yggdrasil.model.entities.resources;

import org.ex.yggdrasil.model.entities.EntityMaterial;
import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.entities.items.Items;

public enum ResourceNodeType {

	BUSH(new ItemDrops(Items.STICK, Items.LEAVES), EntityMaterial.BUSH, EntityMaterial.BUSH_DEPLETED, 15000, 15000, new String[0]);
	
	public final EntityMaterial activeMaterial;
	public final EntityMaterial depletedMaterial;
	public final ItemDrops items;
	public final int respawn_time;
	public final int respawn_variance;
	public final String[] actions;

	/**
	 * Defines a resource node type.
	 * @param items the items this resource produces along with their drop chance
	 * @param active the texture to show when this node type is ready to be consumed
	 * @param depleted the texture to show when this node is depleted
	 * @param respawn_time the minimum time it takes for this resource type to re-spawn
	 * @param respawn_variance the uniform distribution spread for re-spawn times of this node
	 */
	ResourceNodeType(ItemDrops items, EntityMaterial active, EntityMaterial depleted, int respawn_time, int respawn_variance, String[] actions) {
		this.items = items;
		this.activeMaterial = active;
		this.depletedMaterial = depleted;
		this.respawn_time = respawn_time;
		this.respawn_variance = respawn_variance;
		this.actions = actions;
	}
	
	public static class ItemDrops {
		private final Item[] items;
		private final float[] chances;
		
		public ItemDrops(Item... items) {
			this.items = items;
			this.chances =  new float[items.length];
			
			float uniformChance = 1f / items.length;

			for (int i = 0; i < chances.length; i++) {
				this.chances[i] = uniformChance;
			}
		}
		
		public Item get(float f) {
			float total = 0.0f;
			
			for (int i = 0; i < chances.length; i++) {
				total += chances[i];
				
				if (f < total) {
					return items[i];
				}
			}
			
			return items[items.length - 1];
		}
	}
}
