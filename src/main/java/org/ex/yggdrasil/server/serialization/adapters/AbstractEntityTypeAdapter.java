package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.entities.Entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public abstract class AbstractEntityTypeAdapter {

	public JsonObject serializeEntity(Entity src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("position", context.serialize(src.getPosition()));
		root.add("id", new JsonPrimitive(src.getId()));
		root.add("material", new JsonPrimitive(src.getMaterial().toString()));
		root.add("facing", new JsonPrimitive(src.getFacing().name()));
		
		return root;
	}

}
