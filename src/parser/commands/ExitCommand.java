package parser.commands;

import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

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
