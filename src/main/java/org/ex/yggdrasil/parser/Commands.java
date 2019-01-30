package org.ex.yggdrasil.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ex.yggdrasil.parser.commands.BuildCommand;
import org.ex.yggdrasil.parser.commands.EchoCommand;
import org.ex.yggdrasil.parser.commands.ExitCommand;
import org.ex.yggdrasil.parser.commands.FaceCommand;
import org.ex.yggdrasil.parser.commands.GoCommand;
import org.ex.yggdrasil.parser.commands.HelpCommand;
import org.ex.yggdrasil.parser.commands.InterruptCommand;
import org.ex.yggdrasil.parser.commands.LCommand;
import org.ex.yggdrasil.parser.commands.LookCommand;
import org.ex.yggdrasil.parser.commands.NoCommandCommand;
import org.ex.yggdrasil.parser.commands.SaveCommand;
import org.ex.yggdrasil.parser.commands.SayCommand;
import org.ex.yggdrasil.parser.commands.SysTPCommand;
import org.ex.yggdrasil.parser.commands.SysUpdateCommand;
import org.ex.yggdrasil.parser.commands.TimeCommand;
import org.ex.yggdrasil.parser.commands.UnkownCommandCommand;
import org.ex.yggdrasil.parser.commands.WhereAmICommand;

public class Commands {

	private static Map<String,Command> commands = generateDefaultCommands();
	
	/**
	 * Get's the map of string command names to the commands themselves.
	 * @return an unmodifiable map of string to commands.
	 */
	public static Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}
	
	private static Map<String, Command> generateDefaultCommands() {
		commands = new HashMap<String,Command>();
		
		addCommand(new EchoCommand());
		addCommand(new ExitCommand());
		addCommand(new UnkownCommandCommand());
		addCommand(new HelpCommand());
		addCommand(new NoCommandCommand());
		addCommand(new SaveCommand());
//		addCommand(new LoadCommand());
		addCommand(new LookCommand());
		addCommand(new LCommand());
		addCommand(new GoCommand());
		addCommand(new BuildCommand());
		addCommand(new SayCommand());
		addCommand(new TimeCommand());
		addCommand(new WhereAmICommand());
		addCommand(new SysUpdateCommand());
		addCommand(new SysTPCommand());
		addCommand(new FaceCommand());
		addCommand(new InterruptCommand());
		
		return commands;
	}
	
	public static void addCommand(Command c) {
		commands.put(c.name, c);
	}
	
}
