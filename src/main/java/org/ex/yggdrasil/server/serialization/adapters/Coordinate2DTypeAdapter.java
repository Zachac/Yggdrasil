package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.world.chunks.Coordinate2D;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Coordinate2DTypeAdapter implements JsonSerializer<Coordinate2D> {

	@Override
	public JsonElement serialize(Coordinate2D src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("x", new JsonPrimitive(src.getX()));
		root.add("y", new JsonPrimitive(src.getY()));
		
		return root;
	}

}
