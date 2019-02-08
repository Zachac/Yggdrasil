package org.ex.yggdrasil.model.entities.players.specialization.specializations;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ex.yggdrasil.model.entities.players.specialization.Specialization;
import org.ex.yggdrasil.model.entities.players.specialization.Specializations;

import java.util.Set;

public class SpecializationLevels implements Serializable {

	private static final long serialVersionUID = 3046583792948878317L;
	
	public static final int MAX_LEVELS = 100;

	private Map<String,AbstractSpecialization> levels;
	private transient Set<Entry<String, AbstractSpecialization>> levelsView;

	private int totalLevels = 0;
	
	public SpecializationLevels() {
		this.totalLevels=0;
		this.levels = new HashMap<>();
		this.levelsView = Collections.unmodifiableSet(this.levels.entrySet());
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.levelsView = Collections.unmodifiableSet(this.levels.entrySet());
	}
	
	public int getTotalLevels() {
		return totalLevels;
	}
	
	public boolean addClass(String name) {
		if (totalLevels >= MAX_LEVELS) {
			return false;
		} else if (levels.containsKey(name)) {
			return false;
		}
		
		AbstractSpecialization result = Specializations.newInstance(name);
		if (result == null) {
			return false;
		}
		
		levels.put(result.getName(), result);
		totalLevels += result.getCurrentLevel();
		return true;
	}
	
	public Specialization getSpecialization(String name) {
		return levels.get(name);
	}
	
	public synchronized boolean levelUp(String name) {
		boolean result;
		
		if (totalLevels >= MAX_LEVELS) {
			result = false;
		} else {			
			AbstractSpecialization s = levels.get(name);
			
			if (s == null) {
				result = false;
			} else {
				result = s.levelUp();
				
				if (result) {
					totalLevels++;
				}
			}
		}

		return result;
	}

	public Iterable<Entry<String, AbstractSpecialization>> getClasses() {
		return levelsView;
	}
}
