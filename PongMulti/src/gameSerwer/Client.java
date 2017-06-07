package gameSerwer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prosty klient w środowisku Swing Buduje okienko z tekstem do wprowadzania
 * wiadomości a
 *
 * Klient postępuje zgodnie z zasadami, który wyglądają następująco: Kiedy
 * serwer wysyła "SUBMITNAME" klient odpowiada za dopóki klient nie poda nazwy
 * własnej, która nie jest już w użyciu. Wtedy serwer wysyła początek linii Z
 * "NAMEACCEPTED" klient może teraz zacząć wysyłanie do serwera dowolnych znaków
 * Rozmowy które zostaną wyświetlone muszą rozpoczynać się od "MESSAGE".
 */
public class Client  {

	BufferedReader in;
	PrintWriter out;
	BlockingQueue<String> queue;

	String myID;
	String serverAddress;

	/**
	 * Konstruktor klienta, tworzący GUI i rejestrując Słuchacz z pola
	 * tekstowego tak, że naciskając Enter Wyzwalacz wysyła zawartość pola
	 * tekstowego do serwera. Na początku pola tekstowego nie można edytować,
	 * Staje się edytowalne tylko po otrzymaniu klienta NAMEACCEPTED od serwera.
	 * 
	 * @throws IOException
	 */
	public Client(String myID, String serverAddress) throws IOException {
		this.myID = myID;
		this.serverAddress = serverAddress;
		 queue = new ArrayBlockingQueue<String>(7024);
		 connectProcess.start();

	}

	/**
	 * Łączy się z serwerem, a następnie wchodzi do pętli przetwarzania.
	 */
	Thread connectProcess= new Thread() {
		@Override
		public void run() {
			int lenMyId=myID.length()+6;
			// Nawiąż połączenie i zainicjuj strumienie
			// Przetwarzaj wszystkie wiadomości z serwera, zgodnie z protokołem.
			try {
				@SuppressWarnings("resource")
				Socket socket = new Socket(serverAddress, 9001);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e1) {
					e1.printStackTrace();
			}

			while (true) {
				String line = null;
				try {
					line = in.readLine();
				} catch (IOException e) {
				}
				if (line.startsWith("SUBMITNAME")) {
					out.println(myID);
				} else if (line.startsWith("NAMEACCEPTED")) {
					System.out.println(line + " " + myID);
				} else if (line.startsWith("MESSAGE")) {
					if (!(checkName(line).equals(myID))){ 
						queue.add(line.substring(8+checkName(line).length()+6));
					}
				}
			}
		}
		private String checkName(String tekst) {
		final Pattern pattern = Pattern.compile("<n>(.+?)<k>");
		final Matcher matcher = pattern.matcher(tekst);
		matcher.find();
		return matcher.group(1);
		}

	};

	public void send(String toSend) {
		out.println(toSend);

	}

	public String recive() {
		String ret = "";
		if (!queue.isEmpty())
			ret = queue.poll();

		return ret;
	}



}
