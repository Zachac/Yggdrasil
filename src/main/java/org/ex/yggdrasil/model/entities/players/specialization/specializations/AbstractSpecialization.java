package org.ex.yggdrasil.model.entities.players.specialization.specializations;

import java.io.Serializable;

import org.ex.yggdrasil.model.entities.players.specialization.Specialization;

public abstract class AbstractSpecialization implements Specialization, Serializable, Comparable<Specialization> {

	private static final long serialVersionUID = -8420468571521584420L;

	private int level;
	private int xp;
	
	public AbstractSpecialization() {
		this.level = 1;
		this.xp = 0;
	}

	protected abstract int[] getXpRequirements();
	
	protected final boolean levelUp() {
		if (level >= getMaxLevel()) {
			return false;
		} if (xp < getNextLevelXp()) {
			return false;
		}
		
		this.level++;
		return true;
	}
	
	protected void addXp(int xp) {
		this.xp += xp;
	}
	
	@Override
	public int getMaxLevel() {
		return getXpRequirements().length;
	}
	
	@Override
	public int getCurrentLevel() {
		return this.level;
	}

	@Override
	public int getCurrentXp() {
		return this.xp;
	}

	@Override
	public int getNextLevelXp() {
		return getXpRequirements()[this.level];
	}
	
	@Override
	public int compareTo(Specialization other) {
		return this.getName().compareTo(other.getName());
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return this.getName().equals(((AbstractSpecialization) obj).getName());
	}
}
