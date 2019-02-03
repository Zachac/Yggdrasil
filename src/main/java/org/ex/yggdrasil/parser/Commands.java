package org.ex.yggdrasil.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ex.yggdrasil.util.YggdrasilReflections;

public class Commands {

	private static final Map<String,Command> commands = generateCommands();
	
	/**
	 * Get's the map of string command names to the commands themselves.
	 * @return an unmodifiable map of string to commands.
	 */
	public static Map<String, Command> getCommands() {
		return commands;
	}
	
	private static Map<String, Command> generateCommands() {
		HashMap<String, Command> result = new HashMap<String,Command>();
		
		Iterable<Class<? extends Command>> commands = YggdrasilReflections.get().getSubTypesOf(Command.class);
		
		System.out.println("INFO: Loading classes");
		
		int loaded = 0;
		int total = 0;
		
		for (Class<?> cl : commands) {
			total++;
			System.out.println("DEBUG: Adding " + cl);
			try {
				Command c = (Command) cl.newInstance();
				result.put(c.name, c);
				loaded++;
			} catch (InstantiationException | ClassCastException | IllegalAccessException e) {
				System.out.println("ERROR: Unnable to instantiate command: " + e.getMessage() + ": " + cl);
			}
		}
		
		System.out.println("INFO: Loaded " + loaded + "/" + total + " classes");
		
		return Collections.unmodifiableMap(result);
	}
}
