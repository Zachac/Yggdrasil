package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

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
