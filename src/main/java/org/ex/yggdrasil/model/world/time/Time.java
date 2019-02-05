package org.ex.yggdrasil.model.world.time;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Time implements Serializable {

	private static final long serialVersionUID = -508161503959612624L;

	private static final Logger LOG = LoggerFactory.getLogger(Time.class);

	public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
	public static final int TICK_LENGTH = 500;

	private final LinkedList<ContinuousEvent> tickers;
	private final PriorityQueue<ScheduledEvent> scheduledEvents;
	private transient Thread[] threads;

	private long tickTime;

	public Time() {
		threads = new Thread[ThreadType.COUNT];
		tickers = new LinkedList<>();
		scheduledEvents = new PriorityQueue<>();
		tickTime = 0;
	}

	private void readObject(java.io.ObjectInputStream in) {
		threads = new Thread[ThreadType.COUNT];
	}

	public void start() {
		for (ThreadType t : ThreadType.values()) {
			int i = t.ordinal();
			if (threads[i] == null) {
				try {
					Constructor<? extends Thread> constructor = t.threadClass.getDeclaredConstructor(Time.class);
					constructor.setAccessible(true);
					threads[i] = constructor.newInstance(this);
					threads[i].start();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					LOG.error("Could not create thread for Time", e);
				}
			}
		}
	}

	public void end() {
		for (ThreadType t : ThreadType.values()) {
			int i = t.ordinal();
			threads[i].interrupt();
			threads[i] = null;
		}
	}

	public long getTickTime() {
		return tickTime;
	}

	public void addContinuousEvent(ContinuousEvent e) {
		Objects.requireNonNull(e);
		tickers.add(e);
	}

	public static void scheduleEvent(Runnable reset, long respawnTime) {

	}

	private enum ThreadType {
		CONTINUOUS_EVENTS(TickThread.class);

		public static final int COUNT = ThreadType.values().length;
		public final Class<? extends Thread> threadClass;

		ThreadType(Class<? extends Thread> c) {
			this.threadClass = c;
		}
	}

	/**
	 * Thread to run a given runnable with a tick length wait in-between. The
	 * runnable is executed synchronously so the total time between executions for
	 * the runnable would be (run_time + tick_length * time_unit).
	 * 
	 * @author Zachary Chandler
	 */
	private class TickThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					ScheduledEventExecutor.execute(scheduledEvents);
					ContinuousEventExecutor.execute(tickers);
					tickTime++;
					Time.TIME_UNIT.sleep(Time.TICK_LENGTH);
				}
			} catch (InterruptedException e) { }
		}
	}
}
