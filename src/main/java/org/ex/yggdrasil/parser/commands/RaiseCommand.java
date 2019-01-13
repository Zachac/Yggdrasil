package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;

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
