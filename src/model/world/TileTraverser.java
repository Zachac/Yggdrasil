package model.world;

public class TileTraverser {

	public static interface Handler {
		void run(Tile t);
	}

	public static void traverse(Coordinate coordinate, Integer range, Handler h) {
		traverse(World.get().getTile(coordinate), range, h);
	}
	
	public static void traverse(Tile root, int range, Handler h) {

		traverseAll(
				h,
				root.position.x - range,
				root.position.x + range,
				root.position.y - range,
				root.position.y + range,
				root.position.z);
		
	}
	
	// lookup on hashmap / tree map
	// O(n * log(m)) where n = nodes in radius & m = total nodes
	public static void traverseAll(Handler h, int minX, int maxX, int minY, int maxY, int z) {
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				Tile t = World.get().getTile(new Coordinate(i, j, z));
				
				if (t != null) {
					h.run(t);
				}
			}
		}
	}
	
	// DFS with in place array lists
	// O(n^2) runtime :(
	// where n = number of nodes in the radius
//	public static void traverse(Tile root, int range, Handler h, Direction... directions) {
//		int radius = range + 1;
//		int maxTiles = radius * radius * 4;
//
//		
//		List<Tile> seen = new ArrayList<>(maxTiles);
//		List<Tile> visited = new ArrayList<>(maxTiles);
//		
//		seen.add(root);
//		
//		while (!seen.isEmpty()) {
//			Tile t = seen.remove(seen.size() - 1);
//			h.run(t);
//			visited.add(t);
//			
//			for (Direction d : directions) {
//				Tile n = t.links[d.ordinal()];
//				if (n != null && root.position.getDistance(n.position) < range && !seen.contains(n) && !visited.contains(n)) {
//					seen.add(n);
//				}
//			}
//		}		
//	}
}
