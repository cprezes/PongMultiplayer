package gameSerwer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class start1 {

	public static void main(String[] args) throws IOException, InterruptedException {
		Client client1 = new Client("client1","localhost");
		TimeUnit.SECONDS.sleep(1);
		Client client2 = new Client("client2","localhost");
		TimeUnit.SECONDS.sleep(1);
		client1.send("wysylam info jako client1");
		TimeUnit.SECONDS.sleep(1);
		w(client1.receive()+" client 1 odbior");
		TimeUnit.SECONDS.sleep(1);
		w(client2.receive()+" client 2 odbior");
		TimeUnit.SECONDS.sleep(1);
		client2.send(" jestem clientem 2");
		TimeUnit.SECONDS.sleep(1);
		w(client1.receive()+" client 1 odbior ");
		TimeUnit.SECONDS.sleep(1);
		w(client2.receive()+" client 2 odbior");
		TimeUnit.SECONDS.sleep(1);
	}
	public static void w(String w) {
		System.out.println(w);
		
	}
}
