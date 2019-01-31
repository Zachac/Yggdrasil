package org.ex.yggdrasil.model.world.time;

/**
 * Thread to run a given runnable with a tick length wait in-between. The runnable
 * is executed synchronously so the total time between executions for the runnable
 * would be (run_time + tick_length * time_unit).
 * 
 * @author Zachary Chandler
 */
public class TickThread extends Thread {

	private final Runnable r;
	
	public TickThread(Runnable r) {
		this.r = r;
	}
	
	public void start() {
		try {
			while (true) {
				r.run();
				Time.TIME_UNIT.sleep(Time.TICK_LENGTH);					
			}
		} catch (InterruptedException e) { }
	}
}
