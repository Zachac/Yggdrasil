package org.ex.yggdrasil;

import java.io.IOException;

import org.ex.yggdrasil.server.Server;
import org.ex.yggdrasil.server.websocket.WebSocketServer;

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
