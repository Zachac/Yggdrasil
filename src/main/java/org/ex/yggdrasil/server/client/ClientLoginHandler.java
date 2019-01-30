package org.ex.yggdrasil.server.client;

import java.io.PrintStream;

import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.model.entities.players.Player.AlreadyLoggedInException;
import org.ex.yggdrasil.model.world.World;
import org.ex.yggdrasil.parser.CommandParser;
import org.ex.yggdrasil.server.client.input.ClientCommandIterator;

public class ClientLoginHandler {
	
	
	/**
	 * Login a player through the given input/output streams
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception if the player was unable to log in.
	 */
	public static Player login(ClientCommandIterator in, PrintStream out) throws Exception {
		Player result;
		
		do {
			result = attemptLogin(in, out);
		} while (result == null);
		
		CommandParser.parse(new String[] {"sysupdate"}, result).run();;
		
		return result;
	}

	private static Player attemptLogin(ClientCommandIterator in, PrintStream out) {
		String name = promptPlayerName(in, out);
		Player result = World.getPlayer(name);
		
		while (result == null) {
			out.println("Player not found,");
			boolean createCharacter = getYesNoResponse("Would you like to create a new charachter", in, out);
			
			if (createCharacter) {
				result = new Player(name, World.get().getRoot());
				World.addPlayer(result);
			} else {
				name = promptPlayerName(in, out);
				result = World.getPlayer(name);
			}
		}
		
		try {
			result.login();
		} catch (AlreadyLoggedInException e) {
			result = null;
			out.println("You are already logged in!");
		}
		return result;
	}

	private static boolean getYesNoResponse(String prompt, ClientCommandIterator in, PrintStream out) {
		Boolean result = null;
		prompt = prompt + "(Y/N): ";		
		
		out.println(prompt);
		out.flush();
		
		String response = in.next();
		
		if (response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES")) {
			result = true;
		} else if (response.equalsIgnoreCase("N") || response.equalsIgnoreCase("NO")) {
			result = false;
		} else {
			out.println("Please enter Yes or No,");
			
		}
		
		while (result == null) {
			out.println(prompt);
			out.flush();
			
			response = in.next();
			
			if (response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES")) {
				result = true;
			} else if (response.equalsIgnoreCase("N") || response.equalsIgnoreCase("NO")) {
				result = false;
			} else {
				out.println("Please enter Yes or No,");
			}
		}
		
		return result;
	}

	private static String promptPlayerName(ClientCommandIterator in, PrintStream out) {
		out.println("Please enter a username to login: ");
		out.flush();

		String name = in.next();
		
		while (name==null || !org.ex.yggdrasil.util.Strings.isValidPlayerName(name)) {
			out.println("Invalid characters detected,");
			out.println("Please enter a username to login: ");
			out.flush();
			name = in.next();
		}
		
		return name;
	}
}
