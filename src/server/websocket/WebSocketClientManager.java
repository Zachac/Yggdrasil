package server.websocket;

import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketListener;

import server.client.Client;

public class WebSocketClientManager extends WebSocketApplication {
	
	@Override
	public void onConnect(WebSocket s) {
		add(s);
		System.out.println("INFO: Connected " + s);
	}	
	
	@Override
	public WebSocket createSocket(ProtocolHandler handler, HttpRequestPacket requestPacket, WebSocketListener... listeners) {
		return new ExtendedWebSocket(handler, requestPacket, listeners);
	}
	
	@Override
	public void onClose(WebSocket socket, DataFrame frame) {
		super.onClose(socket, frame);
		
		Client c = ((ExtendedWebSocket) socket).getClient();
		
		if (c != null) {
			c.close();			
		}
		
		System.out.println("INFO: Player " + c.getPlayer() + " reason " + frame);
	}
	
	@Override
	public boolean add(WebSocket s) {
		boolean result = super.add(s);
		
		if (result) {
			Client c = new Client(new WebSocketClientAdapter(s));
			((ExtendedWebSocket) s).setClient(c);
			new Thread(c).start();
		}
		
		return result;
	}
}
