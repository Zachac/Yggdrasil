package model.charachters;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Class implements Comparable<Class>, Serializable {

	private static final long serialVersionUID = 8032677692919720250L;
	
	public final String name;
	public final byte levelCap;
	public List<Ability> abilities;
	
	public Class(String name, byte levelCap, Ability... abilities) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(abilities);
		
		this.name = name;
		this.levelCap = levelCap;
		
		Arrays.sort(abilities);
		
		this.abilities = Collections.unmodifiableList(Arrays.asList(abilities));
	}
	
	public String getName() {
		return name;
	}
	
	public byte getLevelCap() {
		return levelCap;
	}

	@Override
	public int compareTo(Class arg0) {
		return this.name.compareTo(arg0.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
