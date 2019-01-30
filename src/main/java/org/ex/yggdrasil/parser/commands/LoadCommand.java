package org.ex.yggdrasil.parser.commands;

import java.io.File;

import org.ex.yggdrasil.model.world.Persistence;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;

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
			result = org.ex.yggdrasil.util.Strings.stringify(e);
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
