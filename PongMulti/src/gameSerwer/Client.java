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
* Prosty klient w œrodowisku Swing  
 * Buduje okienko z tekstem do wprowadzania wiadomoœci a
 *
 * Klient postêpuje zgodnie z zasadami, który wygl¹daj¹ nastêpuj¹co:
 * Kiedy serwer wysy³a "SUBMITNAME" klient odpowiada za
 * dopóki klient nie poda nazwy w³asnej, która nie jest  
 * ju¿ w u¿yciu. Wtedy serwer wysy³a pocz¹tek linii
 * Z "NAMEACCEPTED" klient mo¿e teraz zacz¹æ  wysy³anie do serwera dowolnych znaków
 * Rozmowy które zostan¹ wyœwietlone musz¹ rozpoczynaæ siê 
 * od "MESSAGE".
 */
public class Client {

    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);

    /**
	 * Konstruktor klienta, tworz¹cy GUI i rejestruj¹c
     * S³uchacz z pola tekstowego tak, ¿e naciskaj¹c Enter 
     * Wyzwalacz wysy³a zawartoœæ pola tekstowego do serwera. 
     * Na pocz¹tku pola tekstowego nie mo¿na edytowaæ, 
     * Staje siê edytowalne tylko po otrzymaniu klienta NAMEACCEPTED
     * od serwera.
     */
    public Client() {

        // Przyk³adowe  GUI
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
     * PotwierdŸ wprowadŸ adres serwera.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Wpisz ip serwera gry ",
            "Witam",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * WprowadŸ swoj¹ nazwê uzytkownika
     */
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Wpisz swoj¹ nazwê gracza:",
            "WprowadŸ nazwê gracza",
            JOptionPane.PLAIN_MESSAGE);
    }

    /**
     *£¹czy siê z serwerem, a nastêpnie wchodzi do pêtli przetwarzania.
     */
    private void run() throws IOException {

        // Nawi¹¿ po³¹czenie i zainicjuj strumienie
        String serverAddress = getServerAddress();
        @SuppressWarnings("resource")
		Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Przetwarzaj wszystkie wiadomoœci z serwera, zgodnie z protoko³em.
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
     * Uruchamia klienta jako aplikacjê z okienkow¹.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}