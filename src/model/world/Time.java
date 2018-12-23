package model.world;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Time implements Serializable {

	private static final long serialVersionUID = -508161503959612624L;

	public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
	public static final int TICK_LENGTH = 500;
	
	private final LinkedList<ContinuousEvent> oddTickers;
	private final LinkedList<ContinuousEvent> evenTickers;
	
	private long tickTime;
	
	private transient Thread t;
	
	public Time() {
		oddTickers = new LinkedList<>();
		evenTickers = new LinkedList<>();
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

		if (e instanceof ContinuousEventOdd) {
			oddTickers.add(e);
		}
		
		if (e instanceof ContinuousEventEven) {
			evenTickers.add(e);
		}
		
		if (!(e instanceof ContinuousEventOdd) && !(e instanceof ContinuousEventEven)) {
			evenTickers.add(e);
			oddTickers.add(e);
		}
		
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
		
	}
	
	public static interface ContinuousEventOdd extends ContinuousEvent {
		/**
		 * Execute a single part of the continuous event only on the odd tick of the server.
		 * Must be added to Time.addContinuuousEvent. 
		 * @return if this continuous event has finished.
		 */
		public boolean tick();
	}
	
	public static interface ContinuousEventEven extends ContinuousEvent {

		/**
		 * Execute a single part of the continuous event only on the even tick of the server.
		 * Must be added to Time.addContinuousEvent. 
		 * @return if this continuous event has finished.
		 */
		public boolean tick();
	}
	
	private class ContinuousEventExecutor implements Runnable {

		@Override
		public void run() {
			boolean interrupted = false;
			
			while (!interrupted) {
				
				if ((tickTime & 1) == 0) {
					execute(evenTickers);
				} else {
					execute(oddTickers);
				}
				
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
				boolean hasFinished = iter.next().tick();
				
				if (hasFinished) {
					iter.remove();
				}
			}
		}
	}
}
