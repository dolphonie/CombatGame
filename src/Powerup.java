import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Powerup extends Rectangle{
	public static final double P_WIDTH = 75;
	public static final double P_HEIGHT = 75;
	public Powerup(Color color ){
		super(P_WIDTH,P_HEIGHT,color);
		rgen = new Random();
		setX(rgen.nextInt((int)CombatGame.WINDOW_WIDTH));
		setY(rgen.nextInt((int)CombatGame.WINDOW_HEIGHT));
	}
	
	public Color getColor(){
		return (Color) getFill();
	}
	
	Random rgen;
}
