package org.ex.yggdrasil.model.world.generation;

import org.ex.yggdrasil.model.world.Tile.Biome;

import fastnoise.FastNoise;
import fastnoise.FastNoise.NoiseType;

public class PerlinWorldGenerator extends AbstractSingleBiomeWorldGenerator {

	private final FastNoise noise;
	private final float scale;
	private final float height;

	public PerlinWorldGenerator(Biome b) {
		this(b, 8, 25);
	}

	public PerlinWorldGenerator(Biome b, float scale, float height) {
		this(b, scale, height, (int) (System.currentTimeMillis() & 0xFF_FF_FF_FFL));
	}
	
	public PerlinWorldGenerator(Biome b, float scale, float height, int seed) {
		super(b);
		
		if (scale == 0) {
			throw new IllegalArgumentException("Cannot divide by 0.");
		}
		
		this.scale = scale;
		this.height = height;
		this.noise = new FastNoise(seed);
		noise.SetNoiseType(NoiseType.Perlin);
	}

	@Override
	public float chooseHeight(Integer...integers) {
		float result = 0;

		int k = 0;
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] != null) {
				k++;
				result += integers[i];
			}
		}
		
		return result/k;
	}
	
	@Override
	public int getW(int i, int j) {
		float raw_result = this.noise.GetPerlin((float) i/scale, (float) j/scale) * height;
		int result = Math.round(raw_result);
		return result;
	}

}
