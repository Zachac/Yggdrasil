package org.ex.yggdrasil.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ex.yggdrasil.util.YggdrasilReflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Commands {
	
	private static final Logger LOG = LoggerFactory.getLogger(Commands.class);

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
		
		LOG.info("Loading commands");
		
		int loaded = 0;
		int total = 0;
		
		for (Class<?> cl : commands) {
			LOG.debug("Adding {}", cl);
			total++;
			try {
				Command c = (Command) cl.newInstance();
				result.put(c.name, c);
				loaded++;
			} catch (InstantiationException | ClassCastException | IllegalAccessException e) {
				LOG.error("Unnable to instantiate command {}", cl);
				LOG.error("Could not instantiate command", e);
			}
		}
		
		LOG.info("Loaded {}/{} commands", loaded, total);
		
		return Collections.unmodifiableMap(result);
	}
}
