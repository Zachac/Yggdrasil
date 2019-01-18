package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Tile.Biome;

public class FlatWorldGenerator extends AbstractSingleBiomeWorldGenerator implements WorldGenerator {
	
	public FlatWorldGenerator(Biome b) {
		super(b);
	}

	@Override
	public float chooseHeight(Integer... heights) {
		return 0;
	}
	
	@Override
	public int getW(int i, int j) {
		return 0;
	}
}
