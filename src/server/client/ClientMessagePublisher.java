package server.client;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import model.Time;
import server.exceptions.ClosedConnectionException;

class ClientMessagePublisher implements Runnable {

	private final Client c;
	private CountDownLatch latch;

	ClientMessagePublisher(Client client, CountDownLatch latch) {
		this.c = client;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			publishLoop();
		} catch (ClosedConnectionException e) { }
	}
	
	public void publishLoop() {
		try {
			while (!latch.await(Time.TICK_LENGTH, Time.TIME_UNIT)) {
				publishAll();
			}
		} catch (InterruptedException e) { }
	}

	public void publishAll() {
		Iterator<String> messages = c.player.messages.iterator();
		boolean hasMessages = messages.hasNext();
		
		while (messages.hasNext()) {
			c.out.println(messages.next());
			messages.remove();
		}
		
		if (c.player.updates.shouldSend()) {
			c.out.println(c.player.updates.consume());
			hasMessages = true;
		}

		if (hasMessages) {
			c.out.flush();
		}
	}
}