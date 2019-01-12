package server.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Time.ContinuousEvent;
import model.charachters.Player;
import model.updates.NetworkUpdate;
import model.world.Coordinate3D;
import model.world.Coordinate4D;
import model.world.Tile;
import server.serialization.adapters.ContinuousEventTypeAdapter;
import server.serialization.adapters.Coordinate3DTypeAdapter;
import server.serialization.adapters.Coordinate4DTypeAdapter;
import server.serialization.adapters.NetworkUpdateTypeAdapter;
import server.serialization.adapters.PlayerTypeAdapter;
import server.serialization.adapters.TileTypeAdapater;

public class Serializer {

	private static Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(ContinuousEvent.class, new ContinuousEventTypeAdapter())
			.registerTypeAdapter(Player.class, new PlayerTypeAdapter())
			.registerTypeAdapter(Tile.class, new TileTypeAdapater())
			.registerTypeAdapter(Coordinate3D.class, new Coordinate3DTypeAdapter())
			.registerTypeAdapter(Coordinate4D.class, new Coordinate4DTypeAdapter())
			.registerTypeAdapter(NetworkUpdate.class, new NetworkUpdateTypeAdapter())
			.create();
	
	public static String prepare(NetworkUpdate n) {
		return gson.toJson(n);
	}
}
