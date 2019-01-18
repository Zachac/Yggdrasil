package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Coordinate4D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.Tile.Biome;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.model.world.World;

public class FlatWorldGenerator implements WorldGenerator {

	public final Biome b;
	
	public FlatWorldGenerator(Biome b) {
		this.b = b;
	}

	@Override
	public void generate(SearchField s) {
		for (int i = s.minx; i <= s.maxx; i++) {
			for (int j = s.miny; j <= s.maxy; j++) {
				Coordinate4D pos = new Coordinate4D(i, j, s.z, this.getW(i,j));
				Tile t = World.get().getTile(pos);
				
				if (t == null) {
					t = World.get().addTile(pos, b);
				}
				
				t.setType(b);
				t.setHeight(pos.getW());
			}
		}
	}

	public Biome getBiome(int i, int j) {
		return b;
	}
	
	public int getW(int i, int j) {
		return 0;
	}
}
