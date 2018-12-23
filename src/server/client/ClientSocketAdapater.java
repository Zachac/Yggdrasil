package server.client;

import java.io.PrintStream;

import server.client.input.ClientCommandIterator;

public interface ClientSocketAdapater {

	public ClientCommandIterator getInput();
	public PrintStream getOuput();
	
}
