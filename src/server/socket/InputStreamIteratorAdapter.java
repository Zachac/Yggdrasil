package server.socket;

import java.io.InputStream;
import java.util.Scanner;

import server.client.input.ClientCommandIterator;

public class InputStreamIteratorAdapter implements ClientCommandIterator, AutoCloseable {

	private final Scanner s;
	
	public InputStreamIteratorAdapter(InputStream inputStream) {
		s = new Scanner(inputStream);
	}

	@Override
	public boolean hasNext() {
		return s.hasNextLine();
	}

	@Override
	public String next() {
		return s.nextLine();
	}

	@Override
	public void close() {
		s.close();
	}

}
