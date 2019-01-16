package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.TileTraverser.SearchField;

public interface WorldGenerator {
	
	public void generate(SearchField s);
	
}
