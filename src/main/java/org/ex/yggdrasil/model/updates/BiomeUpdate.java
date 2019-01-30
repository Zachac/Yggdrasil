package org.ex.yggdrasil.model.updates;

import java.io.Serializable;
import java.util.Objects;

import org.ex.yggdrasil.model.world.Biome;

public class BiomeUpdate implements Serializable, Comparable<BiomeUpdate> {

	private static final long serialVersionUID = 1146124524499271594L;

	public final int x, y;
	public final Biome b;
	
	public BiomeUpdate(Biome b, int x, int y) {
		super();
		
		Objects.requireNonNull(b);
		
		this.x = x;
		this.y = y;
		this.b = b;
	}

	@Override
	public int compareTo(BiomeUpdate arg0) {
		int result;
		
		result = this.b.ordinal() - arg0.b.ordinal();
		if (result != 0) {
			return result;
		}
		
		result = this.x - arg0.x;
		if (result != 0) {
			return result;
		}
		
		result = this.y - arg0.y;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b.ordinal();
		result = prime * result + x;
		result = prime * result + y;
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
		BiomeUpdate other = (BiomeUpdate) obj;
		if (b != other.b)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Biome getBiome() {
		return b;
	}
}
