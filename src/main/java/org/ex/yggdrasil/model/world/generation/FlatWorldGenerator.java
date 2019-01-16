package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Coordinate4D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.Tile.Biome;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.model.world.World;

public class FlatWorldGenerator implements WorldGenerator {

	private final Biome b;
	
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
					createTile(pos, b);
				} else {
					updateTile(t, pos.getW(), b);
				}
			}
		}
	}

	private void updateTile(Tile t, int w, Biome b2) {
		// TODO Auto-generated method stub
		t.position.setW(w);
		t.setType(b2);
	}

	private static void createTile(Coordinate4D pos, Biome b2) {
		// TODO Auto-generated method stub
		Tile t = World.get().addTile(pos, b2);
		t.position.setW(pos.getW());
	}

	private int getW(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}
}
