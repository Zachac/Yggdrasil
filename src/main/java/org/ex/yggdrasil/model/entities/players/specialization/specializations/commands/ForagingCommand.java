package org.ex.yggdrasil.model.entities.players.specialization.specializations.commands;

import org.ex.yggdrasil.model.entities.players.specialization.specializations.GatheringSpecialization;
import org.ex.yggdrasil.model.entities.resources.ResourceNodeType;

public class ForagingCommand extends AbstractResourceRetrievalCommand {

	public ForagingCommand() {
		super("forage", "Foraging", "You forage through the bush", GatheringSpecialization.NAME, 1, ResourceNodeType.BUSH);
	}
}
