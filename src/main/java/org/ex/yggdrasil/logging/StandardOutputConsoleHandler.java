package org.ex.yggdrasil.logging;

import java.util.logging.ConsoleHandler;

public class StandardOutputConsoleHandler extends ConsoleHandler {
	public StandardOutputConsoleHandler() {
		setOutputStream(System.out);
	}
}