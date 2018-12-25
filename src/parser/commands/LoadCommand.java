package parser.commands;

import java.io.File;

import model.Persistence;
import model.world.World;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

public class LoadCommand extends Command {

	private LoadCommand() {
		super("load", getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 
		
		pattern.add("[a-zA-Z0-9.]+", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.OPTIONAL.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		String fileName = getFileName(d);
		String result;
		
		if (fileName == null) {
			result = "Must specify filename.";
		} else {
			result = loadWorld(fileName);
		}
		
		d.source.messages.add(result.toString());
	}

	private String loadWorld(String fileName) {
		String result;
		try {
			Persistence.load(new File(fileName));
			result = "Loaded world succesfully!";
			
		} catch (Exception e) {
			result = util.Strings.stringify(e);
		}
		return result;
	}

	private static String getFileName(CommandData d) {
		String fileName;
		if (d.args.isEmpty()) {
			fileName = World.get().getFilename();
		} else {
			fileName = d.args.get(0);
		}
		return fileName;
	}
}
