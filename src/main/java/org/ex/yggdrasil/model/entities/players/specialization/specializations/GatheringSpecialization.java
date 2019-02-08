package org.ex.yggdrasil.model.entities.players.specialization.specializations;

import java.util.Collection;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.entities.players.specialization.Specialization;
import org.ex.yggdrasil.model.entities.resources.ResourceNode;
import org.ex.yggdrasil.model.entities.resources.ResourceNodeType;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.model.world.chunks.Coordinate2D;
import org.ex.yggdrasil.model.world.time.AbstractContinousEvent;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

public class GatheringSpecialization extends AbstractSpecialization {

	private static final long serialVersionUID = 5333950295135146330L;

	private static final String NAME = "gathering";
	private static final int[] XP_REQUIREMENTS = { 0, 10, 100, 1_000, 10_000 };

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected int[] getXpRequirements() {
		return XP_REQUIREMENTS;
	}

	public static class ForageCommand extends Command {

		private static final String THERE_IS_NOTHING_LEFT = "There is nothing left.";
		private static final String COULD_NOT_FIND_ANYTHING_TO_GATHER_FROM = "Could not find anything to gather from.";
		private static final String REQUIRED_LEVEL = "You need to specialize in gathering to learn how to do that\n\tlevel required, "
				+ NAME + " 1";

		public ForageCommand() {
			super("forage");
		}

		@Override
		public void run(CommandData d) throws CommandException {
			Specialization s = d.source.specialization.getSpecialization(NAME);

			if (s == null) {
				d.source.messages.add(REQUIRED_LEVEL);
				return;
			}

			Coordinate2D position = d.source.getPosition().get(d.source.getFacing());
			Collection<Entity> lookingAt = d.source.getChunk().getEntities(position);

			ResourceNode result = null;

			for (Entity e : lookingAt) {
				if (e instanceof ResourceNode && ((ResourceNode) e).getType() == ResourceNodeType.BUSH) {
					result = (ResourceNode) e;
					break;
				}
			}

			if (result == null) {
				d.source.messages.add(COULD_NOT_FIND_ANYTHING_TO_GATHER_FROM);
			} else {
				startGathering(d.source, s, result);
			}
		}

		private static void startGathering(Player p, Specialization s, ResourceNode node) {
			p.setAction(new GatheringAction(p, s, node));
		}

		/**
		 * 
		 * @param p
		 * @param s
		 * @param node
		 * @return whether or not the node is now depleted.
		 */
		private static boolean gather(Player p, Specialization s, ResourceNode node) {
			if (node.isDepleted()) {
				p.messages.add(THERE_IS_NOTHING_LEFT);
				return true;
			}

			Item item = node.consume(s.getCurrentLevel() / 5.0f);

			if (item != null) {
				p.messages.add("You find a " + item);
				((AbstractSpecialization) s).addXp(1);
			} else {
				p.messages.add("You forage through the bush");
			}

			return node.isDepleted();
		}

		private static class GatheringAction extends AbstractContinousEvent {

			private static final long serialVersionUID = 2018454681389702463L;
			
			private Specialization s;
			private ResourceNode node;
			private boolean start;

			public GatheringAction(Player p, Specialization s, ResourceNode node) {
				super(p);
				this.s = s;
				this.node = node;
				this.start = (World.get().time.getTickTime() & 1) == 0;
			}

			@Override
			public boolean tick(long tick) {
				if (canceled) {
					return true;
				}
				
				if (((tick & 1) == 0) != start) {
					return gather(p, s, node);
				} else {
					return false;
				}
			}

			@Override
			public String getPrettyName() {
				return "Gathering";
			}
		}
	}
}
