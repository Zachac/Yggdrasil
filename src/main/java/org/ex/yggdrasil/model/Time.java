package org.ex.yggdrasil.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.ex.yggdrasil.model.entities.players.Player;

public class Time implements Serializable {

	private static final long serialVersionUID = -508161503959612624L;

	public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
	public static final int TICK_LENGTH = 500;
	
	private final LinkedList<ContinuousEvent> tickers;
	
	private long tickTime;
	
	private transient Thread t;
	
	public Time() {
		tickers = new LinkedList<>();
		tickTime = 0;
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(new ContinuousEventExecutor());
			t.start();
		}
	}
	
	public void end() {
		if (t != null) {
			t.interrupt();
			t = null;
		}
	}
	
	public long getTickTime() {
		return tickTime;
	}
	
	public void addContinuousEvent(ContinuousEvent e) {
		Objects.requireNonNull(e);
		tickers.add(e);
	}

	public static interface ContinuousEvent extends Serializable { 
		/**
		 * Execute a single part of the continuous event on the odd and even tick of the server.
		 * Must be added to Time.addContinuuousEvent. 
		 * @return if this continuous event has finished.
		 */
		public boolean tick();
		
		/**
		 * A method to indicate the event was cancelled. All subsequent calls to tick() 
		 * should return false and should not contain side effects. 
		 */
		public void cancel();

		/**
		 * Set's the player who's action is this continuous event.
		 * When this event ends, naturally, the player's action is set to null.
		 * @param p the player to set.
		 */
		public void setPlayer(Player p);
		
		public Player getPlayer();
		
		/**
		 * Get the user friendly name of this action.
		 * @return 
		 */
		public String getPrettyName();
		
	}
	
	private class ContinuousEventExecutor implements Runnable {

		@Override
		public void run() {
			boolean interrupted = false;
			
			while (!interrupted) {
				execute(tickers);
				
				try {
					TIME_UNIT.sleep(TICK_LENGTH);
				} catch (InterruptedException e) {
					interrupted = true;
				}
				
				tickTime++;
			}
		}
		
		private void execute(LinkedList<ContinuousEvent> l) {
		
			Iterator<ContinuousEvent> iter = l.iterator();
			
			while (iter.hasNext()) {
				boolean hasFinished = safelyExecute(iter.next());
				if (hasFinished) {
					iter.remove();
				}
			}
		}

		public boolean safelyExecute(ContinuousEvent event) {
			boolean hasFinished;
			try {
				hasFinished = event.tick();					
			} catch (Exception e) {
				e.printStackTrace();
				Player p = event.getPlayer();
				if (p != null) {
					p.messages.add("Exception occured while attempting to execute action.");
					p.setAction(null);
				}
				hasFinished = true;
			}
			return hasFinished;
		}
	}
}
