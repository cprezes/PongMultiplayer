package pongClient.model;

import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Klasa reprezentuje obiekt okre�laj�cy pi�k�. Nadaje podstawowe warto�ci
 * pocz�tkowe oraz zmienia parametry jej ruchu na podstawie danych dostarczony
 * na ja ko wska�niki przyspieszenia oraz po�o�enia.
 */
public class PongBall {

	private Circle ball; // Pole obiektu reprezentuj�ce wymiary i rozmiar
							// pi�eczki.
	private int velocityX; // Pole reprezentowane przez liczb� ca�kowit�
							// definiuj�ce przyspieszenie w osi poziomej
							// pi�eczki
	private int velocityY;// Pole reprezentowane przez liczb� ca�kowit�
							// definiuj�ce przyspieszenie w osi pionowej
							// pi�eczki

	/**
	 * Metoda po kt�rej wywo�aniu zostaje sycony obiekt typu
	 * {@link Scene#shape#Circle} o �rodkowym po�o�eniu mierzonym w pikselach.
	 * 
	 * @return zawracana jest warto�� obiektu {@link Scene#shape#Circle}
	 */
	public Circle getBall() {
		return ball;
	}

	/**
	 * Konstruktor obiektu metody {@link PongBall}. Wywo�uje metod�
	 * inicjalizuj�c� wszystkie wymagane warto�ci obiektu.
	 */
	public PongBall() {
		initialize();
	}

	/**
	 * Metoda ustala parametry centrum obiektu {@link Scene#shape#Circle} dla
	 * {@link PongBall} w postaci 2 liczb ca�kowitych maj�cych odzwierciedlane
	 * an uk�adzie wsp�rz�dnych sceny.
	 * 
	 * @param x
	 *            parametr definiuj�cy kolejny numer piksela poziomego licz�c od
	 *            lewej strony sceny
	 * @param y
	 *            parametr definiuj�cy kolejny numer piksela pionowego licz�c od
	 *            g�ry sceny
	 */
	public void setPosition(int x, int y) {
		ball.setCenterX(x);
		ball.setCenterY(y);
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyj�tym
	 * s�ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje warto�ci z obiektu {@link Color}
	 */
	public void setBallFill(Color colour) {
		ball.setFill(colour);
	}

	/**
	 * Metoda okre�la parametr promienia dla obiektu {@link Scene#shape#Circle}
	 * prezentuj�cego pi�eczk�.
	 * 
	 * @param r
	 *            warto�� liczbowa ca�kowita okre�laj�c promie� obiektu
	 *            {@link Scene#shape#Circle}
	 */
	public void setRadius(int r) {
		ball.setRadius(r);
	}

	/**
	 * Metoda inicjalizuj�c� podstawowe warto�ci obiektu {@link PongBall}.
	 * Okre�la pocz�tkowe przyspieszenie i pr�dko�ci� pi�eczki w zwyk�ych
	 * wsp�rz�dnych kartezja�skich. Definiowane przede wszystkim s� takie
	 * warto�ci wysoko�� generacji pi�eczki szeroko�� oraz rozmycie kraw�dzi
	 * kszta�tu podstawowego obiektu {@link Scene#shape#Circle}.
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
	 * Metoda definiuje przyspieszanie w kierunku okre�lonym rzez wektor w
	 * uk�adzie wsp�rz�dnych. W uk�adzie wsp�rz�dnych po�o�enie obiektu
	 * opisujemy przez podanie odleg�o�ci obiektu od pocz�tku uk�adu
	 * wsp�rz�dnych, czyli d�ugo�ci wektora wodz�cego, oraz k�ta, jaki tworzy
	 * wektor wodz�cy z poziom� osi� kartezja�skiego uk�adu wsp�rz�dnych (osi�
	 * X). Nale�y pami�ta� i� obiektach typu {@link JavaFX} uk�ad wsp�rz�dnych
	 * jest odwr�cony i rysuje elementy od lewego g�rnego rogu do prawego
	 * dolnego rogu macierzy pikseli.
	 * 
	 * @param vX
	 *            ca�o liczbowa warto�ci okre�laj�ce przyspieszenia w osi
	 *            poziomej
	 * @param vY
	 *            ca�o liczbowa warto�ci okre�laj�ce przyspieszenia w osi
	 *            pionowej
	 */
	public void setVelocity(int vX, int vY) {
		velocityX = vX;
		velocityY = vY;
	}

	/**
	 * Metoda zwracaj�ca warto�� przyspieszenia w w osi poziomej w postaci
	 * liczby ca�kowej.
	 * 
	 * @return zwracana jest liczba ca�kowita okre�laj�ca przyspieszenie w osi
	 *         poziomej obiektu {@link Scene#shape#Circle}
	 */
	public int getVelocityX() {
		return velocityX;
	}

	/**
	 * Metoda zwracaj�ca warto�� przyspieszenia w osi pionowej w postaci liczby
	 * ca�kowej.
	 * 
	 * @return zwracana jest liczba ca�kowita okre�laj�ca przyspieszenie w osi
	 *         pionowej obiektu {@link Scene#shape#Circle}
	 */

	public int getVelocityY() {
		return velocityY;
	}

	/**
	 * Metoda aktualizuje pozycje centrum pola {@link PongBall#ball} o warto�ci
	 * zadane z obiektach definiuj�cych przyspieszenie
	 * {@link PongBall#getVelocityX()} oraz {@link PongBall#getVelocityY()}.
	 */
	public void updatePosition() {
		ball.setCenterX(ball.getCenterX() + velocityX);
		ball.setCenterY(ball.getCenterY() + velocityY);

		// this.setPosition(ball.getCenterX() + this.velocityX,
		// ball.getCenterY() + this.velocityY);
	}
}
