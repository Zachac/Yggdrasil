package server.client;

import java.util.concurrent.CountDownLatch;

import parser.Command.CommandException;
import parser.Command.ExitException;
import server.exceptions.ClosedConnectionException;
import parser.CommandParser;

class ClientCommandReader implements Runnable {

	private final Client c;
	private CountDownLatch latch;

	ClientCommandReader(Client client, CountDownLatch latch) {
		this.c = client;
		this.latch = latch;
	}

	@Override
	public void run() {
		Thread.currentThread().setUncaughtExceptionHandler((th, thr) -> {
			thr.printStackTrace();
			latch.countDown();
			c.player.messages.add("An unexpected exception occured during the previous session. Sorry about that,");
		});
		
		try {
			messageReadingLoop();
		} catch (ClosedConnectionException e) { }
		latch.countDown();
	}
	
	private void messageReadingLoop() {
		boolean exit = false;
		
		while (!exit && c.in.hasNext()) {
			try {
				String[] commands = c.in.next().toLowerCase().split(";");
				
				for (String s : commands) {
					CommandParser.parse(s.trim().split("\\s+"), c.player).run();					
				}
				
				c.mPublisher.publishAll();
			} catch (ExitException e) {
				exit = true;
			} catch (CommandException e) {
				c.out.println("Unkown exception reached.");
				e.printStackTrace();
			}
		}
		
		c.player.messages.add("Now exiting, thanks for playing!");
		c.mPublisher.publishAll();
	}
}