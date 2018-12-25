package server.serialization.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.Entity;
import model.world.Tile;

public class TileTypeAdapater implements JsonSerializer<Tile>  {

	@Override
	public JsonElement serialize(Tile src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();
		
		root.add("biome", new JsonPrimitive(src.type.toString()));
		root.add("position", context.serialize(src.position));
		
		if (src.isWall) {
			root.add("isWall", new JsonPrimitive(src.isWall));			
		}
		
		if (!src.contents.isEmpty()) {
			JsonArray contents = new JsonArray();
			
			for (Entity e : src.contents) {
				JsonObject o = context.serialize(e).getAsJsonObject();
				o.add("type", new JsonPrimitive(e.getClass().getSimpleName()));
				contents.add(o);
			}
			
			root.add("contents", contents);			
		}
		
		root.add("id", new JsonPrimitive(src.getId()));
		
		return root;
	}

}
