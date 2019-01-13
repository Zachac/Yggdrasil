package org.ex.yggdrasil.server.client;

import java.io.PrintStream;

import org.ex.yggdrasil.server.client.input.ClientCommandIterator;

public interface ClientSocketAdapater {

	public ClientCommandIterator getInput();
	public PrintStream getOuput();
	
}
