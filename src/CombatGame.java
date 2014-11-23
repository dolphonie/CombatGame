import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CombatGame extends Application {
	public static final double WINDOW_HEIGHT = 1000;
	public static final double WINDOW_WIDTH = 1000;
	private static final double KILL_REGION = 200;
	private static final boolean PRINT_DATA = false;
	private static final double[] STARTING_X = { WINDOW_WIDTH / 3,
			WINDOW_WIDTH / 3 * 2 };
	private static final double STARTING_Y = WINDOW_HEIGHT
			- Combatant.CHAR_HEIGHT * 2;
	private static final double TEXT_SIZE = 200;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		rgen = new Random();
		primaryStage.setTitle("Combat Game (Only $9999.99)");
		Group root = new Group();
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		map = new Map();
		combs[0] = new Combatant(Color.ORANGE, map);
		combs[0].setTranslateY(STARTING_Y);
		combs[0].setTranslateX(STARTING_X[0]);
		combs[1] = new Combatant(Color.RED, map);
		combs[1].setTranslateY(STARTING_Y);
		combs[1].setTranslateX(STARTING_X[1]);
		damageDisplay = new Text(WINDOW_WIDTH / 2 - TEXT_SIZE, 50, "");
		damageDisplay.setFont(new Font(30));
		updateDamageDisplay();
		livesDisplay = new Text(WINDOW_WIDTH / 4 - TEXT_SIZE, 50, "");
		livesDisplay.setFont(new Font(30));
		updateLivesDisplay();
		winsDisplay = new Text(WINDOW_WIDTH / 4 * 3 , 50, "");
		winsDisplay.setFont(new Font(30));
		updateWinsDisplay();
		root.getChildren().addAll(combs[0], combs[1], damageDisplay,
				livesDisplay, winsDisplay, map);
		primaryStage.setScene(scene);
		EventHandler<KeyEvent> keyPressHandler = (new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				handleKeyPressEvent(ke);
			}
		});
		EventHandler<KeyEvent> keyReleaseHandler = (new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				handleKeyReleaseEvent(ke);
			}
		});
		scene.setOnKeyPressed(keyPressHandler);
		scene.setOnKeyReleased(keyReleaseHandler);
		primaryStage.show();
		animate();
	}

	private void animate() {
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				combs[0].move();
				combs[1].move();
				for (Combatant c : combs) {
					if(rgen.nextInt(100)==42){
						//Powerup p = new Powerup(Color.BLUE);
						//map.getChildren().add(p);
					}
					
					boolean isDead = false;
					if (c.getTranslateX() <= -KILL_REGION
							|| c.getTranslateX() >= WINDOW_HEIGHT + KILL_REGION)
						isDead = c.reset();
					if (isDead) {
						if (c == combs[0]){
							combs[1].incrementWins();
						}else combs[0].incrementWins();
						System.out.println((c == combs[0] ? "Red Player"
								: "Orange Player") + " Wins!");
						restart();
					}
				}
				updateDamageDisplay();
				updateLivesDisplay();
				updateWinsDisplay();
			}
		}.start();
	}

	private void restart() {
		for (int i = 0; i < combs.length; i++) {
			Combatant c = combs[i];
			c.reset();
			c.setTranslateX(STARTING_X[i]);
			c.setLives(3);
		}
	}

	private void handleKeyPressEvent(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		EventType<KeyEvent> et = ke.getEventType();
		if (PRINT_DATA)
			System.out.println(et);
		switch (kc) {

		case A:
			combs[0].setMovDir(Combatant.Direction.LEFT);
			break;
		case D:
			combs[0].setMovDir(Combatant.Direction.RIGHT);
			break;
		case G:
			combs[0].attack(combs[1]);
			break;
		case NUMPAD1:
			combs[1].attack(combs[0]);
			break;
		case LEFT:
			combs[1].setMovDir(Combatant.Direction.LEFT);
			break;
		case RIGHT:
			combs[1].setMovDir(Combatant.Direction.RIGHT);
			break;
		case W:
			if (combs[0].getJumps() < 2) {
				combs[0].setJumps((int) (combs[0].getJumps() + 1));
				combs[0].setYVel(20);
			}
			break;
		case UP:
			if (combs[1].getJumps() < 2) {
				combs[1].setJumps((int) (combs[1].getJumps() + 1));
				combs[1].setYVel(20);
			}
			break;
		default:
			break;
		}
	}

	private void updateDamageDisplay() {
		damageDisplay.setText("Orange Damage: "
				+ (double) Math.round(combs[0].getDamage() * 10) / 10
				+ "\nRed Damage: "
				+ (double) Math.round(combs[1].getDamage() * 10) / 10);
	}

	private void updateLivesDisplay() {
		livesDisplay.setText("Orange Lives: " + (int) combs[0].getLives()
				+ "\nRed Lives: " + (int) combs[1].getLives());
	}

	private void updateWinsDisplay() {
		winsDisplay.setText("Orange Wins: " + combs[0].getWins()
				+ "\nRed Wins: " + combs[1].getWins());
	}

	private void handleKeyReleaseEvent(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		EventType<KeyEvent> et = ke.getEventType();
		if (PRINT_DATA)
			System.out.println(et);
		switch (kc) {

		case A:
			if (combs[0].getMoveDir() != Combatant.Direction.RIGHT)
				combs[0].setMovDir(Combatant.Direction.NONE);
			break;
		case D:
			if (combs[0].getMoveDir() != Combatant.Direction.LEFT)
				combs[0].setMovDir(Combatant.Direction.NONE);
			break;
		case LEFT:
			if (combs[1].getMoveDir() != Combatant.Direction.RIGHT)
				combs[1].setMovDir(Combatant.Direction.NONE);
			break;
		case RIGHT:
			if (combs[1].getMoveDir() != Combatant.Direction.LEFT)
				combs[1].setMovDir(Combatant.Direction.NONE);
			break;

		default:
			break;
		}
	}

	private Random rgen;
	private Map map;
	private Text damageDisplay, livesDisplay, winsDisplay;
	private Combatant[] combs = { new Combatant(Color.ORANGE, map),
			new Combatant(Color.RED, map) };// Combatant Lists
}