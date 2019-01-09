package model.charachters;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import model.Entity;
import model.Time.ContinuousEvent;
import model.updates.NetworkUpdate;
import model.updates.UpdateProcessor;
import model.world.Coordinate;
import model.world.Tile;
import model.world.Coordinate.Direction;

public class Player extends Entity implements Serializable {

	private static final long serialVersionUID = -862762766117190960L;
	
	public final ClassLevels specialization;
	public final String userName;
	private long experience;
	transient boolean loggedIn;
	private Tile location;
	private Direction facing;

	private final List<ContinuousEvent> actions;
	public final Queue<String> messages;
	
	public final NetworkUpdate updates;
	
	public Player(String userName, Tile location) {
		Objects.requireNonNull(userName);
		Objects.requireNonNull(location);
		
		this.location = location;
		this.userName = userName;
		this.updates = new NetworkUpdate();
		this.facing = Direction.N;
		specialization = new ClassLevels();
		experience=0;
		actions = new LinkedList<>();
		messages = new LinkedList<>();
		loggedIn = false;
	}
	
	public long getExperience() {
		return experience;
	}
	
	public void addAction(ContinuousEvent e) {
		Objects.requireNonNull(e);
		
		Iterator<ContinuousEvent> iter = actions.iterator();
		
		while (iter.hasNext()) {
			ContinuousEvent e2 = iter.next();
			
			if (e.getClass().isAssignableFrom(e2.getClass())) {
				e2.cancel();
				iter.remove();
			}
		}
		
		actions.add(e);	
	}
	
	public Tile getLocation() {
		return location;
	}

	public void move(Tile nextTile) {
		Objects.requireNonNull(nextTile);
		this.location.contents.remove(this);
		nextTile.contents.add(this);
		this.location = nextTile;
	}

	public synchronized void login() throws AlreadyLoggedInException {
		if (loggedIn) {
			throw new AlreadyLoggedInException("Already logged in!");
		}
		
		loggedIn = true;
		System.out.println("INFO: " + userName + " logged in.");
		
		this.location.contents.add(this);
		UpdateProcessor.update(this);		
	}

	public void logout() {
		UpdateProcessor.update(this);
		this.location.contents.remove(this);
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

	public List<ContinuousEvent> getActions() {
		return Collections.unmodifiableList(actions);
	}

	@Override
	public Coordinate getPosition() {
		if (!loggedIn) {
			return null;
		}
		
		return location.position;
	}

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		Objects.requireNonNull(facing);
		this.facing = facing;
	}
}
