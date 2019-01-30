package org.ex.yggdrasil.parser;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.ex.yggdrasil.model.entities.players.Player;
import org.ex.yggdrasil.parser.Command.CommandPattern.PatternNode;
import org.ex.yggdrasil.parser.commands.UnkownCommandCommand;

public class CommandParser {

	public static final Map<String,Command> commands = Commands.getCommands();
	
	public static CommandData parse(String[] args, Player p) {
		Objects.requireNonNull(args);
		
		Command c = getCommand(args);
		
		CommandData data = new CommandData(c, p);
		
		if (data.command instanceof UnkownCommandCommand) {
			data.args.add(args[0]);
		}
		
		try {
			loadData(data, args);
		} catch (ArgumentNotFoundException e) {
			c = commands.get("echo");
			data = new CommandData(c, p);
			data.args.add(e.getMessage());
		}
		
		return data;
	}
	
	public static void loadData(CommandData data, String[] args) throws ArgumentNotFoundException {
		Iterator<PatternNode> pattern = data.command.pattern.getNodes().iterator();
		PatternNode p = null;
		
		boolean consumeNode = true;
		int i;
		
		for (i = 1; i < args.length && !(consumeNode && !pattern.hasNext()); i++) {
			if (consumeNode) {
				p = pattern.next();
			}
			
			consumeNode = parseArg(data, args[i], p);
		}
		
		// if we ran out of patterns/args to parse in the loop
		if (data.command.isStrict && invalidArgsRemaining(args, pattern, i)) {
			throw new ArgumentNotFoundException("Invalid number of arguments to " + data.command.name);				
		}
	}

	/**
	 * A command that isStrict must not have arguments remaining to be parsed 
	 * or contain non-optional remaining patterns
	 * 
	 * @param args
	 * @param pattern
	 * @param i
	 * @return
	 */
	private static boolean invalidArgsRemaining(String[] args, Iterator<PatternNode> pattern, int i) {
		if (i < args.length) {
			return true;
		}
		
		while (pattern.hasNext()) {
			if ((pattern.next().flags & PatternNode.Flag.OPTIONAL.value) != 1) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean parseArg(CommandData data, String arg, PatternNode p) throws ArgumentNotFoundException {
		boolean consumeNode = true;
		
		if (arg.matches(p.value)) {
			if (p.containsFlag(PatternNode.Flag.IMPORTANT)) {
				data.args.add(arg);
			}
			
			if (p.containsFlag(PatternNode.Flag.REPEATABLE)) {
				consumeNode = false;
			}
		} else {
			if (!p.containsFlag(PatternNode.Flag.OPTIONAL)) {
				throw new ArgumentNotFoundException("Unkown argument: " + arg);
			}
		}
		
		return consumeNode;
	}

	public static Command getCommand(String[] args) {
		Command c = null;
		
		if (args.length <= 0) {
			c = commands.get("");
		} else {
			c = commands.get(args[0]);
			
			if (c == null) {
				c = commands.get("?");
			}
		}
		
		return c;
	}
	
	private static class ArgumentNotFoundException extends Exception {
		private static final long serialVersionUID = -4355049065649223658L;
		
		public ArgumentNotFoundException(String message) {
			super(message);
		}
	}
}
