package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;

public class EchoCommand extends Command {

	public EchoCommand() {
		super("echo", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 
		
		pattern.add(".*", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));
		
		return pattern;
	}
}
