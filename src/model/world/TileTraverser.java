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
				h, new SearchField(
				root.position.x - range,
				root.position.y - range,
				root.position.x + range,
				root.position.y + range,
				root.position.z));
		
	}
	
	// lookup on hashmap / tree map
	// O(n * log(m)) where n = nodes in radius & m = total nodes
	public static void traverseAll(Handler h, SearchField s) {
		for (int i = s.minx; i <= s.maxx; i++) {
			for (int j = s.miny; j <= s.maxy; j++) {
				Tile t = World.get().getTile(new Coordinate(i, j, s.z));
				
				if (t != null) {
					h.run(t);
				}
			}
		}
	}
	
	public static class SearchField {

		public final int minx, miny, maxx, maxy, z;

		public SearchField(int minx, int miny, int maxx, int maxy, int z) {
			super();
			this.minx = minx;
			this.miny = miny;
			this.maxx = maxx;
			this.maxy = maxy;
			this.z = z;
		}
	}
}
