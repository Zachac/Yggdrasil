package org.ex.yggdrasil.server.serialization;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.Time.ContinuousEvent;
import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.updates.NetworkUpdate;
import org.ex.yggdrasil.model.world.Coordinate3D;
import org.ex.yggdrasil.server.serialization.adapters.ContinuousEventTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.Coordinate3DTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.EntityPositionTypeAdapter;
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
			.create();
	
	public static String prepare(NetworkUpdate n) {
		return gson.toJson(n);
	}
}
