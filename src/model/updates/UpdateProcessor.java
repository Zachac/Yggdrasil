package model.updates;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Entity;
import model.charachters.Player;
import model.world.Coordinate;
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

	public static void publicUpdate(Tile t) {
		NetworkUpdate n = new NetworkUpdate();
		n.tiles.add(t);
		publicUpdate(t, n);
	}
	
	public static void publicUpdate(NetworkUpdate n) {
		Map<Integer, Coordinate> broadcastPositions = new TreeMap<>();

		if (!n.tiles.isEmpty()) {
			getBroadCastPositions(n.tiles, broadcastPositions);
		}

		Map<Integer, Integer> maxRanges = getMaxRange(n.tiles, broadcastPositions);

		broadcast(broadcastPositions, maxRanges, n);
	}

	public static void publicUpdate(Tile root, NetworkUpdate n) {
		String toSend = Serializer.prepare(n);
		TileTraverser.Handler h = (t) -> {
			for (Entity e : t.contents) {
				if (e instanceof Player) {
					((Player) e).messages.add(toSend);
				}
			}
		};

		TileTraverser.traverse(root, LookCommand.DEFAULT_LOOK, h);
	}

	/**
	 * Get's the average position for each z level in the list of tiles. If there is
	 * an existing position in the list of broad cast position, it won't be
	 * overwritten.
	 * 
	 * @param tiles
	 *            the tiles to average
	 * @param broadcastPositions
	 *            the positions to broadcast from
	 */
	private static void getBroadCastPositions(List<Tile> tiles, Map<Integer, Coordinate> broadcastPositions) {
		Map<Integer, AverageCoordinate> zLevels = new TreeMap<>();

		for (Tile t : tiles) {

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

			super.x = this.x / n;
			super.y = this.y / n;
		}

	}

	private static Map<Integer, Integer> getMaxRange(List<Tile> tiles, Map<Integer, Coordinate> broadcastPositions) {
		Map<Integer, Integer> result = new TreeMap<>();

		for (Tile t : tiles) {
			Integer lastMax = result.get(t.position.z);
			int newMax;

			if (lastMax == null) {
				result.put(t.position.z, t.position.getDistance(broadcastPositions.get(t.position.z)));
			} else if (lastMax < (newMax = t.position.getDistance(broadcastPositions.get(t.position.z)))) {
				result.put(t.position.z, newMax);
			}
		}

		return result;
	}

	private static void broadcast(Map<Integer, Coordinate> broadcastPositions, Map<Integer, Integer> maxRanges,
			NetworkUpdate n) {
		String toSend = Serializer.prepare(n);

		TileTraverser.Handler h = new UpdateSender(toSend);

		for (Integer z : broadcastPositions.keySet()) {
			TileTraverser.traverse(broadcastPositions.get(z), maxRanges.get(z) + LookCommand.DEFAULT_LOOK, h);
		}
	}

	public static void publicUpdate(Tile t, NetworkUpdate n, Coordinate extension) {

		int minX = t.position.x - LookCommand.DEFAULT_LOOK;
		int maxX = t.position.x + LookCommand.DEFAULT_LOOK;

		int minY = t.position.y - LookCommand.DEFAULT_LOOK;
		int maxY = t.position.y + LookCommand.DEFAULT_LOOK;

		if (extension.x > 0) {
			maxX += extension.x;
		} else {
			minX -= extension.x;
		}
		
		if (extension.y > 0) {
			maxY += extension.y;
		} else {
			minY -= extension.y;
		}
		
		TileTraverser.traverseAll(new UpdateSender(Serializer.prepare(n)),
				minX, maxX,
				minY, maxY,
				t.position.z);
	}
	
	private static final class UpdateSender implements TileTraverser.Handler {

		private final String toSend;
		
		public UpdateSender(String s) {
			this.toSend = s;
		}

		@Override
		public void run(Tile t) {
			for (Entity e : t.contents) {
				if (e instanceof Player) {
					((Player) e).messages.add(toSend);
				}
			}
		}
	}
}
