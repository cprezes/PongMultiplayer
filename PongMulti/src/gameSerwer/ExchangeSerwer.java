package gameSerwer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


// TODO: Musimy zdecydowa� gdzie umieszczamy logik� gry w tej kasie czy ta klasa ma dzia�a� jako ca�kowity po�rednik.

/**

* Wielow�tkowy serwer przekazywania informacji dzia�a niezale�nie jako po�rednik. 
* do rozr�nienia klient�w jest wykorzystywana tylko nazwa klienta.
* Tekst "SUBMITNAME" i nadal prosi o nazw� u�ytkownika.
* Po otrzymaniu unikalnej nazwy klienta.
* Serwer potwierdza wysy�aj�c "NAMEACCEPTED". Nast�pnie
* Wszystkie wiadomo�ci z tego klienta b�d� transmitowane do wszystkich innych.
* Wiadomo�ci nadawcze s� poprzedzone "MESSAGE".
*/




public class ExchangeSerwer {

    /**
     * Port nas�uchu.
     */
    private static final int PORT = 9001;

    /**
     * Zestaw wszystkich nazw klient�w. Zapisywany jest w tablicy
     * aby by�a mo�liwo�� sprawdzenia, czy nowi klienci nie rejestruj� nazwy
     * b�d�cej ju� w u�yciu.
     */
    private static HashSet<String> names = new HashSet<String>();

    /**
     * Zestaw wszystkich odbieraj�cych.
     * Zestaw jest zapisywany aby u�atwi� transmitowanie wiadomo�ci.
     */
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    /**
     * Konstruktor aplikacji, s�ucha w porcie i przekazuje informacje klientom. 
     */
    public ExchangeSerwer() throws Exception {
        System.out.println("Jestem serwerem (Dzia�am jako serwer).");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    /**
     * Klasa wewn�trzna w�tk�w przekazywania wiadomo�ci.
     * Ka�dy w�tek jest odpowiedzialny za kontakt z okre�lonym klientem.
     */
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Konstruktor klasy obs�uguj�cej watkami Socket�w .
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Metoda ta inicjuje watek oraz wysy�a zapytanie o podanie unikatowego identyfikatora klienta.
         * Nast�pnie wysy�a potwierdzenia nazwy i rejestruje strumienie dla komunikacji w globalnym zestawie 
         * Nast�pnie przesy�a dane wej�ciowe i przekazuje je do emisji.
         */
        public void run() {
            try {

                // Tworzenie strumieni dla Socket�w.
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Za��daj nazwy od klienta. pon�w pro�b�, dop�ki nie
                // podano nazwy, kt�ra nie jest ju� u�ywana.
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {   // sprawdzenie istnienia nazwy i dodanie nazwy robione jest podczas blokowania zestawu nazw.
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }

                // Gdy uda�o si� wybra� unikaln� nazw�, dodaj
                // socket do zestawu wszystkich odbieraj�cych
                // klient mo�e teraz odbiera� wiadomo�ci.
                out.println("NAMEACCEPTED");
                writers.add(out);

                // Przyjmowanie wiadomo�ci od aktualnego klienta oraz jej wyemitowanie do reszty klient�w.
                // Zignoruj innych klient�w, kt�rych nie mo�na nadawa�.
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + input);   //TODO:  Tutaj trzeba wybra� form� wiadomo�ci 
                    }
                }
                
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // Klient si� roz��czy�y 
                // Oczy�� zmienne tego klienta 
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}