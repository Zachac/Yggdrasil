package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;
import org.glassfish.grizzly.http.server.util.Enumerator;

public class LookCommand extends Command {

	private static final String NOTHING_INDICATOR = "You see nothing.";
	private static final String ENTITY_LIST_PREFACE = "There is,\n";
	public static final int MAX_LOOK = 100;
	public static final int DEFAULT_LOOK = 15;
	
	public LookCommand() {
		this("look");
	}
	
	protected LookCommand(String name) {
		super(name, getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add("closely", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.OPTIONAL.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		boolean verbose = d.args.contains("closely");
		d.source.messages.add(look(d.source.getChunk(), verbose));
	}
	
	public String look(Chunk location, boolean verbose) {
		
		StringBuilder description = new StringBuilder();
		description.append(ENTITY_LIST_PREFACE);
		
		boolean hasElements = false;
		
		Enumerator<Entity> e = location.getEntities();
		hasElements = hasElements || e.hasMoreElements();
		while(e.hasMoreElements()) {
			append(description, e.nextElement(), verbose);
		}
		
		String result;
		
		if (hasElements) {
			result = description.toString(); 
		} else {
			result = NOTHING_INDICATOR;
		}
		
		return result;
	}
	
	public static void append(StringBuilder result, Entity e, boolean verbose) {
		
		result.append('\t');
		result.append(e.toString());
		
		if (verbose) {
			result.append('#');
			result.append(e.id);			
		}
		
		result.append('\n');
	}
}
