package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;

public class SpecializeCommand extends Command {

	public SpecializeCommand(String name) {
		super(name, getMyPattern(), false);
	}
	
	public SpecializeCommand() {
		this("specialize");
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 
		
		pattern.add("[a-zA-Z]+", PatternNode.Flag.IMPORTANT.value);
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		String className = d.args.get(0);
		boolean result = d.source.specialization.addClass(className);
		
		if (result) {
			d.source.messages.add("You specialized into " + className);
		} else {
			d.source.messages.add("Could not specialize into " + className);
		}
	}
	
	public static class SpecCommand extends SpecializeCommand {

		public SpecCommand() {
			super("spec");
		}
		
	}
}
