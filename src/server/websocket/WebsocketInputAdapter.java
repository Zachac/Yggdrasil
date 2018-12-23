package server.websocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketListener;

import server.client.input.ClientCommandIterator;
import server.exceptions.ClosedConnectionException;

public class WebsocketInputAdapter implements ClientCommandIterator {

	private BlockingQueue<String> messages;
	private WebSocket socket;
	
	public WebsocketInputAdapter(WebSocket s) {
		
		messages = new LinkedBlockingQueue<>();
		socket = s;
		
		s.add(new WebSocketListener() {

			@Override public void onMessage(WebSocket socket, String text) {
				messages.add(text);
			}
			
			@Override public void onClose(WebSocket socket, DataFrame frame) { }
			@Override public void onConnect(WebSocket socket) { }
			@Override public void onMessage(WebSocket socket, byte[] bytes) { }
			@Override public void onPing(WebSocket socket, byte[] bytes) { }
			@Override public void onPong(WebSocket socket, byte[] bytes) { }
			@Override public void onFragment(WebSocket socket, String fragment, boolean last) { }
			@Override public void onFragment(WebSocket socket, byte[] fragment, boolean last) { }
		});
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public String next() {
		try {
			return messages.take();
		} catch (InterruptedException e) {
			throw new ClosedConnectionException(e);
		}
	}

	@Override
	public void close() {
		if (socket.isConnected()) {
			socket.close();			
		}
	}
}
