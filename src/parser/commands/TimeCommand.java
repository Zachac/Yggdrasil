package parser.commands;

import model.world.World;
import parser.Command;
import parser.CommandData;

public class TimeCommand extends Command {

	public TimeCommand() {
		super("time", getMyPattern(), false);
	}
	
	private static CommandPattern getMyPattern() {
		return new CommandPattern();
	}
	
	@Override
	public void run(CommandData d) throws ExitException {
		StringBuilder result = new StringBuilder();
		
		long time = World.get().time.getTickTime();
		time /= 2;
		int seconds = (int) (time % 60); time = (time - seconds)/60;
		int minutes = (int) (time % 60); time = (time - minutes)/60;
		int hours = (int) (time % 24); time = (time - hours)/24;
		int days = (int) (time % 365); time = (time - days)/365;
		int years = (int) time;

		result.append("It is year ");
		result.append(years);
		
		result.append(" day ");
		result.append(days);
		
		result.append(" at ");

		if (hours < 10) result.append(0);
		result.append(hours);
		
		result.append(':');
		
		if (minutes < 10) result.append(0);
		result.append(minutes);
		
		result.append(':');
		
		if (seconds < 10) result.append(0);
		result.append(seconds);
		
		
		d.source.messages.add(result.toString());
	}
}
