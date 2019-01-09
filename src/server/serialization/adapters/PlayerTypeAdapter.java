package server.serialization.adapters;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.Time.ContinuousEvent;
import model.charachters.Player;

public class PlayerTypeAdapter implements JsonSerializer<Player> {

	@Override
	public JsonElement serialize(Player src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject root = new JsonObject();

		root.add("position", context.serialize(src.getPosition()));
		root.add("id", new JsonPrimitive(src.getId()));
		root.add("userName", new JsonPrimitive(src.userName));
		root.add("facing", new JsonPrimitive(src.getFacing().name()));
		
		List<ContinuousEvent> actions = src.getActions();
		
		if (!actions.isEmpty()) {
			root.add("actions", context.serialize(actions));
		}

		
		return root;
	}
}
