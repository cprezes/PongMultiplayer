package gameSerwer;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.bind.annotation.W3CDomHandler;

/**
* Prosty klient w środowisku Swing  
 * Buduje okienko z tekstem do wprowadzania wiadomości a
 *
 * Klient postępuje zgodnie z zasadami, który wyglądają następująco:
 * Kiedy serwer wysyła "SUBMITNAME" klient odpowiada za
 * dopóki klient nie poda nazwy własnej, która nie jest  
 * już w użyciu. Wtedy serwer wysyła początek linii
 * Z "NAMEACCEPTED" klient może teraz zacząć  wysyłanie do serwera dowolnych znaków
 * Rozmowy które zostaną wyświetlone muszą rozpoczynać się 
 * od "MESSAGE".
 */
public class Client {

    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);

    /**
	 * Konstruktor klienta, tworzący GUI i rejestrując
     * Słuchacz z pola tekstowego tak, że naciskając Enter 
     * Wyzwalacz wysyła zawartość pola tekstowego do serwera. 
     * Na początku pola tekstowego nie można edytować, 
     * Staje się edytowalne tylko po otrzymaniu klienta NAMEACCEPTED
     * od serwera.
     */
    public Client() {

        // Przykładowe  GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        //  Listeners
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    /**
     * Potwierdź wprowadź adres serwera.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Wpisz ip serwera gry ",
            "Witam",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Wprowadź swoją nazwę uzytkownika
     */
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Wpisz swoją nazwę gracza:",
            "Wprowadź nazwę gracza",
            JOptionPane.PLAIN_MESSAGE);
    }

    /**
     *Łączy się z serwerem, a następnie wchodzi do pętli przetwarzania.
     */
    private void run() throws IOException {

        // Nawiąż połączenie i zainicjuj strumienie
        String serverAddress = getServerAddress();
        @SuppressWarnings("resource")
		Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Przetwarzaj wszystkie wiadomości z serwera, zgodnie z protokołem.
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName());
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }

    /**
     * Uruchamia klienta jako aplikację z okienkową.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}