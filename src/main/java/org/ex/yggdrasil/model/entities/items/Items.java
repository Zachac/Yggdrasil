package org.ex.yggdrasil.model.entities.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Items {

	private static final Logger LOG = LoggerFactory.getLogger(ItemImpl.class);

	static {
		LOG.info("Loading items");
	}

	public static final ItemImpl STICK = new ItemImpl("STICK", false);
	public static final ItemImpl LEAVES = new ItemImpl("LEAVES", true);

	static {
		LOG.info("Finished loading items.");
	}
}
