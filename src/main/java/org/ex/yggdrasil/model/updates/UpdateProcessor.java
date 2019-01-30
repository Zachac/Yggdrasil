package org.ex.yggdrasil.model.updates;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.charachters.Player;
import org.glassfish.grizzly.http.server.util.Enumerator;

public class UpdateProcessor {

	/**
	 * Signal to all players in the chunk that this entity has been updated.
	 * @param e the entity that was updated.
	 */
	public static void update(Entity e) {
		Enumerator<Player> players = e.getChunk().getPlayers();
		
		while (players.hasMoreElements()) {
			players.nextElement().updates.addEntity(e);
		}
	}

	public static void completeUpdate(Player source) {
		source.updates.setLocalPlayer(source);
		source.updates.setChunk(source.getChunk());

		Enumerator<Player> players = source.getChunk().getPlayers();
		while (players.hasMoreElements()) {
			source.updates.addEntity(players.nextElement());
		}
		
		Enumerator<Entity> entities = source.getChunk().getEntities();
		while (entities.hasMoreElements()) {
			source.updates.addEntity(entities.nextElement());
		}
	}
}
