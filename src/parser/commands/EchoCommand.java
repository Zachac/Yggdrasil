package parser.commands;

import parser.Command;
import parser.Command.CommandPattern.PatternNode;

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
