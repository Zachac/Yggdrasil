package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.world.Biome;
import org.ex.yggdrasil.model.world.Chunk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ChunkTypeAdapter implements JsonSerializer<Chunk>  {

	@Override
	public JsonElement serialize(Chunk src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		Biome[][] tiles = new Biome[src.getXSize()][src.getYSize()];
		
		for (int x = 0; x < src.getXSize(); x++) {
			for (int y = 0; y < src.getYSize(); y++) {
				tiles[x][y] = src.getTile(x, y);
			}
		}
		
		return root;
	}

}
