package model.charachters;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import model.world.Entity;
import model.world.Tile;
import model.world.Time.ContinuousEvent;

public class Player extends Entity implements Serializable {

	private static final long serialVersionUID = -862762766117190960L;
	
	public final ClassLevels specialization;
	public final String userName;
	private long experience;
	transient boolean loggedIn;
	private Tile location;

	private final List<ContinuousEvent> actions;
	public final Queue<String> messages;
	
	public Player(String userName, Tile location) {
		Objects.requireNonNull(userName);
		Objects.requireNonNull(location);
		
		this.location = location;
		this.location.contents.add(this);
		this.userName = userName;
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
	}

	public void logout() {
		loggedIn = false;
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
}
