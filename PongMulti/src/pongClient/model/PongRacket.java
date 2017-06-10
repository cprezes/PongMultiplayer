package pongClient.model;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Klasa generuj�ca oraz zarz�dzaj�ca rakietk� gracza. W postaci prostok�ta o
 * zdefiniowanej szeroko��. W kasie s� zawarte metody definiuj�ce zachowanie
 * oraz podrzenie rakiety gracza. Przechowywane w s� tu r�wnie� statystyki
 * ka�dego obiektu rakiety, parametry okre�laj�ce wy�wietlanie oraz okre�laj�cy
 * interakcje z obiektem {@link PongBall} zmieniaj�c jego wektory ruchu oraz
 * pomorzenie w przestrzeni na podstawie interakcji.
 */
public class PongRacket {

	private Rectangle racket; // Parametr okre�laj�cy wsp�rz�dne prostok�ta
								// definiuj�cego po�o�enie rakietki.
	// private Image racketBackground;

	private int bounceNumber; // Zmienna stosowana do statystyk aktywno�ci
								// poszczeg�lnych graczy.

	private Bloom racketBloom; // Zmienna stosowana do definiowania efektu
								// wy�wietlania rakietki.

	/**
	 * Bezparametryczny konstruktor klasy, Uruchamia metody �aduj�ce kontent
	 * oraz wype�niaj�ce wi�kszo�� p�l obiektu. Wi�kszo�� warto�� parametr�w
	 * zostaje za�adowana zdefiniowanymi na sta�e warto�ciami okre�laj�cymi
	 * wielko�� i szeroko�� prostok�ta {@link PongRacket} oraz parametry
	 * definiuj�ce rodzaj wy�wietlania elementu
	 */
	public PongRacket() {
		racket = new Rectangle();
		racket.setArcHeight(20);
		racket.setArcWidth(20);

		racketBloom = new Bloom();
		racketBloom.setThreshold(1.0);
		racket.setEffect(racketBloom);

		// racket.setFill(new ImagePattern(racketBackground)); //ewentualny
		// background

		BoxBlur bb = new BoxBlur();
		bb.setWidth(5);
		bb.setHeight(5);
		bb.setIterations(1);
		racket.setEffect(bb);

	}

	/**
	 * Metoda okre�la wielko�� wysoko�ci prostok�ta rakietki.
	 * 
	 * @param h
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            wielko�� w pikselach
	 */
	public void setHeight(int h) {
		racket.setHeight(h);
	}

	/**
	 * Metoda okre�la wielko�� szeroko�� prostok�ta rakietki.
	 * 
	 * @param w
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            szeroko�� w pikselach
	 */
	public void setWidth(int w) {
		racket.setWidth(w);
	}

	/**
	 * Metoda definiuje pomorzenie prostok�ta w przestrzeni dwu wymiarowej na
	 * macierzy sceny gry {@link TitleScreenView#setView}
	 * 
	 * @param x
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            przesuniecie szeroko�ci obiektu w odst�pie od lewego g�rnego
	 *            roku pikselach
	 * @param y
	 *            parametr przyjmuje warto�� liczb� ca�kowit� okre�laj�c�
	 *            przesuniecie wysoko�ci obiektu w odst�pie od lewego g�rnego
	 *            roku pikselach
	 */
	public void setPosition(int x, int y) {
		racket.setTranslateX(x);
		racket.setTranslateY(y);
	}

	/**
	 * Metoda maj�ca na celu przekazanie informacji o pomorzeniu w przestrzenie
	 * dwu wymiarowej obiektu {@link PongRacket}.
	 * 
	 * @return zwracany jest obiekt Rectangle z zawartymi w wewn�trz
	 *         interesuj�cymi nas wsp�rz�dnymi
	 */
	public Rectangle getRacket() {
		return racket;
	}

	/**
	 * Metod s�u�y do zwracania pola kt�ra przechowuje liczb� dobi� rakietki.
	 * 
	 * @return zwracana jest liczba reprezentuj�ca odbicia pi�eczki od rakietki
	 */
	public int getBounceNumber() {
		return bounceNumber;
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyj�tym
	 * s�ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje warto�ci z obiektu {@link Color}
	 */
	public void setRacketFill(Color colour) {
		racket.setFill(colour);
	}

	/**
	 * Metoda ustawia liczb� odbi� dla zamiennej obiektu rakieta.
	 * 
	 * @param bounceNumber
	 *            liczba ca�kowita reprezentuj�ca liczb� odbi�
	 */
	public void setBounceNumber(int bounceNumber) {
		this.bounceNumber = bounceNumber;
	}

	/**
	 * Wywo�anie metody powoduje inkrementacj� liczby odbi� dla zamiennej
	 * obiektu {@link PongRacket}.
	 */
	public void incrementBounceNumber() {
		bounceNumber++;
	}

	/**
	 * Metoda pozwala na wykrycie kolizji obiektu {@link PongRacket} oraz
	 * obiektu {@link PongBall}. Gdy parametr {@link Shape.intersect} przyjmie
	 * inna jak -1 oznacza, to i� obiekty wesz�y w kolizje. Zostaje obliczona w
	 * kt�rym miejscu obiektu {@link PongRacket} proporcjonalnie do �rodka oraz
	 * obliczany jest wektor ruchu {@link PongBall}. Zostaj� nast�pnie zmienione
	 * nast�puj�ce parametry w obiekcie {@link PongBall} przy u�yciu funkcji
	 * {@link Math#pi}. Po przebrodzonych obliczaniach na obiekt
	 * {@link PongBall} zostaje zaburzony wektor ruchu i przekazany do obiektu .
	 * 
	 * @param ball
	 *            przekazywany jest obiekt reprezentuj�cy pi�eczk�
	 * @return zawracana warto�ci boolowska gdy dla 2 obiekt�w nast�pi�a kolizja
	 */
	public boolean intersectBall(PongBall ball) {
		boolean collisionDetected = false;

		Shape intersect = Shape.intersect(this.racket, ball.getBall());
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;

			int vX = ball.getVelocityX();
			int vY = ball.getVelocityY();

			double ballY = ball.getBall().getCenterY();
			double racketCenterY = racket.getTranslateY() + racket.getHeight() / 2;
			double angle = (racketCenterY - ballY) / (racket.getHeight() / 2) * Math.PI / 2;

			double modulusV = Math.sqrt(vX * vX + vY * vY);
			double sign_vX = Math.signum(vX);

			vX = (int) (Math.cos(angle) * modulusV);
			vX = -(int) (vX * sign_vX);
			vY = (int) (Math.sin(angle) * modulusV);
			ball.setVelocity(vX, vY);
		}

		return collisionDetected;
	}
}
