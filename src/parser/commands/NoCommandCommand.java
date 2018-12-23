package parser.commands;

import parser.Command;
import parser.CommandData;

public class NoCommandCommand extends Command {

	public NoCommandCommand() {
		super("", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) {
		
	}
}
