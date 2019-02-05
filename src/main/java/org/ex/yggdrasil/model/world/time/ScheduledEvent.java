package org.ex.yggdrasil.model.world.time;

import java.io.Serializable;
import java.util.Objects;

public class ScheduledEvent implements Comparable<ScheduledEvent>, Serializable {

	private static final long serialVersionUID = 4465924708834610502L;
	
	public final long whenToExecute;
	public final Runnable r;
	
	public ScheduledEvent(long whenToExecute, Runnable r) {
		Objects.requireNonNull(r);
		this.whenToExecute = whenToExecute;
		this.r = r;
	}
	
	@Override
	public int compareTo(ScheduledEvent arg0) {
		return (int) (arg0.whenToExecute - this.whenToExecute);
	}

}
