package server.websocket;

import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DefaultWebSocket;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import server.client.Client;

public class ExtendedWebSocket extends DefaultWebSocket {
	
	private Client client;

	public ExtendedWebSocket(ProtocolHandler handler, HttpRequestPacket packet, WebSocketListener[] listeners) {
		super(handler, packet, listeners);
	}

	public Client getClient() {
		return this.client;
	}
	
	public void setClient(Client c) {
		this.client = c;
	}
}
