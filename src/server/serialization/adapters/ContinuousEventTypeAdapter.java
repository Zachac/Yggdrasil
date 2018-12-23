package server.serialization.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.world.Time.ContinuousEvent;

public class ContinuousEventTypeAdapter implements JsonSerializer<ContinuousEvent> {

	@Override
	public JsonElement serialize(ContinuousEvent src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getClass().getSimpleName());
	}

}
