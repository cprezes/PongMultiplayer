package pongClient.model;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PongRacketTest {

	
	
	@Test
	public final void testPongRacket() {

		PongRacket tester = new PongRacket();
		
		assertEquals(tester.getClass().toString(),"class pongClient.model.PongRacket");
	}

	@Test
	public final void testSetHeight() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		Exception ex = null; 
		
        try {
        	tester.setHeight(tmp);
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null,ex);
    }
		
		


	@Test
	public final void testSetWidth() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		Exception ex = null; 
		
        try {
        	tester.setWidth(tmp);
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null,ex);
	}

	
	@Test
	public final void testSetPosition() {
		PongRacket tester = new PongRacket();
		int x = 20;
		int y=50;
		Exception ex = null; 
		
        try {
        	tester.setPosition(x, y);
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);
       int testX = (int) tester.getRacket().getTranslateX();
       int testY = (int) tester.getRacket().getTranslateY();
        assertEquals(x, testX);
        assertEquals(y, testY);
        
		
	}

	@Test
	public final void testGetRacket() {
		PongRacket tester = new PongRacket();
		int x = 20;
		int y=50;
		Exception ex = null; 
		
        try {
        	tester.setPosition(x, y);
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);
       int testX = (int) tester.getRacket().getTranslateX();
       int testY = (int) tester.getRacket().getTranslateY();
        assertEquals(x, testX);
        assertEquals(y, testY);
        
	}

	@Test
	public final void testGetBounceNumber() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		tester.setBounceNumber(tmp);
		assertEquals(tmp, tester.getBounceNumber());
	}

	@Test
	public final void testSetRacketFill() {
		PongRacket tester = new PongRacket();

		Exception ex = null; 
		
        try {
        	tester.setRacketFill(Color.BLUE);;
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(null==ex);
	}

	@Test
	public final void testSetBounceNumber() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		tester.setBounceNumber(tmp);
		assertEquals(tmp, tester.getBounceNumber());
	}

	@Test
	public final void testIncrementBounceNumber() {
		PongRacket tester = new PongRacket();
		int tmp = 20;
		tester.setBounceNumber(tmp);
		tester.incrementBounceNumber();
		assertEquals(tmp+1, tester.getBounceNumber());
	}

	@Test
	public final void testIntersectBall() {
		assertTrue(true); //TODO: To trzeba poprawiæ.
	}

}
