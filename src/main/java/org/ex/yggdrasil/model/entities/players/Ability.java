package org.ex.yggdrasil.model.entities.players;

import java.io.Serializable;

public abstract class Ability implements Comparable<Ability>, Serializable {

	private static final long serialVersionUID = -5992345171571084901L;
	
	public final String name;
	public final byte levelRequirement;
	
	public Ability(String name, byte levelRequirement) {
		this.name=name;
		this.levelRequirement=levelRequirement;
	}
	
	@Override
	public int compareTo(Ability other) {
		return this.levelRequirement - other.levelRequirement;
	}
	
}
