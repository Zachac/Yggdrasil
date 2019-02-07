package org.ex.yggdrasil.model.updates;

import java.util.Collection;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Chunk;
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
		source.updates.setFollowEntity(source);
		source.updates.setChunk(source.getChunk());

		Enumerator<Player> players = source.getChunk().getPlayers();
		while (players.hasMoreElements()) {
			source.updates.addEntity(players.nextElement());
		}

		Collection<Entity> entities = source.getChunk().getEntities();
		
		for (Entity e : entities) {			
			source.updates.addEntity(e);
		}
	}

	public static void update(Chunk chunk, Biome type, int x, int y) {
		BiomeUpdate b = new BiomeUpdate(type, x, y);
		Enumerator<Player> players = chunk.getPlayers();
		
		while (players.hasMoreElements()) {
			players.nextElement().updates.addBiomeUpdate(b);
		}
	}
}
