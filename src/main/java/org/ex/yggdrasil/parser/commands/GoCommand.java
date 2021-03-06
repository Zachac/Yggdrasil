package org.ex.yggdrasil.parser.commands;

import java.io.Serializable;
import java.util.Objects;

import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.world.chunks.Direction2D;
import org.ex.yggdrasil.model.world.time.ContinuousEvent;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;

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
			Direction2D d1 = Direction2D.valueOf(s.charAt(s.length() - 1));
			Direction2D d2 = null;
			
			if (s.length() >= 2) {
				d2 = Direction2D.valueOf(s.charAt(s.length() - 2));
			}

			Direction2D direction;
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
		
		public final Direction2D d;
		public int steps;
		
		public DirectionData(int steps, Direction2D d) {
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
	
	public static class MoveEvent implements ContinuousEvent {

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
		public boolean tick(long tick) {
			if (finished) {
				return finished;
			}
			
			int i = 0;
			
			while (i < directions.length && directions[i].steps <= 0) i++;
			
			if (i < directions.length) {
				directions[i].steps--;
				move(directions[i].d);
			} else {
				finished = true;
				player.setAction(null);
				player.messages.add("You finished walking at " + player.position);
			}
			
			return finished;
		}

		protected void move(Direction2D d) {
			
			int x = player.position.getX() + d.direction.getX();
			int y = player.position.getY() + d.direction.getY();

			if (player.canMove(x, y)) {
				player.move(x, y);
			}
			
			player.setFacing(d);
		}

		@Override
		public void cancel() {
			this.finished = true;
		}

		@Override
		public String getPrettyName() {
			return "Walking";
		}

		@Override
		public Player getPlayer() {
			return this.player;
		}
	}
}
