package server.serialization.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import server.serialization.NetworkUpdate;

public class NetworkUpdateTypeAdapter implements JsonSerializer<NetworkUpdate> {

	@Override
	public JsonElement serialize(NetworkUpdate src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		if (src.localPlayer != null) {
			root.add("localPlayer", new JsonPrimitive(src.localPlayer.userName));
		}
		
		if (!src.tiles.isEmpty()) {
			root.add("tiles", context.serialize(src.tiles));
		}
		
		
		return root;
	}
}
