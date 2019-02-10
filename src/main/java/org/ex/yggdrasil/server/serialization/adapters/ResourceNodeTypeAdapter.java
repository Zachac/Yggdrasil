package org.ex.yggdrasil.server.serialization.adapters;

import java.lang.reflect.Type;

import org.ex.yggdrasil.model.entities.resources.ResourceNode;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResourceNodeTypeAdapter extends AbstractEntityTypeAdapter implements JsonSerializer<ResourceNode>, TypeAdapter {

	@Override
	public JsonElement serialize(ResourceNode src, Type typeOfSrc, JsonSerializationContext context) {
		return super.serializeEntity(src, typeOfSrc, context);
	}

	@Override
	public Class<?> getType() {
		return ResourceNode.class;
	}
}
