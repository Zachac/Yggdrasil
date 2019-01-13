package org.ex.yggdrasil.parser.commands;

import java.io.File;

import org.ex.yggdrasil.model.Persistence;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;

public class SaveCommand extends Command {

	public SaveCommand() {
		super("save", getMyPattern(), true);
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
			result = saveWorld(fileName);
		}
		
		d.source.messages.add(result.toString());
	}

	private String saveWorld(String fileName) {
		String result;
		try {
			Persistence.save(new File(fileName));
			result = "Saved world succesfully!";
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
