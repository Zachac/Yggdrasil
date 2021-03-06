package org.ex.yggdrasil.model.entities.players;

import java.io.Serializable;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.EntityMaterial;
import org.ex.yggdrasil.model.entities.players.inventory.Inventory;
import org.ex.yggdrasil.model.entities.players.specialization.SpecializationLevels;
import org.ex.yggdrasil.model.updates.NetworkUpdate;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.model.world.chunks.Chunk;
import org.ex.yggdrasil.model.world.time.ContinuousEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player extends Entity implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(Player.class);

	public static final String[] ACTIONS = new String[0];

	private static final long serialVersionUID = -862762766117190960L;

	public final Queue<String> messages;
	public final NetworkUpdate updates;
	public final Inventory inventory;
	public final SpecializationLevels specialization;
	public final String userName;
	
	private final Queue<ContinuousEvent> actionBacklog;
	
	private long experience;
	
	private transient boolean loggedIn;
	private transient ContinuousEvent action;

	public Player(String userName, Chunk location) {
		super(location);
		Objects.requireNonNull(userName);
		Objects.requireNonNull(location);

		this.setMaterial(EntityMaterial.HUMAN);

		this.userName = userName;
		this.updates = new NetworkUpdate();
		this.specialization = new SpecializationLevels();
		this.experience = 0;
		this.action = null;
		this.messages = new ConcurrentLinkedQueue<>();
		this.actionBacklog = new ConcurrentLinkedQueue<>();
		this.loggedIn = false;
		this.inventory = new Inventory(this);
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
	 * Add's an action to be called after the current action. If the current action
	 * is replaced by a new action, all current actions waiting on it will not be
	 * added.
	 * 
	 * @param e the action to add.
	 */
	public void addAction(ContinuousEvent e) {
		Objects.requireNonNull(e);
		
		if (this.action == null) {
			this.action = e;
			UpdateProcessor.update(this);
			World.get().time.addContinuousEvent(e);
		} else {
			this.actionBacklog.add(e);			
		}
	}

	public synchronized void login() throws AlreadyLoggedInException {
		if (loggedIn) {
			throw new AlreadyLoggedInException("Already logged in!");
		}

		loggedIn = true;

		LOG.info("{} logged in", userName);

		this.getChunk().add(this);
		UpdateProcessor.update(this);
		UpdateProcessor.completeUpdate(this);
	}

	public void logout() {
		loggedIn = false;
		this.getChunk().remove(this);
		UpdateProcessor.update(this);
		UpdateProcessor.completeUpdate(this);
		LOG.info("{} logged out", userName);
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

	@Override
	public void jumpto(Chunk c, int x, int y) {
		super.jumpto(c, x, y);
		UpdateProcessor.completeUpdate(this);
	}

	@Override
	public String[] getActions() {
		return ACTIONS;
	}
}
