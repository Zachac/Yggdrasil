package server.client;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;

import model.charachters.Player;
import server.client.input.ClientCommandIterator;

public class Client implements Runnable {

	public final ClientCommandIterator in;
	public final PrintStream out;
	
	Player player;
	ClientMessagePublisher mPublisher;
	
	private boolean closing;
	private Thread publisher;
	private Thread reader;
	
	public Client(ClientSocketAdapater c) {
		this(c.getInput(), c.getOuput());
	}
	
	public Client(ClientCommandIterator in, PrintStream out) {
		this.in = in;
		this.out = out;
		this.closing = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void run() {
		
		try {
			player = ClientLoginHandler.login(in, out);
			player.messages.add("Welcome to Yggdrasil Online");
			
			startSubThreads();
			this.close();
		} catch (Exception e) {
			if (player == null) {
				System.out.println("INFO: Player failed to login.");
			} else {
				System.out.println("INFO: Player " + player + " closed connection.");				
			}
		}
	}

	private void startSubThreads() {
		
		// Create a latch that the publisher can check if the reader has exited,
		// And so that we know when we can exit gracefully
		CountDownLatch latch = new CountDownLatch(1);
		mPublisher = new ClientMessagePublisher(this, latch);
		reader = new Thread(new ClientCommandReader(this, latch));
		publisher = Thread.currentThread();
		
		reader.start();
		mPublisher.run();
	}
	
	/**
	 * Interrupt the threads interacting with the client and close the respective in/out channels. 
	 */
	public synchronized void close() {
		if (!closing) {
			closing = true;
		}
		
		try { publisher.interrupt(); } catch (Exception e) { }
		try { reader.interrupt(); } catch (Exception e) { }
		try { this.in.close(); } catch (Exception e) { }
		try { this.out.close(); } catch (Exception e) { }
		
		if (player != null) {
			player.logout();
		}
	}

	public void join() throws InterruptedException {
		publisher.join();
		reader.join();
	}
}
