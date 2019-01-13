package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

public class SysUpdateCommand extends Command {

	public SysUpdateCommand() {
		super("sysupdate", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) {
		UpdateProcessor.completeUpdate(d.source);
	}
}
