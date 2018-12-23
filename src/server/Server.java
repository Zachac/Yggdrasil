package server;

import java.io.FileNotFoundException;

import model.Persistence;
import model.world.World;
import util.Strings;

public abstract class Server {

	
	public abstract void start();
	public abstract void shutdown();
	
	public void startWorld() {
		try {
			Persistence.load();
			System.out.println("INFO: World loaded.");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Failed to load world: " + Strings.stringify(e));
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
