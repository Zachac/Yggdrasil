package org.ex.yggdrasil.model.entities.items;

public enum Item {
	STICK(false), LEAVES(true);
	
	public final boolean plural;
	
	Item(boolean plural) {
		this.plural = plural;
	}
}
