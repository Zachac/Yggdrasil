package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Chunk;

public abstract class AbstractWorldGenerator implements WorldGenerator {

	public AbstractWorldGenerator() {
		super();
	}

	public abstract Biome getBiome(int i, int j);
	
	@Override
	public void generate(Chunk c) {
		for (int i = 0; i < c.getXSize(); i++) {
			for (int j = 0; j < c.getYSize(); j++) {
				c.setTile(getBiome(i, j), i, j);
			}
		}
	}
}