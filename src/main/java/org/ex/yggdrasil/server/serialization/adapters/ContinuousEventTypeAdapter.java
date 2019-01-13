package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.Time.ContinuousEvent;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ContinuousEventTypeAdapter implements JsonSerializer<ContinuousEvent> {

	@Override
	public JsonElement serialize(ContinuousEvent src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getClass().getSimpleName());
	}

}
