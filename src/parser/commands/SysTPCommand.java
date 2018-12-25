package parser.commands;

import model.updates.UpdateProcessor;
import model.world.Coordinate;
import model.world.Tile;
import model.world.World;
import parser.Command;
import parser.Command.CommandPattern.PatternNode.Flag;
import server.serialization.NetworkUpdate;
import parser.CommandData;
import parser.CommandParser;

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
		
		Coordinate c = new Coordinate(x,y,z);
		Tile t = World.get().getTile(c);
		
		if (t == null) {
			d.source.messages.add("Invalid location, could not find " + c);
		} else {
			NetworkUpdate previousPos = new NetworkUpdate();
			previousPos.tiles.add(d.source.getLocation());
			
			NetworkUpdate newPos = new NetworkUpdate();
			newPos.tiles.add(t);
			
			d.source.move(t);
			
			UpdateProcessor.publicUpdate(previousPos.tiles.get(0), previousPos);			
			CommandParser.parse(new String[] {"sysupdate"}, d.source).run();
			UpdateProcessor.publicUpdate(newPos.tiles.get(0), newPos);
			
			d.source.messages.add("With a pop, you arrive at " + d.source.getLocation().position);
		}
	}

	
}
