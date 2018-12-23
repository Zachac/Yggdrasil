package local;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LocalServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		new Thread(() -> {
			server.Main.main(null);
		}).start();
		
		while (true) {
			TimeUnit.SECONDS.sleep(1);
			LocalClient.main(null);
		}
		
	}
	
}
