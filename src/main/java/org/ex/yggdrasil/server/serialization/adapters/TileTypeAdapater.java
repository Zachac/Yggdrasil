package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.world.Tile;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TileTypeAdapater implements JsonSerializer<Tile>  {

	@Override
	public JsonElement serialize(Tile src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("position", context.serialize(src.getPosition()));
		root.add("id", new JsonPrimitive(src.getId()));
		root.add("biome", new JsonPrimitive(src.getType().toString()));
		
		if (src.isWall()) {
			root.add("isWall", new JsonPrimitive(src.isWall()));
		}
		
		return root;
	}

}
