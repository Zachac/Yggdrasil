package local;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import server.socket.SocketServer;

public class LocalClient {

	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket s = new Socket("localhost", SocketServer.getServer().getLocalPort());
		
		Scanner stdin = new Scanner(System.in);
		Scanner in = new Scanner(s.getInputStream());
		PrintStream out = new PrintStream(s.getOutputStream(), true);

		Thread inputThread = new Thread(() -> {
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
		});
		
		Thread outputThread = new Thread(() -> {
			while(stdin.hasNextLine()) {
				out.println(stdin.nextLine());
			}
		});
	
		inputThread.start();
		outputThread.start();
		
		try {
			inputThread.join();
			outputThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		stdin.close();
		in.close();
		s.close();
	}
	
	
	
	
}
