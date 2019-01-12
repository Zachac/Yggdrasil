package parser.commands;

import java.util.Iterator;

import model.updates.UpdateProcessor;
import model.world.Coordinate3D.Direction;
import model.world.Tile;
import model.world.Tile.Biome;
import model.world.World;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

public class BuildCommand extends Command {

	public BuildCommand() {
		this("build");
	}

	protected BuildCommand(String name) {
		super(name, getMyPattern(), true);
	}

	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern();

		StringBuilder biomeTypes = new StringBuilder();

		biomeTypes.append("(?i)");

		boolean first = true;

		for (Biome b : Tile.Biome.values()) {
			if (first) {
				first = false;
			} else {
				biomeTypes.append('|');
			}

			biomeTypes.append(b.toString());
		}

		pattern.add(biomeTypes.toString(), (PatternNode.Flag.IMPORTANT.value));
		pattern.add("[0-9]+[nsewud]", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));

		return pattern;
	}

	@Override
	public void run(CommandData d) {

		Tile cursor = d.source.getLocation();

		Iterator<String> paths = d.args.iterator();
		Biome type;
		

		try {
			type = Biome.valueOf(paths.next().toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DEBUG: " + d.args);
			d.source.messages.add("ERROR: This should not ever happen, please report this bug.");
			return;
		}

		BuildReturnValue result = new BuildReturnValue(cursor, true);
		
		do {
			result = place(type, result.cursor, paths.next());
		} while (result.completed && paths.hasNext());
		
		if (result.completed) {
			d.source.messages.add("Completed at " + result.cursor.position);
		} else {
			d.source.messages.add("Could not complete, collision at " + result.cursor.position);			
		}
	}

	public static BuildReturnValue place(Biome type, Tile cursor, String path) {
		Direction d;
		int length;

		try {
			d = Direction.valueOf(path.charAt(path.length() - 1));
			length = Integer.parseInt(path.substring(0, path.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DEBUG: " + path);
			return new BuildReturnValue(cursor, false);
		}

		while (cursor.links[d.ordinal()] == null && length > 0) {
			
			Tile swap = World.get().addTile(cursor.position.get(d), type);
			UpdateProcessor.update(swap);
			
			if (d == Direction.D || d == Direction.U) {
				cursor.links[d.ordinal()] = swap;
				swap.links[d.opposite().ordinal()] = cursor;
			}
			
			length--;
			
			cursor = swap;
		}
		
		return new BuildReturnValue(cursor, length <= 0);
	}

	/**
	 * Java doesn't allow multiple return values...
	 */
	private static class BuildReturnValue {
		public final Tile cursor;
		public final boolean completed;
		
		public BuildReturnValue(Tile cursor, boolean result) {
			this.cursor = cursor;
			this.completed = result;
		}
	}
}
