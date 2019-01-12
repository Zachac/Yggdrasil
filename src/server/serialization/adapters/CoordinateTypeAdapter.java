package server.serialization.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.world.Coordinate;

public class CoordinateTypeAdapter implements JsonSerializer<Coordinate> {

	@Override
	public JsonElement serialize(Coordinate src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("x", new JsonPrimitive(src.getX()));
		root.add("y", new JsonPrimitive(src.getY()));
		root.add("z", new JsonPrimitive(src.getZ()));
		
		return root;
	}

}
