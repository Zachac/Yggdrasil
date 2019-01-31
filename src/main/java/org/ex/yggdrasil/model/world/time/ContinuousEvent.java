package org.ex.yggdrasil.model.world.time;

import java.io.Serializable;

import org.ex.yggdrasil.model.entities.players.Player;

public interface ContinuousEvent extends Serializable { 
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