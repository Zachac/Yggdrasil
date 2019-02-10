package org.ex.yggdrasil.model.entities.players.specialization.specializations.commands;

import java.util.Collection;
import java.util.EnumSet;

import org.ex.yggdrasil.model.entities.Entity;
import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.entities.players.specialization.Specialization;
import org.ex.yggdrasil.model.entities.players.specialization.specializations.actions.ResourceRetrievalAction;
import org.ex.yggdrasil.model.entities.resources.ResourceNode;
import org.ex.yggdrasil.model.entities.resources.ResourceNodeType;
import org.ex.yggdrasil.model.world.chunks.Coordinate2D;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.CommandData;

public abstract class AbstractResourceRetrievalCommand extends Command {

	private static final String COULD_NOT_FIND_ANYTHING_TO_GATHER_FROM = "Could not find anything";
	private EnumSet<ResourceNodeType> validTypes;
	private final String requiredLevelPrompt;
	private final String requiredClass;
	private final int requiredLevel;
	private final String prettyName;
	private final String idleText;

	public AbstractResourceRetrievalCommand(String name, String prettyName, String idleText, String requiredClass, int requiredLevel, ResourceNodeType firstType, ResourceNodeType... types) {
		super(name);
		this.prettyName = prettyName;
		this.requiredClass = requiredClass;
		this.requiredLevel = requiredLevel;
		this.requiredLevelPrompt = requiredClass + " level " + requiredLevel + " required";
		this.validTypes = EnumSet.of(firstType, types);
		this.idleText = idleText;
	}

	@Override
	public void run(CommandData d) throws CommandException {
		Specialization s = d.source.specialization.getSpecialization(requiredClass);

		if (s == null || s.getCurrentLevel() < requiredLevel) {
			d.source.messages.add(requiredLevelPrompt);
			return;
		}

		ResourceNode result = getResourceNode(d.source);

		if (result == null) {
			d.source.messages.add(COULD_NOT_FIND_ANYTHING_TO_GATHER_FROM);
		} else {
			startRetrieval(d.source, s, result);
		}
	}

	private ResourceNode getResourceNode(Player p) {
		Coordinate2D position = p.getPosition().get(p.getFacing());
		Collection<Entity> lookingAt = p.getChunk().getEntities(position);

		for (Entity e : lookingAt) {
			if (e instanceof ResourceNode && validTypes.contains(((ResourceNode) e).getType())) {
				return (ResourceNode) e;
			}
		}
		
		return null;
	}

	private void startRetrieval(Player p, Specialization s, ResourceNode node) {
		p.addAction(new ResourceRetrievalAction(p, s, node, idleText, prettyName));
	}
}