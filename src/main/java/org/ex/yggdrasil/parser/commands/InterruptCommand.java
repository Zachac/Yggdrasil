package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

public class InterruptCommand extends Command {

	public InterruptCommand() {
		super("interrupt", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		d.source.setAction(null);
		d.source.messages.add("Current action interrupted.");
	}
}
