package org.ex.yggdrasil.util;

import org.reflections.Reflections;

public class YggdrasilReflections {

	private static Reflections reflections;
	
	
	public static Reflections get() {
		if (reflections == null) {
			 reflections = new Reflections("org.ex.yggdrasil");
		}
		
		return reflections;
	}
	
}
