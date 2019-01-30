package org.ex.yggdrasil.model.charachters;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import org.ex.yggdrasil.model.Entity;
import org.ex.yggdrasil.model.Time.ContinuousEvent;
import org.ex.yggdrasil.model.updates.NetworkUpdate;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.Chunk;
import org.ex.yggdrasil.model.world.Direction3D;
import org.ex.yggdrasil.model.world.World;

public class Player extends Entity implements Serializable {

	private static final long serialVersionUID = -862762766117190960L;

	public final ClassLevels specialization;
	public final String userName;
	private long experience;
	transient boolean loggedIn;
	private Direction3D facing;
	private transient ContinuousEvent action;

	public final Queue<String> messages;
	private final Queue<ContinuousEvent> actionBacklog;

	public final NetworkUpdate updates;

	public Player(String userName, Chunk location) {
		super(location);
		Objects.requireNonNull(userName);
		Objects.requireNonNull(location);

		this.userName = userName;
		this.updates = new NetworkUpdate();
		this.facing = Direction3D.N;
		specialization = new ClassLevels();
		experience = 0;
		action = null;
		messages = new LinkedList<>();
		actionBacklog = new LinkedList<>();
		loggedIn = false;
	}

	public long getExperience() {
		return experience;
	}

	public void setAction(ContinuousEvent e) {
		
		if (e == null) {
			e = actionBacklog.poll();
		} else {
			actionBacklog.clear();
		}
		
		if (action != null) {
			action.cancel();
		}

		if (e != null) {
			World.get().time.addContinuousEvent(e);
		}

		this.action = e;
		UpdateProcessor.update(this);
	}
	
	/**
	 * Add's an action to be called after the current action. If
	 * the current action is replaced by a new action, all current
	 * actions waiting on it will not be added.
	 * 
	 * @param e the action to add.
	 */
	public void addAction(ContinuousEvent e) {
		Objects.requireNonNull(e);
		this.actionBacklog.add(e);
	}

	public synchronized void login() throws AlreadyLoggedInException {
		if (loggedIn) {
			throw new AlreadyLoggedInException("Already logged in!");
		}

		loggedIn = true;
		System.out.println("INFO: " + userName + " logged in.");

		this.getChunk().add(this);
		UpdateProcessor.update(this);
	}

	public void logout() {
		UpdateProcessor.update(this);
		this.getChunk().remove(this);
		loggedIn = false;
		System.out.println("INFO " + userName + " logged out.");
	}

	@Override
	public String toString() {
		return userName;
	}

	public class AlreadyLoggedInException extends Exception {
		private static final long serialVersionUID = -9170675722530860184L;

		public AlreadyLoggedInException(String message) {
			super(message);
		}
	}

	public ContinuousEvent getAction() {
		return this.action;
	}

	public Direction3D getFacing() {
		return facing;
	}

	public void setFacing(Direction3D facing) {
		Objects.requireNonNull(facing);
		this.facing = facing;
	}
}
