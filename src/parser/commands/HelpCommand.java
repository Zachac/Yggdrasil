package parser.commands;

import parser.Command;
import parser.CommandData;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		d.source.messages.add("but nobody came");
	}
}
