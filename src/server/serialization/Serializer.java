package server.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.charachters.Player;
import model.world.Coordinate;
import model.world.Tile;
import model.world.Time.ContinuousEvent;
import server.serialization.adapters.ContinuousEventTypeAdapter;
import server.serialization.adapters.CoordinateTypeAdapter;
import server.serialization.adapters.NetworkUpdateTypeAdapter;
import server.serialization.adapters.PlayerTypeAdapter;
import server.serialization.adapters.TileTypeAdapater;

public class Serializer {

	private static Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(ContinuousEvent.class, new ContinuousEventTypeAdapter())
			.registerTypeAdapter(Player.class, new PlayerTypeAdapter())
			.registerTypeAdapter(Tile.class, new TileTypeAdapater())
			.registerTypeAdapter(Coordinate.class, new CoordinateTypeAdapter())
			.registerTypeAdapter(NetworkUpdate.class, new NetworkUpdateTypeAdapter())
			.create();
	
	public static String prepare(NetworkUpdate n) {
		return gson.toJson(n);
	}
}
