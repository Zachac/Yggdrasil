package parser.commands;

import java.io.Serializable;
import java.util.Objects;

import model.Time;
import model.Time.ContinuousEvent;
import model.charachters.Player;
import model.world.Coordinate;
import model.world.Tile;
import model.world.World;
import parser.Command;
import parser.Command.CommandPattern.PatternNode;
import parser.CommandData;

// TODO expand updates
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
		
		ContinuousEvent event = new MoveEvent(d.source, directions);
		World.get().time.addContinuousEvent(event);
		

		d.source.messages.add("You start walking.");
	}
	
	private static class Direction implements Serializable {
		
		private static final long serialVersionUID = 4107742626198770702L;
		
		public final Coordinate.Direction d;
		public int steps;
		
		public Direction(int steps, char direction) {
			this.d = Coordinate.Direction.valueOf(direction);
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
			
			player.addAction(this);
		}
		
		@Override
		public boolean tick() {
			if (finished) {
				return finished;
			}
			
			int i = 0;
			
			while (i < directions.length && directions[i].steps <= 0) i++;
			
			if (i < directions.length) {
				
				directions[i].steps--;
				Tile nextTile = player.getLocation().links[directions[i].d.ordinal()];
				
				if (nextTile == null) {
					nextTile = player.getLocation();
				}
				
				player.move(nextTile);
			} else {
				finished = true;	
				player.messages.add("You finished walking at " + player.getLocation().position);
			}
			return finished;
		}

		@Override
		public void cancel() {
			this.finished = true;
		}
		
	}
}
