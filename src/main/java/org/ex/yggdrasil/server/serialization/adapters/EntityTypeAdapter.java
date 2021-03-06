package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.entities.Entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EntityTypeAdapter extends AbstractEntityTypeAdapter implements JsonSerializer<Entity>, TypeAdapter {

	@Override
	public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {
		return super.serializeEntity(src, typeOfSrc, context);
	}

	@Override
	public Class<?> getType() {
		return Entity.class;
	}
}
