package parser.commands;

import model.updates.UpdateProcessor;
import model.world.TileTraverser;
import parser.Command;
import parser.CommandData;
import server.serialization.NetworkUpdate;

public class SysUpdateCommand extends Command {

	public SysUpdateCommand() {
		super("sysupdate", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) {
		NetworkUpdate n = new NetworkUpdate();
		
		n.localPosition = d.source.getLocation().position;
		n.localPlayer = d.source;

		TileTraverser.traverse(d.source.getLocation(), LookCommand.DEFAULT_LOOK, (t) -> {
			n.tiles.add(t);
		});

		UpdateProcessor.privateUpdate(d.source, n);
	}
}
