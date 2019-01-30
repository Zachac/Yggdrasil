package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.updates.BiomeUpdate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BiomeUpdateTypeAdapter implements JsonSerializer<BiomeUpdate>  {

	@Override
	public JsonElement serialize(BiomeUpdate src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("x", new JsonPrimitive(src.getX()));
		root.add("y", new JsonPrimitive(src.getY()));
		root.add("biome", new JsonPrimitive(src.getBiome().toString()));
		
		return root;
	}

}
