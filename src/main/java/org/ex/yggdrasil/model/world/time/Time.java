package org.ex.yggdrasil.model.world.time;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
			t = new TickThread(() -> {
				ContinuousEventExecutor.execute(this.tickers);
				this.tickTime++;
			});
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
}
