package parser.commands;

import model.Entity;
import model.charachters.Player;
import model.world.Tile;
import model.world.TileTraverser;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

public class SayCommand extends Command {

	public static final int SAY_RANGE = 30;

	public SayCommand() {
		this("say");
	}
	
	protected SayCommand(String name) {
		super(name, getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add(".+", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		StringBuilder result = new StringBuilder();

		addPreface(result, d);
		addMessage(result, d);
		sendMessage(result.toString(), d.source.getLocation());
	}
	
	public void sendMessage(String message, Tile location, int range) {
		TileTraverser.traverse(location, range, (Tile t) -> {
			t.contents.forEach((Entity e) -> {
				if (e instanceof Player) {
					((Player) e).messages.add(message);
				}
			});
		});
	}
	
	public void sendMessage(String s, Tile location) {
		sendMessage(s, location, getRange());
	}
	
	protected void addPreface(StringBuilder result, CommandData d) {
		result.append(d.source);
		result.append(':');
	}
	
	protected void addMessage(StringBuilder result, CommandData d) {
		for (String s : d.args) {
			result.append(' ');
			result.append(s);
		}
	}

	protected int getRange() {
		return SAY_RANGE;
	}
	
}
