package parser.commands;

import java.util.Iterator;

import model.world.Entity;
import model.world.Tile;
import model.world.TileTraverser;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

public class LookCommand extends Command {

	private static final String NOTHING_INDICATOR = "You see nothing.";
	private static final String ENTITY_LIST_PREFACE = "There is,\n";
	public static final int MAX_LOOK = 100;
	public static final int DEFAULT_LOOK = 10;
	
	public LookCommand() {
		this("look");
	}
	
	protected LookCommand(String name) {
		super(name, getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add("[0-9]+", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.OPTIONAL.value));
		pattern.add("closely", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.OPTIONAL.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		boolean detailed = d.args.contains("closely"); 
		int range = DEFAULT_LOOK;
		
		if (!d.args.isEmpty()) {
			try {
				range = Integer.parseInt(d.args.get(0));  
			} catch (NumberFormatException e) { }
		}
		
		if (range > MAX_LOOK) {
			range = MAX_LOOK;
		}
		
		d.source.messages.add(look(d.source.getLocation(), range, detailed));
	}
	
	public String look(Tile root, int range, boolean detailed) {
		
		StringBuilder result = new StringBuilder();

		result.append(ENTITY_LIST_PREFACE);
		
		TileTraverser.Handler h = new TileTraverser.Handler() {
			@Override
			public void run(Tile t) {
				look(result, t, detailed);
			}
		};
		
		TileTraverser.traverse(root, range, h);
		
		if (result.length() == ENTITY_LIST_PREFACE.length()) {
			result.setLength(0);
			result.append(NOTHING_INDICATOR);
		}
		
		return result.toString();
	}
	
	public void look(StringBuilder result, Tile root, boolean detailed) {
		
		Iterator<Entity> iter = root.contents.iterator();
		Entity e = null;
		
		if (iter.hasNext()) {
			e = iter.next();
			result.append('\t');
			result.append(e.toString());
			result.append('#');
			result.append(e.id);
			result.append('\n');
		}
		
		while (iter.hasNext()) {
			e = iter.next();
			result.append(e.toString());
			result.append('#');
			result.append(e.id);
			result.append('\n');
		}
	}
}
