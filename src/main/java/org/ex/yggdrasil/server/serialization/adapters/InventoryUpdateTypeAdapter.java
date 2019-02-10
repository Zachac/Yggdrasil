package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.updates.InventoryUpdate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InventoryUpdateTypeAdapter implements JsonSerializer<InventoryUpdate>, TypeAdapter {

	@Override
	public JsonElement serialize(InventoryUpdate src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("i", new JsonPrimitive(src.i));
		
		boolean exists = src.item != null; 
		
		root.add("exists", new JsonPrimitive(exists));
		
		if (exists) {
			root.add("name", new JsonPrimitive(src.item.toString()));
		}
		
		return root;
	}

	@Override
	public Class<?> getType() {
		return InventoryUpdate.class;
	}

}
