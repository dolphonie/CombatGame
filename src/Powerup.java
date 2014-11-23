import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Powerup extends Rectangle{
	public static final double P_WIDTH = 75;
	public static final double P_HEIGHT = 75;
	public Powerup(Color color ){
		super(P_WIDTH,P_HEIGHT,color);
		rgen = new Random();
		setTranslateX(rgen.nextInt((int)CombatGame.WINDOW_WIDTH));
		setTranslateY(rgen.nextInt((int)CombatGame.WINDOW_HEIGHT));
	}
	Random rgen;
}
