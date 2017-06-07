package gameSerwer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class start1 {

	public static void main(String[] args) throws IOException, InterruptedException {
		Client client1 = new Client("client1","localhost");
		TimeUnit.SECONDS.sleep(1);
		Client client2 = new Client("client2","localhost");
		TimeUnit.SECONDS.sleep(1);
		client1.send("test client 1");
		TimeUnit.SECONDS.sleep(1);
		w(client1.recive()+"c1");
		TimeUnit.SECONDS.sleep(1);
		w(client2.recive()+"c2");
		TimeUnit.SECONDS.sleep(1);
		client2.send("test client 2");
		TimeUnit.SECONDS.sleep(1);
		w(client1.recive()+"c1");
		TimeUnit.SECONDS.sleep(1);
		w(client2.recive()+"c2");
		TimeUnit.SECONDS.sleep(1);
	}
	public static void w(String w) {
		System.out.println(w);
		
	}
}
