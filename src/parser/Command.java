package parser;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Command implements Comparable<Command> {

	public final String name;
	public final CommandPattern pattern;
	public final boolean isStrict;
	
	public Command(String name, CommandPattern pattern, boolean isStrict) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(pattern);
		
		this.name = name;
		this.pattern = pattern;
		this.isStrict = isStrict;
	}
	
	public String getName() {
		return name;
	}
	
	public CommandPattern getPattern() {
		return null;
	}
	
	public boolean isStrict() {
		return isStrict;
	}
	
	/**
	 * The default implementation of the run method. It prints out it's arguments and ends.
	 * Hint: extending classes probably need to override this.
	 * @param commandData the data used to run
	 * @throws CommandException if the command has an exceptional task to do
	 */
	public void run(CommandData d) throws CommandException {
		StringBuilder result = new StringBuilder();
		
		boolean first = true;
		
		for (String s : d.args) {
			if (!first) {
				result.append(' ');
			} else {
				first = false;
			}
			
			result.append(s);
		}
		
		d.source.messages.add(result.toString());
	}
	
	@Override
	public int compareTo(Command other) {
		return this.name.compareTo(other.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public static class CommandException extends Exception {
		private static final long serialVersionUID = -8813858169768412769L; 
	}
	
	public static class ExitException extends CommandException {
		private static final long serialVersionUID = -6628844323815067762L; 
	}
	
	public static class CommandPattern {

		private List<PatternNode> nodes;
		
		public CommandPattern(PatternNode... nodes) {
			this.nodes = Arrays.asList(nodes);
		}
		
		public CommandPattern() {
			this.nodes = new LinkedList<>();
		}

		public CommandPattern add(String value) {
			this.nodes.add(new PatternNode(value));
			return this;
		}

		public CommandPattern add(String value, int flags) {
			this.nodes.add(new PatternNode(value, flags));
			return this;
		}
		
		public void finalizeData() {
			this.nodes = Collections.unmodifiableList(this.nodes);
		}
		
		public List<PatternNode> getNodes() {
			return nodes;
		}
		
		public static class PatternNode {
			
			public final String value;
			public final int flags;

			public PatternNode(String value, int flags) {
				this.value = value;
				this.flags = flags;
			}
			
			public PatternNode(String value) {
				this(value, 0);
			}
			
			public boolean containsFlag(Flag f) {
				return (this.flags & f.value) != 0;
			}

			public static enum Flag {
				OPTIONAL(0x01), REPEATABLE(0x02), IMPORTANT(0x04);
				
				public final int value;
				
				private Flag(int value) {
					this.value = value;
				}
				
				public int getValue() {
					return value;
				}
			}
		}
	}
}
