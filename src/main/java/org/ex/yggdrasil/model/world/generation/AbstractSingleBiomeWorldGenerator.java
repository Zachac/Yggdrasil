package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Tile.Biome;

public abstract class AbstractSingleBiomeWorldGenerator extends AbstractWorldGenerator {

	private final Biome b;
	
	public AbstractSingleBiomeWorldGenerator(Biome b) {
		this.b = b;
	}

	@Override
	public Biome getBiome(int i, int j) {
		return b;
	}
}
