package org.ex.yggdrasil.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Strings {

	public static String stringify(Throwable e) {
		StringBuilder result = new StringBuilder();
		
		result.append(getMessage(e));
		e = e.getCause();
		
		while (e != null) {
			if (e.getMessage() != null) {
				result.append(": ");
				result.append(e.getMessage());				
			}
			
			e = e.getCause();
		}
		
		return result.toString();
	}

	/**
	 * @param e the throwable to parse
	 * @return the message of the throwable or a string containing the stack trace. 
	 */
	public static String getMessage(Throwable e) {
		String message = e.getMessage();
		
		if (message == null) {
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			message = s.toString();
		}
		
		return message;
	}
	
	public static boolean isValidPlayerName(String s) {
		return s.matches("[a-zA-Z0-9_.]+");
	}
}
