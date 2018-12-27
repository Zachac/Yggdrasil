package parser.commands;

import model.updates.UpdateProcessor;
import parser.Command;
import parser.CommandData;

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
