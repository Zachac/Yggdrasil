package server;

import java.io.IOException;

import server.websocket.WebSocketServer;

public class Main {

	public static void main(String[] args) {
		Server s = new WebSocketServer();
		
		s.startWorld();
		s.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			s.closeWorld();
        }));
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		s.shutdown();
		System.exit(0);
	}
}
