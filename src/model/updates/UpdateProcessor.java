package model.updates;

import java.util.Iterator;
import java.util.List;

import model.Entity;
import model.charachters.Player;
import model.world.Coordinate3D;
import model.world.Tile;
import model.world.TileTraverser;
import model.world.TileTraverser.Handler;
import parser.commands.LookCommand;

public class UpdateProcessor {

	/**
	 * Signals to everyone in range that this tile has changed.
	 * Does not include the contents of this tile.
	 * 
	 * @param location The tile that has changed.
	 */
	public static void update(Tile location) {
		TileTraverser.traverse(location, LookCommand.DEFAULT_LOOK, new UpdateAdder(location));
	}

	/**
	 * Signals to everyone in range that this entity has changed.
	 * @param e The entity that has changed.
	 */
	public static void update(Entity e) {
		TileTraverser.traverse(e.getPosition(), LookCommand.DEFAULT_LOOK, new UpdateAdder(e));
	}
	
	/**
	 * Sends these entities to the player.
	 * @param player The player to send to.
	 * @param entities The entities.
	 */
	public static void send(Player player, List<Entity> entities) {
		player.updates.addEntities(entities);
	}

	/**
	 * Sends the given tiles and their contents to the player.
	 * @param player The player to send to.
	 * @param tiles The tiles to send.
	 */
	public static void sendTiles(Player player, List<Tile> tiles) {
		player.updates.addTiles(tiles);
	}
	
	/**
	 * Sends this entity to everyone in range of the given position.
	 * @param position the position to send the update.
	 * @param e the entity that was updated.
	 */
	public static void send(Coordinate3D position, Entity e) {
		TileTraverser.traverse(position, LookCommand.DEFAULT_LOOK, new UpdateAdder(e));
	}
	
	/**
	 * Sends this entity to everyone in range of the given position. The distance modifier then
	 * defines the distance by LookCommand.DEFAULT_LOOK + distanceModifier.
	 * 
	 * @param position the position to send the update.
	 * @param e the entity that was updated.
	 * @param distanceModifier the modifier to the default distance.
	 */
	public static void send(Coordinate3D position, Entity e, int distanceModifier) {
		TileTraverser.traverse(position, LookCommand.DEFAULT_LOOK + distanceModifier, new UpdateAdder(e));
	}
	
	/**
	 * Sends the entire surroundings of the player to the player.
	 * @param source the player to send to.
	 */
	public static void completeUpdate(Player source) {
		TileTraverser.traverse(source.getLocation(), LookCommand.DEFAULT_LOOK, 
				(t) -> {
					source.updates.addEntity(t);
					source.updates.addEntities(t.contents);
				});
		
		source.updates.setLocalPlayer(source);
	}
	
	private static class UpdateAdder implements Handler {

		private Entity e;
		private List<Entity> ents;
		
		public UpdateAdder(Entity e) {
			this.e = e;
			
			if (e instanceof Tile) {
				ents = ((Tile) e).contents;
			}
		}
		
		@Override
		public void run(Tile t) {
			Iterator<Entity> iter = t.contents.iterator();
			
			while (iter.hasNext()) {
				Entity e = iter.next();
				if (e instanceof Player) {
					((Player) e).updates.addEntity(this.e);
					if (ents != null) {
						((Player) e).updates.addEntities(this.ents);
					}
				}
			}
		}
	}
}
