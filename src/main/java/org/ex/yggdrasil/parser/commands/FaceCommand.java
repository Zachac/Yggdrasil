package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.Direction3D;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode.Flag;

public class FaceCommand extends Command {

	public FaceCommand() {
		super("face", getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern result = new CommandPattern();
		
		result.add("[neswNESW]", Flag.IMPORTANT.value);
		
		return result;
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		d.source.setFacing(Direction3D.valueOf(d.args.get(0).charAt(0)));
		UpdateProcessor.update(d.source);
	}
}
