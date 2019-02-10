package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.updates.NetworkUpdate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NetworkUpdateTypeAdapter implements JsonSerializer<NetworkUpdate>, TypeAdapter {

	@Override
	public JsonElement serialize(org.ex.yggdrasil.model.updates.NetworkUpdate src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		if (src.getFollowEntity() != null) {
			root.add("follow", new JsonPrimitive(src.getFollowEntity().getId()));
		}

		if (!src.getEntities().isEmpty()) {
			root.add("entities", context.serialize(src.getEntities()));
		}
		
		if (!src.getInventoryUpdates().isEmpty()) {
			root.add("inventory", context.serialize(src.getInventoryUpdates()));
		}

		if (!src.getBiomeUpdates().isEmpty()) {
			root.add("biomes", context.serialize(src.getBiomeUpdates()));
		}
		
		if (src.getChunk() != null) {
			root.add("chunk", context.serialize(src.getChunk()));
		}
		
		return root;
	}

	@Override
	public Class<?> getType() {
		return NetworkUpdate.class;
	}
}
