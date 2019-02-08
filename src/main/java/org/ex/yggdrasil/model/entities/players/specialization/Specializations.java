package org.ex.yggdrasil.model.entities.players.specialization;

import java.util.HashMap;
import java.util.Map;

import org.ex.yggdrasil.model.entities.players.specialization.specializations.AbstractSpecialization;
import org.ex.yggdrasil.util.YggdrasilReflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Specializations {

	private static final Logger LOG = LoggerFactory.getLogger(Specializations.class);
	
	private static final Map<String, Class<? extends AbstractSpecialization>> SPECIALIZATION_MAP = getSpecializationMap();
	
	public static AbstractSpecialization newInstance(String name) {
		Class<? extends AbstractSpecialization> c = SPECIALIZATION_MAP.get(name);
		if (c == null) {
			return null;
		}
		
		AbstractSpecialization a = null;
		
		try {
			 a = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return a;
	}

	private static Map<String, Class<? extends AbstractSpecialization>> getSpecializationMap() {
		Map<String, Class<? extends AbstractSpecialization>> result = new HashMap<>();
		
		Iterable<Class<? extends AbstractSpecialization>> clazzes = YggdrasilReflections.get().getSubTypesOf(AbstractSpecialization.class);
		
		LOG.info("Loading specializations");

		int loaded = 0;
		int total = 0;
		
		for (Class<? extends AbstractSpecialization> c : clazzes) {
			try {
				total++;
				AbstractSpecialization a = c.newInstance();
				result.put(a.getName(), c);
				loaded++;
			} catch (InstantiationException | IllegalAccessException e) {
				LOG.error("Unable to instantiate specialization {}", c);
				LOG.error("Could not instantiate specialization", e);
			}
			
		}
		
		LOG.info("Loaded {}/{} specializations", loaded, total);
		
		return result;
	}

}
