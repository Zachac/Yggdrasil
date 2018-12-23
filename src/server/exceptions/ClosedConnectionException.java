package server.exceptions;

public class ClosedConnectionException extends RuntimeException {
	
	private static final long serialVersionUID = 2164802109640853641L;
	
	public ClosedConnectionException(InterruptedException e) {
		super(e);
	}

	public ClosedConnectionException(String s) {
		super(s);
	}

	public ClosedConnectionException() {
		
	} 
}