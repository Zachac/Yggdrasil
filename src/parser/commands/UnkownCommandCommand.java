package parser.commands;

import parser.Command;
import parser.CommandData;

public class UnkownCommandCommand extends Command {

	public UnkownCommandCommand() {
		super("?", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		d.source.messages.add("Unkown command: " + d.args.get(0));
	}
}
