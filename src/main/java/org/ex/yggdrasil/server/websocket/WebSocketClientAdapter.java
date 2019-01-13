package org.ex.yggdrasil.server.websocket;

import java.io.PrintStream;

import org.ex.yggdrasil.server.client.ClientSocketAdapater;
import org.ex.yggdrasil.server.client.input.ClientCommandIterator;
import org.glassfish.grizzly.websockets.WebSocket;

public class WebSocketClientAdapter implements ClientSocketAdapater {
	
	private final PrintStream out;
	private final WebsocketInputAdapter in;
	private final WebSocket socket;
	
	public WebSocketClientAdapter(WebSocket s) {
		this.socket = s;
		this.in = new WebsocketInputAdapter(s);
		this.out = new PrintStreamWebSocketAdapter(s);
	}

	@Override
	public ClientCommandIterator getInput() {
		return in;
	}

	@Override
	public PrintStream getOuput() {
		return out;
	}

	@Override
	public void close() {
		this.socket.close();
	}
}
