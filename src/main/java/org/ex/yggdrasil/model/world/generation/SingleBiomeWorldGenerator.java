package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Chunk;

public class SingleBiomeWorldGenerator extends AbstractWorldGenerator {

	private final Biome b;
	
	public SingleBiomeWorldGenerator(Biome b) {
		this.b = b;
	}

	@Override
	public Biome getBiome(int i, int j) {
		return b;
	}

	@Override
	public void populate(Chunk c) {
		// do nothing
	}
}
