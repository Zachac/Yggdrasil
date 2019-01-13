package parser.commands;

import model.world.Tile;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

public class RaiseCommand extends Command {

	public RaiseCommand() {
		super("raise", getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add("[-]?[0-9]+", (PatternNode.Flag.IMPORTANT.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		int change = Integer.parseInt(d.args.get(0));
		
		if (change!= 0) {
			Tile t = d.source.getLocation();
			t.setHeight(t.position.getW() + change);
		}
	}
}
