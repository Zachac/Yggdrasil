package model.world;

import java.io.Serializable;

import model.world.Tile.Biome;

public class Chunk implements Serializable {

	private static final long serialVersionUID = -6610624708583536538L;

	public static final int OFFSET_MASK = 0x0F;
	
	private ChunkLayer[] layers;
	private int layerOffset;
	public final ChunkCoordinate position;
	
	public Chunk(ChunkCoordinate cc) {
		this.layers = new ChunkLayer[0];
		this.position = cc;
		this.layerOffset = 0;
	}

	public Tile addTile(Coordinate4D c, Biome type, Long id) {
		int offsetX = c.getX() - this.position.x;
		int offsetY = c.getY() - this.position.y;
		
		int layerOffset = c.getZ() + this.layerOffset;
		
		if (layerOffset >= this.layers.length) {
			setLayerCount(layerOffset+1, 0);
		} else if (layerOffset < 0) {
			setLayerCount(this.layers.length + -layerOffset, -layerOffset);
			this.layerOffset -= layerOffset;
			layerOffset = 0;
		}
		
		ChunkLayer layer = layers[layerOffset];
		
		if (layer == null) {
			layer = layers[layerOffset] = new ChunkLayer();
		}
		
		if (layer.tiles[offsetX][offsetY] != null) {
			throw new UnsupportedOperationException("Cannot replace existing tile.");
		}
		
		return layer.tiles[offsetX][offsetY] = new Tile(id, c, type, this);
	}
	
	public Tile getTile(int x, int y, int z) {
		int offsetX = x - this.position.x;
		int offsetY = y - this.position.y;
		int layerOffset = z + this.layerOffset;

		if (layerOffset >= layers.length || layerOffset < 0) {
			return null;
		}

		ChunkLayer layer = layers[layerOffset];

		if (layer == null) {
			return null;
		}
		
		return layer.tiles[offsetX][offsetY];
	}
	
	private void setLayerCount(int x, int offset) {
		ChunkLayer[] layers = new ChunkLayer[x];
		
		for (int i = 0; i < this.layers.length; i++) {
			layers[i + offset] = this.layers[i];
		}
		
		this.layers = layers;
	}
	
	private static class ChunkLayer implements Serializable {

		private static final long serialVersionUID = -8077300722851553653L;
		
		public Tile[][] tiles;
		
		public ChunkLayer() {
			tiles = new Tile[16][16];
		}
	}
	
	public static class ChunkCoordinate implements Serializable {
		
		private static final long serialVersionUID = -2386605326443224182L;
		
		public final int x, y;

		public ChunkCoordinate(int x, int y) {
			this.x = x & ~Chunk.OFFSET_MASK;
			this.y = y & ~Chunk.OFFSET_MASK;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
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
			ChunkCoordinate other = (ChunkCoordinate) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
}
