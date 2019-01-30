package org.ex.yggdrasil.server.serialization;

import org.ex.yggdrasil.model.Time.ContinuousEvent;
import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.updates.NetworkUpdate;
import org.ex.yggdrasil.model.world.Coordinate3D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.server.serialization.adapters.ContinuousEventTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.Coordinate3DTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.NetworkUpdateTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.PlayerTypeAdapter;
import org.ex.yggdrasil.server.serialization.adapters.TileTypeAdapater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {

	private static Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(ContinuousEvent.class, new ContinuousEventTypeAdapter())
			.registerTypeAdapter(Player.class, new PlayerTypeAdapter())
			.registerTypeAdapter(Tile.class, new TileTypeAdapater())
			.registerTypeAdapter(Coordinate3D.class, new Coordinate3DTypeAdapter())
			.registerTypeAdapter(NetworkUpdate.class, new NetworkUpdateTypeAdapter())
			.create();
	
	public static String prepare(NetworkUpdate n) {
		return gson.toJson(n);
	}
}
