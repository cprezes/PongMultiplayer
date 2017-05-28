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
* Prosty klient w �rodowisku Swing  
 * Buduje okienko z tekstem do wprowadzania wiadomo�ci a
 *
 * Klient post�puje zgodnie z zasadami, kt�ry wygl�daj� nast�puj�co:
 * Kiedy serwer wysy�a "SUBMITNAME" klient odpowiada za
 * dop�ki klient nie poda nazwy w�asnej, kt�ra nie jest  
 * ju� w u�yciu. Wtedy serwer wysy�a pocz�tek linii
 * Z "NAMEACCEPTED" klient mo�e teraz zacz��  wysy�anie do serwera dowolnych znak�w
 * Rozmowy kt�re zostan� wy�wietlone musz� rozpoczyna� si� 
 * od "MESSAGE".
 */
public class Client {

    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);

    /**
	 * Konstruktor klienta, tworz�cy GUI i rejestruj�c
     * S�uchacz z pola tekstowego tak, �e naciskaj�c Enter 
     * Wyzwalacz wysy�a zawarto�� pola tekstowego do serwera. 
     * Na pocz�tku pola tekstowego nie mo�na edytowa�, 
     * Staje si� edytowalne tylko po otrzymaniu klienta NAMEACCEPTED
     * od serwera.
     */
    public Client() {

        // Przyk�adowe  GUI
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
     * Potwierd� wprowad� adres serwera.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Wpisz ip serwera gry ",
            "Witam",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Wprowad� swoj� nazw� uzytkownika
     */
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Wpisz swoj� nazw� gracza:",
            "Wprowad� nazw� gracza",
            JOptionPane.PLAIN_MESSAGE);
    }

    /**
     *��czy si� z serwerem, a nast�pnie wchodzi do p�tli przetwarzania.
     */
    private void run() throws IOException {

        // Nawi�� po��czenie i zainicjuj strumienie
        String serverAddress = getServerAddress();
        @SuppressWarnings("resource")
		Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Przetwarzaj wszystkie wiadomo�ci z serwera, zgodnie z protoko�em.
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
     * Uruchamia klienta jako aplikacj� z okienkow�.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}