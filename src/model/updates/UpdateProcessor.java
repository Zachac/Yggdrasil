package model.updates;

import java.util.Iterator;
import java.util.List;

import model.Entity;
import model.charachters.Player;
import model.world.Tile;
import model.world.TileTraverser;
import model.world.TileTraverser.Handler;
import parser.commands.LookCommand;

public class UpdateProcessor {

	public static void update(Tile location) {
		TileTraverser.traverse(location, LookCommand.DEFAULT_LOOK, new UpdateAdder(location));
	}

	public static void completeUpdate(Player source) {
		TileTraverser.traverse(source.getLocation(), LookCommand.DEFAULT_LOOK, 
				(t) -> {
					source.updates.addTile(t);
				});
		
		source.updates.setLocalPlayer(source);
	}

	public static void update(Player player, List<Tile> tiles) {
		player.updates.addTiles(tiles);
	}

	private static class UpdateAdder implements Handler {

		public final Tile t;
		
		public UpdateAdder(Tile t) {
			this.t = t;
		}
		
		@Override
		public void run(Tile t) {
			Iterator<Entity> iter = t.contents.iterator();
			
			while (iter.hasNext()) {
				Entity e = iter.next();
				if (e instanceof Player) {
					((Player) e).updates.addTile(this.t);
				}
			}
		}
	}
}
