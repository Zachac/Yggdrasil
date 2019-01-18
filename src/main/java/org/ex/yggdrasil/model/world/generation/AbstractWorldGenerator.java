package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Coordinate4D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.Tile.Biome;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.model.world.World;

public abstract class AbstractWorldGenerator implements WorldGenerator {

	public AbstractWorldGenerator() {
		super();
	}

	public abstract int getW(int i, int j);
	public abstract float chooseHeight(Integer... heights);
	public abstract Biome getBiome(int i, int j);
	
	@Override
	public void generate(SearchField s) {
		for (int i = s.minx; i <= s.maxx; i++) {
			for (int j = s.miny; j <= s.maxy; j++) {
				Coordinate4D pos = new Coordinate4D(i, j, s.z, this.getW(i,j));
				Tile t = World.get().getTile(pos);
				Biome b = getBiome(i, j);
				
				if (t == null) {
					t = World.get().addTile(pos, b);
				}
				
				t.setType(b);
				t.setHeight(pos.getW());
			}
		}
		
		for (int i = s.minx; i <= s.maxx; i++) {
			for (int j = s.miny; j <= s.maxy; j++) {
				Coordinate4D pos = new Coordinate4D(i, j, s.z, this.getW(i,j));
				Tile t = World.get().getTile(pos);
				smooth(t);
			}
		}
	}


	public void smooth(Tile t) {
		
		Tile[][] tiles = t.getNearbyTiles();
		
		float[] corners = t.getCorners();
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				Integer[] heights = new Integer[4];
				int k = 0;
				
				k += tiles[i + 0][j + 0] != null && (heights[k] = tiles[i + 0][j + 0].position.getW()) != null ? 1 : 0;
				k += tiles[i + 1][j + 0] != null && (heights[k] = tiles[i + 1][j + 0].position.getW()) != null ? 1 : 0;
				k += tiles[i + 1][j + 1] != null && (heights[k] = tiles[i + 1][j + 1].position.getW()) != null ? 1 : 0;
				k += tiles[i + 0][j + 1] != null && (heights[k] = tiles[i + 0][j + 1].position.getW()) != null ? 1 : 0;
				
				if (j == 0) {
					corners[i] = chooseHeight(heights) - t.position.getW();
				} else { //if (j == 1) {
					corners[3 - i] = chooseHeight(heights) - t.position.getW();
				}
			}
		}
	}
}