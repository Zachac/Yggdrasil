package org.ex.yggdrasil.server.websocket;

import java.io.IOException;
import java.util.logging.Level;

import org.ex.yggdrasil.server.Server;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer extends Server {
	
	private static final int PORT = 9090;

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);
	
	private HttpServer s;

	@Override
	public void start() {
		shutdown();
		configureGrrizzlyLogging();
		this.s = HttpServer.createSimpleServer("src/main/webapp/", PORT);
		LOG.info("Started server on port {}", PORT);
		this.disableFileCaching();
		this.registerWebSocketListener();
		
		try {
			this.s.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void configureGrrizzlyLogging() {
		java.util.logging.Logger.getLogger("org.glassfish.grizzly.http.server").setLevel(Level.WARNING);
	}

	private void registerWebSocketListener() {
		WebSocketAddOn a = new WebSocketAddOn();		
		for (NetworkListener l : s.getListeners()) {
			l.registerAddOn(a);
		}
		
		WebSocketEngine.getEngine().register("", "/connect", new WebSocketClientManager());
		LOG.info("Registerd websocket listener");
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
