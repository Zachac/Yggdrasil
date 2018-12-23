package local;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import model.world.Persistence;
import model.world.World;
import server.client.Client;
import server.socket.InputStreamIteratorAdapter;

public class LocalGame {

	public static void main(String[] args) {
		try {
			Persistence.load();
		} catch (FileNotFoundException e) {
			System.out.println(util.Strings.stringify(e));
		}
		
		World.get().time.start();
		
		PrintStream out = System.out;
		try (InputStreamIteratorAdapter in = new InputStreamIteratorAdapter(System.in)) {
			new Client(in, out).join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			Persistence.save();
		} catch (FileNotFoundException e) {
			System.out.println(util.Strings.stringify(e));
		}
		
		World.get().time.end();
	}
}
