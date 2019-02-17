package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

public class InventoryCommand extends Command {

	public InventoryCommand() {
		super("inventory");
	}
	
	protected InventoryCommand(String name) {
		super(name);
	}

	@Override
	public void run(CommandData d) throws CommandException {
		d.source.messages.add(d.source.inventory.toString());
	}

	public static class InvCommand extends InventoryCommand {
	
		public InvCommand() {
			super("inv");
		}
	}
}
