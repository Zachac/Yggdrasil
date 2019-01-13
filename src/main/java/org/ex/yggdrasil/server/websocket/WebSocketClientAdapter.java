package org.ex.yggdrasil.server.websocket;

import java.io.PrintStream;

import org.ex.yggdrasil.server.client.ClientSocketAdapater;
import org.ex.yggdrasil.server.client.input.ClientCommandIterator;
import org.glassfish.grizzly.websockets.WebSocket;

public class WebSocketClientAdapter implements ClientSocketAdapater {
	
	private final PrintStream out;
	private final WebsocketInputAdapter in;
	
	public WebSocketClientAdapter(WebSocket s) {
		in = new WebsocketInputAdapter(s);
		out = new PrintStreamWebSocketAdapter(s);
	}

	@Override
	public ClientCommandIterator getInput() {
		return in;
	}

	@Override
	public PrintStream getOuput() {
		return out;
	}
}
