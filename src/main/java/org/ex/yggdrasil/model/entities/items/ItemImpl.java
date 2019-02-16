package org.ex.yggdrasil.model.entities.items;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Packaged scope implementation of item as most items should only be
 * instantiated by the Items index class.
 * 
 * @author Zachary Chandler
 */
class ItemImpl implements Item, Serializable {

	private static final long serialVersionUID = 4418470730976431489L;

	private static final Logger LOG = LoggerFactory.getLogger(ItemImpl.class);

	public final String name;
	public final boolean isPlural;

	public ItemImpl(String name, boolean isPlural) {
		super();
		this.name = name;
		this.isPlural = isPlural;

		LOG.debug("Creating Item {}", name);
	}

	@Override
	public boolean isPlural() {
		return this.isPlural;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return equals((ItemImpl) obj);
	}
	
	public boolean equals(Item obj) {
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else
			return this.name.equals(obj.toString());
	}
}
