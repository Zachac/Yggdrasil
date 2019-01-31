package org.ex.yggdrasil.model.world.time;

import java.util.Iterator;
import java.util.LinkedList;

import org.ex.yggdrasil.model.entities.players.Player;

public class ContinuousEventExecutor {

	public static void execute(LinkedList<ContinuousEvent> l) {
	
		Iterator<ContinuousEvent> iter = l.iterator();
		
		while (iter.hasNext()) {
			boolean hasFinished = safelyExecute(iter.next());
			if (hasFinished) {
				iter.remove();
			}
		}
	}

	public static boolean safelyExecute(ContinuousEvent event) {
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