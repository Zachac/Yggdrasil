package org.ex.yggdrasil.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.ex.yggdrasil.server.Server;

public class SocketServer extends Server {

	public static final String SERVER_PORT_PROPERTY_NAME = "yggdrasil.server.port";
	public static final int SERVER_DEFAULT_PORT = 9080;
	
	private static ServerSocket s;
	private int port = -1; 
	
	@Override
	public void start() {
		new Thread (() -> {
			try {
				port = getPort();
				s = new ServerSocket(port);
				while(!s.isClosed()) {
					Socket sc = s.accept();
					System.out.println("INFO: Accepted " + s);
					sc.close(); // to do...
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public ServerSocket getServerSocket() {
		return s;
	}
	
	public int getPort() {
		if (port > 0) {
			return port;
		}
		
		port = SERVER_DEFAULT_PORT;
		
		try {
			String number = System.getProperty(SERVER_PORT_PROPERTY_NAME);
			if (number != null) {				
				port = Integer.parseInt(number);
			}
		} catch (NumberFormatException e) {
			System.err.println("WARNING: Unable to read port property, malformed number");
		}
		
		return port;
	}

	@Override
	public void shutdown() {
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ServerSocket getServer() {
		return s;
	}
}
