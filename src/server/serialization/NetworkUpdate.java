package server.serialization;

import java.util.LinkedList;
import java.util.List;

import model.charachters.Player;
import model.world.Tile;

public class NetworkUpdate {

	public final List<Tile> tiles;
	public Player localPlayer;
	
	public NetworkUpdate() {
		this.tiles = new LinkedList<>();
	}
}
