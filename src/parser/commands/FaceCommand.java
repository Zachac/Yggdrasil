package parser.commands;

import model.updates.UpdateProcessor;
import model.world.Coordinate3D.Direction;
import parser.Command;
import parser.Command.CommandPattern.PatternNode.Flag;
import parser.CommandData;

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
		d.source.setFacing(Direction.valueOf(d.args.get(0).charAt(0)));
		UpdateProcessor.update(d.source);
	}
}
