package model.updates;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.charachters.Player;
import model.world.Tile;
import server.serialization.Serializer;

public class NetworkUpdate {

	private final Set<Tile> tiles;
	public Player localPlayer;
	
	private boolean shouldSend;
	
	public NetworkUpdate() {
		this.tiles = new TreeSet<>();
		shouldSend = false;
	}
	
	public void setLocalPlayer(Player p) {
		this.localPlayer = p;
		shouldSend = true;
	}

	public void addTiles(List<Tile> tiles2) {
		shouldSend = true;
		tiles.addAll(tiles2);
	}
	
	public void addTile(Tile t) {
		shouldSend = true;
		tiles.add(t);
	}
	
	public Set<Tile> getTiles() {
		return tiles;
	}
	
	public boolean shouldSend() {
		return shouldSend;
	}
	
	public void clear() {
		localPlayer = null;
		shouldSend = false;
		tiles.clear();
	}

	/**
	 * Serializes this object into a string and clears out the updates in this object.
	 * @return the serialization of this object.
	 */
	public String consume() {
		String result = Serializer.prepare(this);
		this.clear();
		return result;
	}

	
	
}
