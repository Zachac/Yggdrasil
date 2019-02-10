package org.ex.yggdrasil.model.entities.players.specialization.specializations;

public class GatheringSpecialization extends AbstractSpecialization {

	private static final long serialVersionUID = 5333950295135146330L;

	public static final String NAME = "gathering";
	private static final int[] XP_REQUIREMENTS = { 0, 10, 100, 1_000, 10_000 };

	public GatheringSpecialization() {
		super(XP_REQUIREMENTS);
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
