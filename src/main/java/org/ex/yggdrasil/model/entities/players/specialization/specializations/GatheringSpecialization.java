package org.ex.yggdrasil.model.entities.players.specialization.specializations;

import org.ex.yggdrasil.model.entities.players.specialization.AbstractSpecialization;

public class GatheringSpecialization extends AbstractSpecialization {

	private static final long serialVersionUID = 5333950295135146330L;

	private static final String NAME = "gathering";
	private static final int[] XP_REQUIREMENTS = {0, 10, 100, 1_000, 10_000};
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected int[] getXpRequirements() {
		return XP_REQUIREMENTS;
	}
}
