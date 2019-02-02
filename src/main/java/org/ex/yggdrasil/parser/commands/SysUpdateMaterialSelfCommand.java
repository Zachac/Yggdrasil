package org.ex.yggdrasil.parser.commands;

import org.ex.yggdrasil.model.Material;
import org.ex.yggdrasil.model.entities.EntityMaterial;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;

public class SysUpdateMaterialSelfCommand extends Command {

	public SysUpdateMaterialSelfCommand() {
		super("sysupdatematerialself", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern result = new CommandPattern();
		
		StringBuilder options = new StringBuilder();

		options.append("(?i)");
		
		Material[] mats = EntityMaterial.values();
		
		if (mats.length != 0) {
			options.append(mats[0].toString());
		}
		
		for (int i = 1; i < mats.length; i++) {
			options.append("|");
			options.append(mats[i].toString());
		}
		
		result.add(options.toString(), PatternNode.Flag.IMPORTANT.value);
		
		return result;
	}
	
	@Override
	public void run(CommandData d) {
		d.source.setMaterial(EntityMaterial.valueOf(d.args.get(0).toUpperCase()));
	}
}
