package org.ex.yggdrasil.model.world;

public class TileTraverser {

	public static interface Handler {
		void run(Tile t);
	}

	public static void traverse(Coordinate3D coordinate, Integer range, Handler h) {
		traverse(World.get().getTile(coordinate), range, h);
	}
	
	public static void traverse(Tile root, int range, Handler h) {

		traverseAll(
				h, new SearchField(
				root.position.getX() - range,
				root.position.getY() - range,
				root.position.getX() + range,
				root.position.getY() + range,
				root.position.getZ()));
		
	}
	
	// lookup on hashmap / tree map
	// O(n * log(m)) where n = nodes in radius & m = total nodes
	public static void traverseAll(Handler h, SearchField s) {
		for (int i = s.minx; i <= s.maxx; i++) {
			for (int j = s.miny; j <= s.maxy; j++) {
				Tile t = World.get().getTile(new Coordinate3D(i, j, s.z));
				
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("SearchField [minx=");
			builder.append(minx);
			builder.append(", miny=");
			builder.append(miny);
			builder.append(", maxx=");
			builder.append(maxx);
			builder.append(", maxy=");
			builder.append(maxy);
			builder.append(", z=");
			builder.append(z);
			builder.append("]");
			return builder.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + maxx;
			result = prime * result + maxy;
			result = prime * result + minx;
			result = prime * result + miny;
			result = prime * result + z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SearchField other = (SearchField) obj;
			if (maxx != other.maxx)
				return false;
			if (maxy != other.maxy)
				return false;
			if (minx != other.minx)
				return false;
			if (miny != other.miny)
				return false;
			if (z != other.z)
				return false;
			return true;
		}
	}
}
