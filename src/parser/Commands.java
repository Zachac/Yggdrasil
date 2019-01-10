package parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import parser.commands.BuildCommand;
import parser.commands.EchoCommand;
import parser.commands.ExitCommand;
import parser.commands.FaceCommand;
import parser.commands.GoCommand;
import parser.commands.HelpCommand;
import parser.commands.InterruptCommand;
import parser.commands.LCommand;
import parser.commands.LookCommand;
import parser.commands.NoCommandCommand;
import parser.commands.SaveCommand;
import parser.commands.SayCommand;
import parser.commands.SysTPCommand;
import parser.commands.SysUpdateCommand;
import parser.commands.TimeCommand;
import parser.commands.UnkownCommandCommand;
import parser.commands.WhereAmICommand;

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
