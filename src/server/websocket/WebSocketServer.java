package server.websocket;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;

import server.Server;

public class WebSocketServer extends Server {

	private HttpServer s;

	@Override
	public void start() {
		shutdown();
		
		this.s = HttpServer.createSimpleServer("src/webapp/", 9090);
		WebSocketAddOn a = new WebSocketAddOn();		
		for (NetworkListener l : s.getListeners()) {
			l.registerAddOn(a);
		}
		
		WebSocketEngine.getEngine().register("", "/connect", new WebSocketClientManager());
		try {
			this.s.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
		if (this.s != null && this.s.isStarted()) {
			this.s.shutdown();			
		}
	}

}
