package org.ex.yggdrasil.server.client;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;

import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.server.client.input.ClientCommandIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	public final ClientCommandIterator in;
	public final PrintStream out;
	
	Player player;
	ClientMessagePublisher mPublisher;
	
	private boolean closing;
	private Thread publisher;
	private Thread reader;
	private ClientSocketAdapater socket;
	
	public Client(ClientSocketAdapater c) {
		this.socket = c;
		this.in = c.getInput();
		this.out = c.getOuput();
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
		} catch (Exception e) {
			if (player == null) {
				LOG.info("Player failed to login");
			} else {
				LOG.info("Unexpected exception for player {}", player);
				e.printStackTrace();				
			}
		}
		
		this.close();
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
		} else {
			return;
		}

		try { this.publisher.interrupt(); } catch (Exception e) { }
		try { this.reader.interrupt(); } catch (Exception e) { }
		try { this.in.close(); } catch (Exception e) { }
		try { this.out.close(); } catch (Exception e) { }
		try { this.socket.close(); } catch (Exception e) { }
		
		if (player != null) {
			player.logout();
		}
	}

	public void join() throws InterruptedException {
		publisher.join();
		reader.join();
	}
}
