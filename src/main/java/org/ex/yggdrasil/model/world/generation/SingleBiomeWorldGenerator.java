package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.chunks.Biome;

public class SingleBiomeWorldGenerator extends AbstractWorldGenerator {

	private final Biome b;
	
	public SingleBiomeWorldGenerator(Biome b) {
		this.b = b;
	}

	@Override
	public Biome getBiome(int i, int j) {
		return b;
	}
}
