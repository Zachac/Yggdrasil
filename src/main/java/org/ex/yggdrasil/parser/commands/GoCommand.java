package org.ex.yggdrasil.parser.commands;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.ex.yggdrasil.model.Time;
import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.updates.UpdateProcessor;
import org.ex.yggdrasil.model.world.Direction3D;
import org.ex.yggdrasil.model.world.Tile;
import org.ex.yggdrasil.model.world.TileTraverser;
import org.ex.yggdrasil.model.world.TileTraverser.SearchField;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;

public class GoCommand extends Command {
	
	public GoCommand() {
		this("go");
	}
	
	protected GoCommand(String name) {
		super(name, getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add("[0-9]{0,9}[nsewNSEW]{1,2}", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		DirectionData[] directions = new DirectionData[d.args.size()];
		int i = 0;
		
		for (String s : d.args) {
			Direction3D d1 = Direction3D.valueOf(s.charAt(s.length() - 1));
			Direction3D d2 = null;
			
			if (s.length() >= 2) {
				d2 = Direction3D.valueOf(s.charAt(s.length() - 2));
			}

			Direction3D direction;
			int stepsLength;
			
			if (d2 != null) {
				direction = d1.combine(d2);
				stepsLength = s.length() - 2;
			} else {
				direction = d1;
				stepsLength = s.length() - 1;
			}

			int steps = 1;
			if (stepsLength > 0) {
				steps = Integer.parseInt(s.substring(0, stepsLength));
			}
			
			directions[i++] = new DirectionData(steps, direction);
		}
		
		d.source.setAction(new MoveEvent(d.source, directions));
		d.source.messages.add("You start walking.");
	}
	
	private static class DirectionData implements Serializable {
		
		private static final long serialVersionUID = 4107742626198770702L;
		
		public final Direction3D d;
		public int steps;
		
		public DirectionData(int steps, Direction3D d) {
			this.d = d;
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
		public final DirectionData[] directions;
		
		private boolean finished;
		
		public MoveEvent(Player p, DirectionData... directions) {
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

		protected void sendUpdates(Direction3D d, Tile oldTile, Tile nextTile) {
			if (nextTile != null) {

				if (d != Direction3D.D && d != Direction3D.U) {
					// then send only the new blocks in range

					
					List<Tile> tiles = new LinkedList<>();
					
					if (d.isComposite) {
						for (Direction3D di : d.directions) {
							SearchField s = getNewlyExposedSearchField(di, nextTile);
							TileTraverser.traverseAll((t) -> {tiles.add(t);}, s);
						}
					} else {
						SearchField s = getNewlyExposedSearchField(d, nextTile);
						TileTraverser.traverseAll((t) -> {tiles.add(t);}, s);						
					}
					
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

		/**
		 * Get's the search area for a non-composite direction.
		 * @param d
		 * @param nextTile
		 * @return
		 * @throws IllegalArgumentException if d is NE,NW,SE,SW
		 */
		public static SearchField getNewlyExposedSearchField(Direction3D d, Tile nextTile) {
			if (d.isComposite) {
				throw new IllegalArgumentException();
			}
			
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
