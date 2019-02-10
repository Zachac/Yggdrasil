package org.ex.yggdrasil.model.updates;

import org.ex.yggdrasil.model.entities.items.Item;

public class InventoryUpdate {

	public final int i;
	public final Item item;
	
	public InventoryUpdate(int position, Item i) {
		this.i = position;
		this.item = i;
	}
}
