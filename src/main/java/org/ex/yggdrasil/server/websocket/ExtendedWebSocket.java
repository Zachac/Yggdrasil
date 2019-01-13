package org.ex.yggdrasil.server.websocket;

import org.ex.yggdrasil.server.client.Client;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DefaultWebSocket;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

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
