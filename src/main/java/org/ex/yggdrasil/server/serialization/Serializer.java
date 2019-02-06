package org.ex.yggdrasil.server.serialization;

import org.ex.yggdrasil.model.Material;
import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.entities.resources.ResourceNode;
import org.ex.yggdrasil.model.updates.BiomeUpdate;
import org.ex.yggdrasil.model.updates.NetworkUpdate;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.model.world.chunks.Coordinate3D;
import org.ex.yggdrasil.model.world.time.ContinuousEvent;
import org.ex.yggdrasil.server.serialization.adapters.BiomeUpdateTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.ChunkTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.ContinuousEventTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.Coordinate3DTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.EntityPositionTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.EntityTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.MaterialTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.NetworkUpdateTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.PlayerTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {

	private static Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(ContinuousEvent.class, new ContinuousEventTypeAdapter())
			.registerTypeAdapter(Player.class, new PlayerTypeAdapter())
			.registerTypeAdapter(Coordinate3D.class, new Coordinate3DTypeAdapter())
			.registerTypeAdapter(NetworkUpdate.class, new NetworkUpdateTypeAdapter())
			.registerTypeAdapter(Entity.EntityPosition.class, new EntityPositionTypeAdapter())
			.registerTypeAdapter(Chunk.class, new ChunkTypeAdapter())
			.registerTypeAdapter(Material.class, new MaterialTypeAdapter())
			.registerTypeAdapter(BiomeUpdate.class, new BiomeUpdateTypeAdapter())
			.registerTypeAdapter(ResourceNode.class, new EntityTypeAdapter())
			.create();
	
	public static String prepare(NetworkUpdate n) {
		return gson.toJson(n);
	}
}
