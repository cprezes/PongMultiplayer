package pongClient.model;

import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Klasa reprezentuje obiekt okreœlaj¹cy pi³kê. Nadaje podstawowe wartoœci
 * pocz¹tkowe oraz zmienia parametry jej ruchu na podstawie danych dostarczony
 * na ja ko wskaŸniki przyspieszenia oraz po³o¿enia.
 */
public class PongBall {

	private Circle ball; // Pole obiektu reprezentuj¹ce wymiary i rozmiar
							// pi³eczki.
	private int velocityX; // Pole reprezentowane przez liczbê ca³kowit¹
							// definiuj¹ce przyspieszenie w osi poziomej
							// pi³eczki
	private int velocityY;// Pole reprezentowane przez liczbê ca³kowit¹
							// definiuj¹ce przyspieszenie w osi pionowej
							// pi³eczki

	/**
	 * Metoda po której wywo³aniu zostaje sycony obiekt typu
	 * {@link Scene#shape#Circle} o œrodkowym po³o¿eniu mierzonym w pikselach.
	 * 
	 * @return zawracana jest wartoœæ obiektu {@link Scene#shape#Circle}
	 */
	public Circle getBall() {
		return ball;
	}

	/**
	 * Konstruktor obiektu metody {@link PongBall}. Wywo³uje metodê
	 * inicjalizuj¹c¹ wszystkie wymagane wartoœci obiektu.
	 */
	public PongBall() {
		initialize();
	}

	/**
	 * Metoda ustala parametry centrum obiektu {@link Scene#shape#Circle} dla
	 * {@link PongBall} w postaci 2 liczb ca³kowitych maj¹cych odzwierciedlane
	 * an uk³adzie wspó³rzêdnych sceny.
	 * 
	 * @param x
	 *            parametr definiuj¹cy kolejny numer piksela poziomego licz¹c od
	 *            lewej strony sceny
	 * @param y
	 *            parametr definiuj¹cy kolejny numer piksela pionowego licz¹c od
	 *            góry sceny
	 */
	public void setPosition(int x, int y) {
		ball.setCenterX(x);
		ball.setCenterY(y);
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyjêtym
	 * s³ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje wartoœci z obiektu {@link Color}
	 */
	public void setBallFill(Color colour) {
		ball.setFill(colour);
	}

	/**
	 * Metoda okreœla parametr promienia dla obiektu {@link Scene#shape#Circle}
	 * prezentuj¹cego pi³eczkê.
	 * 
	 * @param r
	 *            wartoœæ liczbowa ca³kowita okreœlaj¹c promieñ obiektu
	 *            {@link Scene#shape#Circle}
	 */
	public void setRadius(int r) {
		ball.setRadius(r);
	}

	/**
	 * Metoda inicjalizuj¹c¹ podstawowe wartoœci obiektu {@link PongBall}.
	 * Okreœla pocz¹tkowe przyspieszenie i prêdkoœci¹ pi³eczki w zwyk³ych
	 * wspó³rzêdnych kartezjañskich. Definiowane przede wszystkim s¹ takie
	 * wartoœci wysokoœæ generacji pi³eczki szerokoœæ oraz rozmycie krawêdzi
	 * kszta³tu podstawowego obiektu {@link Scene#shape#Circle}.
	 * 
	 */
	private void initialize() {
		ball = new Circle();

		BoxBlur bb = new BoxBlur();
		bb.setWidth(5);
		bb.setHeight(5);
		bb.setIterations(1);
		ball.setEffect(bb);
	}

	/**
	 * Metoda definiuje przyspieszanie w kierunku okreœlonym rzez wektor w
	 * uk³adzie wspó³rzêdnych. W uk³adzie wspó³rzêdnych po³o¿enie obiektu
	 * opisujemy przez podanie odleg³oœci obiektu od pocz¹tku uk³adu
	 * wspó³rzêdnych, czyli d³ugoœci wektora wodz¹cego, oraz k¹ta, jaki tworzy
	 * wektor wodz¹cy z poziom¹ osi¹ kartezjañskiego uk³adu wspó³rzêdnych (osi¹
	 * X). Nale¿y pamiêtaæ i¿ obiektach typu {@link JavaFX} uk³ad wspó³rzêdnych
	 * jest odwrócony i rysuje elementy od lewego górnego rogu do prawego
	 * dolnego rogu macierzy pikseli.
	 * 
	 * @param vX
	 *            ca³o liczbowa wartoœci okreœlaj¹ce przyspieszenia w osi
	 *            poziomej
	 * @param vY
	 *            ca³o liczbowa wartoœci okreœlaj¹ce przyspieszenia w osi
	 *            pionowej
	 */
	public void setVelocity(int vX, int vY) {
		velocityX = vX;
		velocityY = vY;
	}

	/**
	 * Metoda zwracaj¹ca wartoœæ przyspieszenia w w osi poziomej w postaci
	 * liczby ca³kowej.
	 * 
	 * @return zwracana jest liczba ca³kowita okreœlaj¹ca przyspieszenie w osi
	 *         poziomej obiektu {@link Scene#shape#Circle}
	 */
	public int getVelocityX() {
		return velocityX;
	}

	/**
	 * Metoda zwracaj¹ca wartoœæ przyspieszenia w osi pionowej w postaci liczby
	 * ca³kowej.
	 * 
	 * @return zwracana jest liczba ca³kowita okreœlaj¹ca przyspieszenie w osi
	 *         pionowej obiektu {@link Scene#shape#Circle}
	 */

	public int getVelocityY() {
		return velocityY;
	}

	/**
	 * Metoda aktualizuje pozycje centrum pola {@link PongBall#ball} o wartoœci
	 * zadane z obiektach definiuj¹cych przyspieszenie
	 * {@link PongBall#getVelocityX()} oraz {@link PongBall#getVelocityY()}.
	 */
	public void updatePosition() {
		ball.setCenterX(ball.getCenterX() + velocityX);
		ball.setCenterY(ball.getCenterY() + velocityY);

		// this.setPosition(ball.getCenterX() + this.velocityX,
		// ball.getCenterY() + this.velocityY);
	}
}
