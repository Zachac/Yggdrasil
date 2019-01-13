package parser.commands;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import model.Time;
import model.charachters.Player;
import model.updates.UpdateProcessor;
import model.world.Coordinate3D;
import model.world.Tile;
import model.world.TileTraverser;
import model.world.TileTraverser.SearchField;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

public class GoCommand extends Command {
	
	public GoCommand() {
		this("go");
	}
	
	protected GoCommand(String name) {
		super(name, getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add("[0-9]+[nsewudNSEWUD]", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		Direction[] directions = new Direction[d.args.size()];
		int i = 0;
		
		for (String s : d.args) {
			int steps = Integer.parseInt(s.substring(0, s.length() - 1));
			char direction = s.charAt(s.length() - 1);
			directions[i++] = new Direction(steps, direction);
		}
		
		d.source.setAction(new MoveEvent(d.source, directions));
		d.source.messages.add("You start walking.");
	}
	
	private static class Direction implements Serializable {
		
		private static final long serialVersionUID = 4107742626198770702L;
		
		public final Coordinate3D.Direction d;
		public int steps;
		
		public Direction(int steps, char direction) {
			this.d = Coordinate3D.Direction.valueOf(direction);
			this.steps = steps;
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			
			result.append(steps);
			result.append(d);
			
			return result.toString();
		}
	}
	
	public static class MoveEvent implements Time.ContinuousEvent {

		private static final long serialVersionUID = 8233289328075381532L;
		
		public final Player player;
		public final Direction[] directions;
		
		private boolean finished;
		
		public MoveEvent(Player p, Direction... directions) {
			Objects.requireNonNull(directions);
			Objects.requireNonNull(p);
			
			this.player = p;
			this.directions = directions;
			this.finished = false;
		}
		
		@Override
		public boolean tick() {
			if (finished) {
				return finished;
			}
			
			int i = 0;
			
			while (i < directions.length && directions[i].steps <= 0) i++;
			
			if (i < directions.length) {
				move(i);
			} else {
				finished = true;
				player.setAction(null);
				player.messages.add("You finished walking at " + player.getLocation().position);
			}
			
			return finished;
		}

		protected void move(int i) {
			directions[i].steps--;
			Tile nextTile = player.getLocation().getTile(directions[i].d);
			Tile oldTile = player.getLocation();
			
			if (nextTile != null) {
				player.move(nextTile);
				sendUpdates(directions[i].d, oldTile, nextTile);				
			}
		}

		protected void sendUpdates(Coordinate3D.Direction d, Tile oldTile, Tile nextTile) {
			if (nextTile != null) {

				if (d != Coordinate3D.Direction.D && d != Coordinate3D.Direction.U) {
					// then send only the new blocks in range

					SearchField s = getNewlyExposedSearchField(d, nextTile);
					List<Tile> tiles = new LinkedList<>();
					TileTraverser.traverseAll((t) -> {tiles.add(t);}, s);
					
					player.setFacing(d);
					UpdateProcessor.send(oldTile.position, player, 1);
					UpdateProcessor.sendTiles(player, tiles);
				} else {
					UpdateProcessor.send(oldTile.position, player);
					UpdateProcessor.send(nextTile.position, player);
					UpdateProcessor.completeUpdate(player);
				}
			}
		}

		public static SearchField getNewlyExposedSearchField(Coordinate3D.Direction d, Tile nextTile) {
			int minx, maxx, xdiff = LookCommand.DEFAULT_LOOK * d.direction.getY();
			
			if (xdiff == 0) {
				minx = nextTile.position.getX() + LookCommand.DEFAULT_LOOK * d.direction.getX();
				maxx = minx;
			} else {
				minx = nextTile.position.getX() - xdiff; 
				maxx = nextTile.position.getX() + xdiff;
			}
			
			int miny, maxy, ydiff = LookCommand.DEFAULT_LOOK * d.direction.getX();
			if (ydiff == 0) {
				miny = nextTile.position.getY() + LookCommand.DEFAULT_LOOK * d.direction.getY();
				maxy = miny;
			} else {
				miny = nextTile.position.getY() - ydiff; 
				maxy = nextTile.position.getY() + ydiff;
			}

			if (ydiff < 0) {
				int swap = miny;
				miny = maxy;
				maxy = swap;
			}
			
			if (xdiff < 0) {
				int swap = minx;
				minx = maxx;
				maxx = swap;
			}
			
			SearchField s = new SearchField(minx, miny, maxx, maxy, nextTile.position.getZ());
			return s;
		}

		@Override
		public void cancel() {
			this.finished = true;
		}

		@Override
		public void setPlayer(Player p) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getPrettyName() {
			return "Walking";
		}
	}
}
