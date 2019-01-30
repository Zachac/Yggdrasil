package org.ex.yggdrasil.parser.commands;

import java.util.List;

import org.ex.yggdrasil.model.charachters.Player;
import org.ex.yggdrasil.model.world.Chunk;
import org.ex.yggdrasil.parser.Command;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.CommandData;
import org.glassfish.grizzly.http.server.util.Enumerator;

public class SayCommand extends Command {

	public static final int SAY_RANGE = LookCommand.DEFAULT_LOOK;

	public SayCommand() {
		this("say");
	}
	
	protected SayCommand(String name) {
		super(name, getMyPattern(), true);
	}
	
	private static CommandPattern getMyPattern() {
		CommandPattern pattern = new CommandPattern(); 

		pattern.add(".+", (PatternNode.Flag.IMPORTANT.value | PatternNode.Flag.REPEATABLE.value));
		
		return pattern;
	}
	
	@Override
	public void run(CommandData d) {
		String message = getMessage(d.source, d.args);
		sendMessage(d.source.getChunk(), message);
	}

	public static void sendMessage(Chunk chunk, String message) {
		Enumerator<Player> players = chunk.getPlayers();
		
		while (players.hasMoreElements()) {
			players.nextElement().messages.add(message);
		}
	}

	public static String getMessage(Player p, List<String> messages) {
		StringBuilder result = new StringBuilder();
		
		result.append(p);
		result.append(':');
		
		for (String s : messages) {
			result.append(' ');
			result.append(s);
		}
		
		return result.toString();
	}
}
