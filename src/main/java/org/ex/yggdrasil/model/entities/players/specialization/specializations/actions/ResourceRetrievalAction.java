package org.ex.yggdrasil.model.entities.players.specialization.specializations.actions;

import org.ex.yggdrasil.model.entities.items.Item;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.entities.players.specialization.Specialization;
import org.ex.yggdrasil.model.entities.players.specialization.specializations.AbstractSpecialization;
import org.ex.yggdrasil.model.entities.resources.ResourceNode;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.model.world.time.AbstractContinousEvent;

public class ResourceRetrievalAction extends AbstractContinousEvent {

	private static final long serialVersionUID = 2018454681389702463L;

	private static final String SINGULAR_RECIEVE = "You get a ";
	private static final String PLURAL_RECIEVE = "You get some ";
	private static final String NOTHING_LEFT = "There is nothing left";
				
	private final String idle_text;
	private final Specialization s;
	private final ResourceNode node;
	private final boolean start;
	private final String prettyName;

	public ResourceRetrievalAction(Player p, Specialization s, ResourceNode node, String idle_text, String prettyName) {
		super(p);
		this.s = s;
		this.node = node;
		this.start = (World.get().time.getTickTime() & 1) == 0;
		this.idle_text = idle_text;
		this.prettyName = prettyName;
	}

	/**
	 * 
	 * @param p
	 * @param s
	 * @param node
	 * @return whether or not the node is now depleted.
	 */
	private boolean gather(Player p, Specialization s, ResourceNode node) {
		if (node.isDepleted()) {
			p.messages.add(NOTHING_LEFT);
			return true;
		}

		Item item = node.consume(s.getCurrentLevel() / 5.0f);

		if (item != null) {
			p.messages.add((item.plural ? PLURAL_RECIEVE : SINGULAR_RECIEVE) + item);
			((AbstractSpecialization) s).addXp(1);
		} else {
			p.messages.add(idle_text);
		}

		return node.isDepleted();
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
		return prettyName;
	}
}