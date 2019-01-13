package org.ex.yggdrasil.server.websocket;

import java.io.IOException;

import org.ex.yggdrasil.server.Server;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;

public class WebSocketServer extends Server {

	private HttpServer s;

	@Override
	public void start() {
		shutdown();
		
		this.s = HttpServer.createSimpleServer("src/main/webapp/", 9090);
		this.disableFileCaching();
		this.registerWebSocketListener();
		
		try {
			this.s.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerWebSocketListener() {
		WebSocketAddOn a = new WebSocketAddOn();		
		for (NetworkListener l : s.getListeners()) {
			l.registerAddOn(a);
		}
		
		WebSocketEngine.getEngine().register("", "/connect", new WebSocketClientManager());
	}

	private void disableFileCaching() {
		s.getServerConfiguration().getHttpHandlersWithMapping().entrySet().stream().forEach((e) -> {
			HttpHandler h = e.getKey();

			if (h instanceof StaticHttpHandler) {
				((StaticHttpHandler) h).setFileCacheEnabled(false);
			}
		});
	}

	@Override
	public void shutdown() {
		if (this.s != null && this.s.isStarted()) {
			this.s.shutdown();			
		}
	}

}
