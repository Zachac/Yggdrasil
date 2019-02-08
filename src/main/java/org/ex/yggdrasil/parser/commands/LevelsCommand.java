package org.ex.yggdrasil.parser.commands;

import java.util.Map.Entry;

import org.ex.yggdrasil.model.entities.players.specialization.specializations.AbstractSpecialization;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

public class LevelsCommand extends Command {

	public LevelsCommand() {
		super("levels", getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		StringBuilder result = new StringBuilder();
		
		for (Entry<String, AbstractSpecialization>  e : d.source.specialization.getClasses()) {
			result.append(e.getValue().getCurrentLevel());
			result.append(" : ");
			result.append(e.getKey());
			result.append('\n');
		}
		
		result.append("Total Levels: ");
		result.append(d.source.specialization.getTotalLevels());
		
		d.source.messages.add(result.toString());
	}
}
