package org.ex.yggdrasil.server.serialization;

import java.util.Set;

import org.ex.yggdrasil.model.updates.NetworkUpdate;
import org.ex.yggdrasil.server.serialization.adapters.TypeAdapter;
import org.ex.yggdrasil.util.YggdrasilReflections;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {

	private static final Logger LOG = LoggerFactory.getLogger(Serializer.class);
	
	private static Gson gson = getSerializer();
	
	public static String prepare(NetworkUpdate n) {
		return gson.toJson(n);
	}
	
	private static Gson getSerializer() {
		LOG.info("Building serialization classes");
		
		Reflections r = YggdrasilReflections.get();
		
		Set<Class<? extends TypeAdapter>> classes = r.getSubTypesOf(TypeAdapter.class);
		
		GsonBuilder builder = new GsonBuilder();
		
		int total = 0;
		int loaded = 0;
		
		for (Class<? extends TypeAdapter> c : classes) {
			LOG.debug("Adding {}", c);
			try {
				total++;
				TypeAdapter adapter = c.newInstance();
				builder.registerTypeAdapter(adapter.getType(), adapter);
				loaded++;
			} catch (Exception e) {
				LOG.error("Failed to load {}", c.getName());
				LOG.error("Unexpected exception", e);
			}
		}
		
		LOG.info("Loaded {}/{} serializers", total, loaded);
		
		return builder.create();
	}
}
