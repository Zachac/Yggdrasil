package org.ex.yggdrasil.server;

import java.io.FileNotFoundException;

import org.ex.yggdrasil.model.world.Persistence;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.model.world.chunks.Biome;
import org.ex.yggdrasil.model.world.generation.SingleBiomeWorldGenerator;
import org.ex.yggdrasil.parser.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Server {

	private static final Logger LOG = LoggerFactory.getLogger(Server.class);
	
	public abstract void start();
	public abstract void shutdown();
	
	public void startWorld() {
		try {
			Persistence.load();
			LOG.info("World loaded");
		} catch (FileNotFoundException e) {
			LOG.error("Failed to load world, could not read save file.");
			LOG.info("Generating terrain");
			new SingleBiomeWorldGenerator(Biome.GRASS).generate(World.get().getRoot());
		}
		
		loadCommands();
		
		World.get().time.start();
		LOG.info("World time started at tick {}", World.get().time.getTickTime());
	}
	
	private void loadCommands() {
		Commands.getCommands();
	}
	
	public void closeWorld() {
		try {
			Persistence.save();
			LOG.info("World saved successfully");
		} catch (FileNotFoundException e) {
			LOG.error("Could not save world", e);
		}
		
		World.get().time.end();
	}
	
	
}
