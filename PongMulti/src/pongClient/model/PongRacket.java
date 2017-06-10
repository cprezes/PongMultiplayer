package pongClient.model;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Klasa generuj¹ca oraz zarz¹dzaj¹ca rakietk¹ gracza. W postaci prostok¹ta o
 * zdefiniowanej szerokoœæ. W kasie s¹ zawarte metody definiuj¹ce zachowanie
 * oraz podrzenie rakiety gracza. Przechowywane w s¹ tu równie¿ statystyki
 * ka¿dego obiektu rakiety, parametry okreœlaj¹ce wyœwietlanie oraz okreœlaj¹cy
 * interakcje z obiektem {@link PongBall} zmieniaj¹c jego wektory ruchu oraz
 * pomorzenie w przestrzeni na podstawie interakcji.
 */
public class PongRacket {

	private Rectangle racket; // Parametr okreœlaj¹cy wspó³rzêdne prostok¹ta
								// definiuj¹cego po³o¿enie rakietki.
	// private Image racketBackground;

	private int bounceNumber; // Zmienna stosowana do statystyk aktywnoœci
								// poszczególnych graczy.

	private Bloom racketBloom; // Zmienna stosowana do definiowania efektu
								// wyœwietlania rakietki.

	/**
	 * Bezparametryczny konstruktor klasy, Uruchamia metody ³aduj¹ce kontent
	 * oraz wype³niaj¹ce wiêkszoœæ pól obiektu. Wiêkszoœæ wartoœæ parametrów
	 * zostaje za³adowana zdefiniowanymi na sta³e wartoœciami okreœlaj¹cymi
	 * wielkoœæ i szerokoœæ prostok¹ta {@link PongRacket} oraz parametry
	 * definiuj¹ce rodzaj wyœwietlania elementu
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
	 * Metoda okreœla wielkoœæ wysokoœci prostok¹ta rakietki.
	 * 
	 * @param h
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            wielkoœæ w pikselach
	 */
	public void setHeight(int h) {
		racket.setHeight(h);
	}

	/**
	 * Metoda okreœla wielkoœæ szerokoœæ prostok¹ta rakietki.
	 * 
	 * @param w
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            szerokoœæ w pikselach
	 */
	public void setWidth(int w) {
		racket.setWidth(w);
	}

	/**
	 * Metoda definiuje pomorzenie prostok¹ta w przestrzeni dwu wymiarowej na
	 * macierzy sceny gry {@link TitleScreenView#setView}
	 * 
	 * @param x
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            przesuniecie szerokoœci obiektu w odstêpie od lewego górnego
	 *            roku pikselach
	 * @param y
	 *            parametr przyjmuje wartoœæ liczb¹ ca³kowit¹ okreœlaj¹c¹
	 *            przesuniecie wysokoœci obiektu w odstêpie od lewego górnego
	 *            roku pikselach
	 */
	public void setPosition(int x, int y) {
		racket.setTranslateX(x);
		racket.setTranslateY(y);
	}

	/**
	 * Metoda maj¹ca na celu przekazanie informacji o pomorzeniu w przestrzenie
	 * dwu wymiarowej obiektu {@link PongRacket}.
	 * 
	 * @return zwracany jest obiekt Rectangle z zawartymi w wewn¹trz
	 *         interesuj¹cymi nas wspó³rzêdnymi
	 */
	public Rectangle getRacket() {
		return racket;
	}

	/**
	 * Metod s³u¿y do zwracania pola która przechowuje liczbê dobiæ rakietki.
	 * 
	 * @return zwracana jest liczba reprezentuj¹ca odbicia pi³eczki od rakietki
	 */
	public int getBounceNumber() {
		return bounceNumber;
	}

	/**
	 * Metoda definiuje kolor z obiektu {@link Color} zgodnie z przyjêtym
	 * s³ownikiem.
	 * 
	 * @param colour
	 *            przyjmuje wartoœci z obiektu {@link Color}
	 */
	public void setRacketFill(Color colour) {
		racket.setFill(colour);
	}

	/**
	 * Metoda ustawia liczbê odbiæ dla zamiennej obiektu rakieta.
	 * 
	 * @param bounceNumber
	 *            liczba ca³kowita reprezentuj¹ca liczbê odbiæ
	 */
	public void setBounceNumber(int bounceNumber) {
		this.bounceNumber = bounceNumber;
	}

	/**
	 * Wywo³anie metody powoduje inkrementacjê liczby odbiæ dla zamiennej
	 * obiektu {@link PongRacket}.
	 */
	public void incrementBounceNumber() {
		bounceNumber++;
	}

	/**
	 * Metoda pozwala na wykrycie kolizji obiektu {@link PongRacket} oraz
	 * obiektu {@link PongBall}. Gdy parametr {@link Shape.intersect} przyjmie
	 * inna jak -1 oznacza, to i¿ obiekty wesz³y w kolizje. Zostaje obliczona w
	 * którym miejscu obiektu {@link PongRacket} proporcjonalnie do œrodka oraz
	 * obliczany jest wektor ruchu {@link PongBall}. Zostaj¹ nastêpnie zmienione
	 * nastêpuj¹ce parametry w obiekcie {@link PongBall} przy u¿yciu funkcji
	 * {@link Math#pi}. Po przebrodzonych obliczaniach na obiekt
	 * {@link PongBall} zostaje zaburzony wektor ruchu i przekazany do obiektu .
	 * 
	 * @param ball
	 *            przekazywany jest obiekt reprezentuj¹cy pi³eczkê
	 * @return zawracana wartoœci boolowska gdy dla 2 obiektów nast¹pi³a kolizja
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
