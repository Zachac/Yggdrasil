package org.ex.yggdrasil.server;

import java.io.FileNotFoundException;

import org.ex.yggdrasil.model.Persistence;
import org.ex.yggdrasil.model.world.Tile.Biome;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.model.world.generation.PerlinWorldGenerator;
import org.ex.yggdrasil.util.Strings;

public abstract class Server {

	
	public abstract void start();
	public abstract void shutdown();
	
	public void startWorld() {
		try {
			Persistence.load();
			System.out.println("INFO: World loaded.");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Failed to load world: " + Strings.stringify(e));
			System.out.println("INFO: Generating terrain...");
			new PerlinWorldGenerator(Biome.GRASS, 8, 25).generate(new SearchField(-50, -50, 50, 50, 0));
		}
		
		World.get().time.start();
		System.out.println("INFO: World time started at tick " + World.get().time.getTickTime());
	}
	
	public void closeWorld() {
		try {
			Persistence.save();
			System.out.println("INFO: World saved successfully.");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Could not save world: " + Strings.stringify(e));
		}
		
		World.get().time.end();
	}
	
	
}
