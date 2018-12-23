package parser;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import model.Entity;
import model.charachters.Player;
import parser.Command.CommandException;

public class CommandData {

	public final Command command;
	
	public final List<Entity> entities;
	public final List<String> args;
	
	public final Player source;
	
	public CommandData(Command command, Player source) {
		Objects.requireNonNull(source);
		Objects.requireNonNull(command);
		
		this.entities = new LinkedList<>();
		this.args = new LinkedList<>();
		
		this.command = command;
		this.source = source;
	}

	/**
	 * Executes the run method on the command with the current data.
	 * Equivelent to this.command.run(this)
	 * @throws CommandException if the command has an exceptional task to do
	 */
	public void run() throws CommandException {
		this.command.run(this);
	}
	
}
