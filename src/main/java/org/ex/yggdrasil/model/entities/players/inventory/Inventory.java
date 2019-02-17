package org.ex.yggdrasil.model.entities.players.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.updates.UpdateProcessor;

public class Inventory implements Serializable {

	private static final long serialVersionUID = 4046734573248729296L;

	public static final int MAX_SIZE = 24;

	private final Player p;
	private final List<Item> contents;

	private int itemCount;

	public Inventory(Player p) {
		this.p = p;
		this.contents = new ArrayList<>(MAX_SIZE);
		this.itemCount = 0;

		for (int i = 0; i < MAX_SIZE; i++) {
			this.contents.add(null);
		}
	}

	public Item get(int position) {
		return contents.get(position);
	}

	public synchronized Item set(int position, Item i) {
		Item result = contents.set(position, i);

		boolean nul = result == null;

		// if we are changing nonNull to null,
		// or changing null to nonNull
		// update the item count
		if (nul != (i == null)) {
			if (nul) {
				this.itemCount = this.itemCount + 1;
			} else {
				this.itemCount = this.itemCount - 1;
			}
		}

		UpdateProcessor.inventoryUpdate(p, position, i);
		return result;
	}

	public synchronized boolean add(Item item) {
		if (isFull()) {
			return false;
		}

		int i = this.contents.indexOf(null);
		this.set(i, item);
		return true;
	}

	public int getCount() {
		return itemCount;
	}

	public boolean isFull() {
		return this.itemCount >= MAX_SIZE - 1;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("There is,");

		if (this.itemCount <= 0) {
			result.append("\n\tnothing.");
		} else {
			for (Item i : this.contents) {
				if (i != null) {
					result.append("\n\t");
					result.append(i.toString());
				}
			}
		}

		return result.toString();
	}
}
