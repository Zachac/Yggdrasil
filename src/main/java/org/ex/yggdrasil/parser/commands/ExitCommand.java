package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;

public class ExitCommand extends Command {
	public ExitCommand() {
		super("exit", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 
		
		pattern.add(".*", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		throw new ExitException();
	}
}
