package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Coordinate3D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.Tile.Biome;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.model.world.World;

public abstract class AbstractWorldGenerator implements WorldGenerator {

	public AbstractWorldGenerator() {
		super();
	}

	public abstract Biome getBiome(int i, int j, int z);
	
	@Override
	public void generate(SearchField s) {
		for (int i = s.minx; i <= s.maxx; i++) {
			for (int j = s.miny; j <= s.maxy; j++) {
				Coordinate3D pos = new Coordinate3D(i, j, s.z);
				Tile t = World.get().getTile(pos);
				Biome b = getBiome(i, j, s.z);
				
				if (t == null) {
					t = World.get().addTile(pos, b);
				}
				
				t.setType(b);
			}
		}
	}
}