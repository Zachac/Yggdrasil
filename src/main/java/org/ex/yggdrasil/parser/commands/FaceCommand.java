package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.world.chunks.Direction2D;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode.Flag;
import org.ex.yggdrasil.parser.CommandData;

public class FaceCommand extends Command {

	public FaceCommand() {
		super("face", getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern result = new CommandPattern();
		
		result.add("[nsewNSEW]{1,2}", Flag.IMPORTANT.value);
		
		return result;
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		String s = d.args.get(0);
		Direction2D d1 = Direction2D.valueOf(s.charAt(0));
		Direction2D d2 = null;
		
		if (s.length() > 1) {
			d2 = Direction2D.valueOf(s.charAt(1));
		}

		Direction2D direction;
		
		if (d2 != null) {
			direction = d1.combine(d2);
		} else {
			direction = d1;
		}
		
		d.source.setFacing(direction);
	}
}
