package org.ex.yggdrasil.model.world.generation;

import java.util.Random;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.resources.ResourceNode;
import org.ex.yggdrasil.model.entities.resources.ResourceNodeType;
import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.model.world.chunks.Direction3D;

public class RandomResourceDistributionWorldGenerator extends SingleBiomeWorldGenerator {

	private static final Random R = new Random();
	
	private ResourceNodeType resourceType;
	private float density;

	public RandomResourceDistributionWorldGenerator(Biome b, ResourceNodeType type) {
		super(b);
		this.resourceType = type;
		this.density = (float) (1.0/16.0);
	}

	@Override
	public void populate(Chunk c) {
		
		int tiles = c.getXSize() * c.getYSize();
		int toPlace = (int) (density * tiles);
		
		int sizeX = c.getXSize();
		int sizeY = c.getYSize();
		
		Direction3D[] directions = Direction3D.values(); 
		
		for (int i = 0; i < toPlace; i++) {
			int x = Math.min((int) (R.nextFloat() * sizeX), sizeX - 1);
			int y = Math.min((int) (R.nextFloat() * sizeY), sizeY - 1);
			
			if (!c.isWall(x, y) && x != 0 && y != 0) {
				Entity e = c.add(new ResourceNode(this.resourceType, c, x, y));
				e.setFacing(directions[R.nextInt(directions.length)]);
				c.setWall(true, x, y);
			}
		}
	}
}
