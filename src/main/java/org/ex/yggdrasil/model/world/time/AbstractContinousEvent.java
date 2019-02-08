package org.ex.yggdrasil.model.world.time;

import org.ex.yggdrasil.model.entities.players.Player;

public abstract class AbstractContinousEvent implements ContinuousEvent {

	private static final long serialVersionUID = 8958512888962050758L;
	
	protected final Player p;
	protected boolean canceled;

	public AbstractContinousEvent(Player p) {
		this.p = p;
		this.canceled = false;
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}

	@Override
	public Player getPlayer() {
		return p;
	}

}
