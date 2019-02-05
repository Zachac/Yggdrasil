package org.ex.yggdrasil.model.world.time;

import java.util.PriorityQueue;

public class ScheduledEventExecutor {

	public static final void execute(PriorityQueue<ScheduledEvent> scheduledEvents) {
		long curTime = System.currentTimeMillis();
		ScheduledEvent event = scheduledEvents.peek();
		
		while (event != null && event.whenToExecute <= curTime) {
			scheduledEvents.poll().r.run();
			event = scheduledEvents.peek();
		}
	}
}
