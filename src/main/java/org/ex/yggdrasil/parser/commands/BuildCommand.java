package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Direction3D;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;

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

		for (Biome b : Biome.values()) {
			if (first) {
				first = false;
			} else {
				biomeTypes.append('|');
			}

			biomeTypes.append(b.toString());
		}

		pattern.add(biomeTypes.toString(), (PatternNode.Flag.IMPORTANT.value));
		pattern.add("[nsewudc]", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.OPTIONAL.value));

		return pattern;
	}

	@Override
	public void run(CommandData d) {

		Biome type = Biome.valueOf(d.args.get(0).toUpperCase());

		Direction3D direction = Direction3D.valueOf(d.args.get(1).charAt(0));

		if (direction == null) {
			direction = Direction3D.C;
		}

		int x = d.source.position.getX() + direction.direction.getX();
		int y = d.source.position.getY() + direction.direction.getY();
		
		if (d.source.getChunk().legalPosition(x, y)) {
			d.source.getChunk().setTile(type, x, y);
		} else {
			d.source.messages.add("Edge of chunk reached at " + x + ", " + y);
		}
	}
}
