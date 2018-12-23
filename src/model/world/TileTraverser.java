package model.world;

import java.util.ArrayList;
import java.util.List;

import model.world.Coordinate.Direction;

public class TileTraverser {

	public static interface Handler {
		void run(Tile t);
	}

	public static void traverse(Coordinate coordinate, Integer range, Handler h) {
		traverse(World.get().getTile(coordinate), range, h);
	}
	
	public static void traverse(Tile root, int range, Handler h) {
		traverse(root, range, h,
				Direction.N,
				Direction.E,
				Direction.S,
				Direction.W);
	}
	
	// O(n^2) runtime :(
	// where n = number of nodes in the radius
	public static void traverse(Tile root, int range, Handler h, Direction... directions) {
		int radius = range + 1;
		int maxTiles = radius * radius * 4;

		
		List<Tile> seen = new ArrayList<>(maxTiles);
		List<Tile> visited = new ArrayList<>(maxTiles);
		
		seen.add(root);
		
		while (!seen.isEmpty()) {
			Tile t = seen.remove(seen.size() - 1);
			h.run(t);
			visited.add(t);
			
			for (Direction d : directions) {
				Tile n = t.links[d.ordinal()];
				if (n != null && !seen.contains(n) && !visited.contains(n)) {
					seen.add(n);
				}
			}
		}		
	}
}
