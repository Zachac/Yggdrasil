package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Tile.Biome;

import fastnoise.FastNoise;
import fastnoise.FastNoise.NoiseType;

public class PerlinWorldGenerator extends FlatWorldGenerator {

	private final FastNoise noise;
	private final float scale;

	public PerlinWorldGenerator(Biome b) {
		this(b, 8, (int) (System.currentTimeMillis() & 0xFF_FF_FF_FFL));
	}

	public PerlinWorldGenerator(Biome b, float scale, int seed) {
		super(b);
		
		if (scale == 0) {
			throw new IllegalArgumentException("Cannot divide by 0.");
		}
		
		this.scale = scale;
		this.noise = new FastNoise(seed);
		noise.SetNoiseType(NoiseType.Perlin);
	}

	@Override
	public int getW(int i, int j) {
		System.out.println();
		float raw_result = this.noise.GetPerlin((float) i/scale, (float) j/scale) * 5 * 5 * 5;
		int result = Math.round(raw_result);
		return result;
	}

}
