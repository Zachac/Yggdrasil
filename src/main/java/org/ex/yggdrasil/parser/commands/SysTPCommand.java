package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.Coordinate3D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode.Flag;

public class SysTPCommand extends Command {

	public SysTPCommand() {
		super("systp", getMyCommandPattern(), true);
	}

	private static CommandPattern getMyCommandPattern() {
		CommandPattern result = new CommandPattern();

		result.add("[0-9]+", Flag.IMPORTANT.value);
		result.add("[0-9]+", Flag.IMPORTANT.value);
		result.add("[0-9]+", Flag.IMPORTANT.value | Flag.OPTIONAL.value);
		
		return result;
	}
	
	@Override
	public void run(CommandData d) throws CommandException {
		int x = 0;
		int y = 0;
		int z = 0;
		
		try {
			x = Integer.parseInt(d.args.get(0));
			y = Integer.parseInt(d.args.get(1));
			
			if (d.args.size() < 2) {
				z = Integer.parseInt(d.args.get(2));				
			}
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { }
		
		Coordinate3D c = new Coordinate3D(x,y,z);
		Tile t = World.get().getTile(c);
		
		if (t == null) {
			d.source.messages.add("Invalid location, could not find " + c);
			return;
		}
		
		teleport(d.source, t);
		
		d.source.messages.add("With a pop, you arrive at " + d.source.getLocation().position);
	}

	protected void teleport(Player p, Tile t) {
		Tile previous = p.getLocation();
		
		p.move(t);

		UpdateProcessor.update(previous);
		UpdateProcessor.update(t);
		UpdateProcessor.completeUpdate(p);
	}

	
}
