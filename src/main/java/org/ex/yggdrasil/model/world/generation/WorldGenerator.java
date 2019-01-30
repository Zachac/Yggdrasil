package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.chunks.Chunk;

public interface WorldGenerator {
	
	void generate(Chunk c);
	
}
