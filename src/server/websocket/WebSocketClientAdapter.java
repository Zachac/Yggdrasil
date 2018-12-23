package server.websocket;

import java.io.PrintStream;

import org.glassfish.grizzly.websockets.WebSocket;

import server.client.ClientSocketAdapater;
import server.client.input.ClientCommandIterator;

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
