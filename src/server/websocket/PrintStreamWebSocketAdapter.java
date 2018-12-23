package server.websocket;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.glassfish.grizzly.websockets.WebSocket;

import server.exceptions.ClosedConnectionException;

public class PrintStreamWebSocketAdapter extends PrintStream {

	private WebSocket s;

	public PrintStreamWebSocketAdapter(WebSocket s) {
		super(new ByteArrayOutputStream());
		
		this.s = s;
	}
	
	@Override
	public void flush() {
		super.flush();
		
		if (!s.isConnected()) {
			throw new ClosedConnectionException();
		}
		
		try {
			String str = ((ByteArrayOutputStream) out).toString("UTF-8");
			
			if (str.length() > 0) {
				s.send(str);				
			}
			
			((ByteArrayOutputStream) out).reset();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void close() {
		super.close();
		
		if (s.isConnected()) {
			s.close();
		}
	}
	
}
