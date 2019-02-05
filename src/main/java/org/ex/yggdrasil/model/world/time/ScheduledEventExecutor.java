package org.ex.yggdrasil.model.world.time;

import java.util.PriorityQueue;

public class ScheduledEventExecutor {

	public static final void execute(PriorityQueue<ScheduledEvent> scheduledEvents) {
		long curTime = System.currentTimeMillis();
		
		while (scheduledEvents.peek().whenToExecute <= curTime) {
			scheduledEvents.poll().r.run();			
		}
	}
}
