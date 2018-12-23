package model.updates;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.charachters.Player;
import model.world.Coordinate;
import model.world.Entity;
import model.world.ModifiableCoordinate;
import model.world.Tile;
import model.world.TileTraverser;
import parser.commands.LookCommand;
import server.serialization.NetworkUpdate;
import server.serialization.Serializer;

public class UpdateProcessor {

	public static void privateUpdate(Player source, NetworkUpdate n) {
		source.messages.add(Serializer.prepare(n));
	}
	
	public static void publicUpdate(NetworkUpdate n) {
		Map<Integer, Coordinate> broadcastPositions = new TreeMap<>();
		
		if (n.localPosition != null) {
			broadcastPositions.put(n.localPosition.z, n.localPosition);
		}
		
		if (!n.tiles.isEmpty()) {
			getBroadCastPositions(n.tiles, broadcastPositions);
		}
		
		Map<Integer,Integer> maxRanges = getMaxRange(n.tiles, broadcastPositions);
		
		broadcast(broadcastPositions, maxRanges, n);
	}

	/**
	 * Get's the average position for each z level in the list of tiles. If there 
	 * is an existing position in the list of broad cast position, it won't be 
	 * overwritten.
	 * 
	 * @param tiles the tiles to average
	 * @param broadcastPositions the positions to broadcast from
	 */
	private static void getBroadCastPositions(List<Tile> tiles, Map<Integer, Coordinate> broadcastPositions) { 
		Map<Integer, AverageCoordinate> zLevels = new TreeMap<>();
		
		for (Tile t: tiles) {

			AverageCoordinate c = zLevels.get(t.position.z);
			if (c == null) {
				c = new AverageCoordinate(t.position.x, t.position.y);
				zLevels.put(t.position.z, c);
			} else {
				c.average(t.position.x, t.position.y);
			}	
		}
		
		for (int z : zLevels.keySet()) {
			if (!broadcastPositions.containsKey(z)) {
				ModifiableCoordinate c = zLevels.get(z);
				broadcastPositions.put(z, new Coordinate(c.x, c.y, z));				
			}
		}
	}
	
	private static class AverageCoordinate extends ModifiableCoordinate {

		private int n, x, y;
		
		public AverageCoordinate(int x, int y) {
			super(x, y);
			this.x = x;
			this.y = y;
			this.n = 1;
		}
		
		public void average(int x, int y) {
			this.x += x;
			this.y += y;
			this.n++;
			
			super.x = this.x/n;
			super.y = this.y/n;
		}
		
	}

	private static Map<Integer,Integer> getMaxRange(List<Tile> tiles, Map<Integer, Coordinate> broadcastPositions) {
		Map<Integer,Integer> result = new TreeMap<>();
		
		for (Tile t : tiles) {
			Integer lastMax = result.get(t.position.z);
			int newMax;

			if (lastMax == null) {
				result.put(t.position.z, getMaxDistance(t.position, broadcastPositions.get(t.position.z)));
			} else if (lastMax < (newMax = getMaxDistance(t.position, broadcastPositions.get(t.position.z)))) {
				result.put(t.position.z, newMax);
			}
		}
		
		return result;
	}
	
	private static int getMaxDistance(Coordinate pos1, Coordinate pos2) {
		int max = pos1.x - pos2.x;
		if (max < 0) max = -max;
		
		int max2 = pos2.y - pos2.y;
		if (max2 < 0) max2 = -max2;
		
		if (max < max2) max = max2;
		
		return max;
	}
	
	private static void broadcast(Map<Integer, Coordinate> broadcastPositions, Map<Integer, Integer> maxRanges, NetworkUpdate n) {
		String toSend = Serializer.prepare(n);
		
		TileTraverser.Handler h = (t) -> {
			for (Entity e : t.contents) {
				if (e instanceof Player) {
					((Player) e).messages.add(toSend);
				}
			}
		};
		
		for (Integer z : broadcastPositions.keySet()) {
			TileTraverser.traverse(broadcastPositions.get(z), maxRanges.get(z) + LookCommand.DEFAULT_LOOK, h);
		}
	}
}
