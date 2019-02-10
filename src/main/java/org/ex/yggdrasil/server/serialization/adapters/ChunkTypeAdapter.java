package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Chunk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ChunkTypeAdapter implements JsonSerializer<Chunk>, TypeAdapter {

	@Override
	public JsonElement serialize(Chunk src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		Biome[][] tiles = new Biome[src.getXSize()][src.getYSize()];
		
		for (int x = 0; x < src.getXSize(); x++) {
			for (int y = 0; y < src.getYSize(); y++) {
				tiles[x][y] = src.getTile(x, y);
			}
		}

		root.add("id", new JsonPrimitive(src.getId()));
		root.add("tiles", context.serialize(tiles));
		
		return root;
	}

	@Override
	public Class<?> getType() {
		return Chunk.class;
	}
}
