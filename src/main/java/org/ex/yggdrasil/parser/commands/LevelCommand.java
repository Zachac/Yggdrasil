package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;

public class LevelCommand extends Command {

	public LevelCommand() {
		super("level", getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add("up", PatternNode.Flag.OPTIONAL.value);
		pattern.add("[a-zA-Z]+", PatternNode.Flag.IMPORTANT.value);
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		String className = d.args.get(0);
		boolean result = d.source.specialization.levelUp(className);
		
		if (result) {
			d.source.messages.add("You leveled up " + className + " to " + d.source.specialization.getSpecialization(className).getCurrentLevel());
		} else {
			d.source.messages.add("Could not level up " + className);
		}
	}
}
