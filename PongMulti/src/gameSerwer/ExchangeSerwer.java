package gameSerwer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


// TODO: Musimy zdecydowaæ gdzie umieszczamy logikê gry w tej kasie czy ta klasa ma dzia³aæ jako ca³kowity poœrednik.

/**

* Wielow¹tkowy serwer przekazywania informacji dzia³a niezale¿nie jako poœrednik. 
* do rozró¿nienia klientów jest wykorzystywana tylko nazwa klienta.
* Tekst "SUBMITNAME" i nadal prosi o nazwê u¿ytkownika.
* Po otrzymaniu unikalnej nazwy klienta.
* Serwer potwierdza wysy³aj¹c "NAMEACCEPTED". Nastêpnie
* Wszystkie wiadomoœci z tego klienta bêd¹ transmitowane do wszystkich innych.
* Wiadomoœci nadawcze s¹ poprzedzone "MESSAGE".
*/




public class ExchangeSerwer {

    /**
     * Port nas³uchu.
     */
    private static final int PORT = 9001;

    /**
     * Zestaw wszystkich nazw klientów. Zapisywany jest w tablicy
     * aby by³a mo¿liwoœæ sprawdzenia, czy nowi klienci nie rejestruj¹ nazwy
     * bêd¹cej ju¿ w u¿yciu.
     */
    private static HashSet<String> names = new HashSet<String>();

    /**
     * Zestaw wszystkich odbieraj¹cych.
     * Zestaw jest zapisywany aby u³atwiæ transmitowanie wiadomoœci.
     */
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    /**
     * Konstruktor aplikacji, s³ucha w porcie i przekazuje informacje klientom. 
     */
    public ExchangeSerwer() throws Exception {
        System.out.println("Jestem serwerem (Dzia³am jako serwer).");
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
     * Klasa wewnêtrzna w¹tków przekazywania wiadomoœci.
     * Ka¿dy w¹tek jest odpowiedzialny za kontakt z okreœlonym klientem.
     */
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Konstruktor klasy obs³uguj¹cej watkami Socketów .
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Metoda ta inicjuje watek oraz wysy³a zapytanie o podanie unikatowego identyfikatora klienta.
         * Nastêpnie wysy³a potwierdzenia nazwy i rejestruje strumienie dla komunikacji w globalnym zestawie 
         * Nastêpnie przesy³a dane wejœciowe i przekazuje je do emisji.
         */
        public void run() {
            try {

                // Tworzenie strumieni dla Socketów.
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Za¿¹daj nazwy od klienta. ponów proœbê, dopóki nie
                // podano nazwy, która nie jest ju¿ u¿ywana.
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

                // Gdy uda³o siê wybraæ unikaln¹ nazwê, dodaj
                // socket do zestawu wszystkich odbieraj¹cych
                // klient mo¿e teraz odbieraæ wiadomoœci.
                out.println("NAMEACCEPTED");
                writers.add(out);

                // Przyjmowanie wiadomoœci od aktualnego klienta oraz jej wyemitowanie do reszty klientów.
                // Zignoruj innych klientów, których nie mo¿na nadawaæ.
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + input);   //TODO:  Tutaj trzeba wybraæ formê wiadomoœci 
                    }
                }
                
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // Klient siê roz³¹czy³y 
                // Oczyœæ zmienne tego klienta 
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