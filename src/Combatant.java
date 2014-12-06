import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Combatant extends Group {
	public static final double CHAR_HEIGHT = 50;
	public static final double CHAR_WIDTH = 50;
	private static final double MOVE_SPEED = 2;

	public enum Direction {
		LEFT, RIGHT, NONE
	}

	public Combatant(Color color, Map map) {
		this.map = map;
		Rectangle character = new Rectangle(CHAR_WIDTH, CHAR_HEIGHT, color);
		getChildren().add(character);
	}

	public boolean reset() {
		vx = 0;
		vy = 0;
		damage = .1;
		movDir = Direction.NONE;
		setTranslateY(CombatGame.WINDOW_HEIGHT - CHAR_HEIGHT * 2);
		setTranslateX(CombatGame.WINDOW_WIDTH / 2);
		lives--;
		if (lives == 0)
			return true;
		else
			return false;
	}

	public void move() {
		if (getMoveDir() == Direction.LEFT) {
			if (vx >= -MOVE_SPEED * 2)
				adjustXVel(-MOVE_SPEED);
		} else if (getMoveDir() == Direction.RIGHT) {
			if (vx <= MOVE_SPEED * 2)
				adjustXVel(MOVE_SPEED);
		}
		if (vx < 0) {
			vx += MOVE_SPEED / 3;
		} else if (vx > 0) {
			vx -= MOVE_SPEED / 3;
		}
		if (vx <= .5 && vx >= -.5)
			vx = 0;
		setTranslateX(getTranslateX() + vx);
		setTranslateY(getTranslateY() - getYVel());
		Rectangle collision = (Rectangle) map.hitsMap(new Point2D(
				getTranslateX(), getTranslateY() + CHAR_HEIGHT));
		if (collision instanceof Powerup) {
			if (((Powerup) collision).getColor() == Color.RED) {
				target.incrementDamage(.5);
			} else if (((Powerup) collision).getColor() == Color.BLUE) {
				incrementDamage(-.5);
			}
			map.getChildren().remove(collision);
		} else {
			if (getTranslateY() <= CombatGame.WINDOW_HEIGHT - CHAR_HEIGHT
					&& collision == null) {
				adjustYVel(-1);
			} else {
				if (collision == null) {
					jumps = 0;
					setTranslateY(CombatGame.WINDOW_HEIGHT - CHAR_HEIGHT);
					setYVel(0);
				} else {
					if (vy < 0) {
						jumps = 0;
						setYVel(0);
						setTranslateY(collision.getY() - CHAR_HEIGHT);
					}

				}
			}
		}

	}

	public void attack() {
		if (Math.abs(getTranslateX() - target.getTranslateX())
				- Combatant.CHAR_WIDTH <= 30
				&& Math.abs(getTranslateY() - target.getTranslateY())
						- Combatant.CHAR_HEIGHT <= 75) {
			target.incrementDamage(.1);
			double tVelAdjust = 100 * (target.getDamage()>0?target.getDamage():0);
			if (getTranslateX() > target.getTranslateX()) {
				target.setXVel(target.getXVel() - tVelAdjust);
			} else
				target.setXVel(target.getXVel() + tVelAdjust);
		}
	}

	public int getWins() {
		return wins;
	}

	public void incrementWins() {
		wins++;
	}

	public double getLives() {
		return lives;
	}

	public void setLives(int l) {
		lives = l;
	}

	public double getXVel() {
		return vx;
	}

	public double getYVel() {
		return vy;
	}

	public void adjustXVel(double increment) {
		vx += increment;
	}

	public void adjustYVel(double increment) {
		setYVel(getYVel() + increment);
	}

	public void setXVel(double vel) {
		vx = vel;
	}

	public void setYVel(double vel) {
		vy = vel;
	}

	public void setMovDir(Direction dir) {
		movDir = dir;
	}

	public Direction getMoveDir() {
		return movDir;
	}

	public void incrementDamage(double i){
		setDamage(getDamage()+i);
	}
	
	public void setDamage(double d) {
		damage = d;
	}

	public double getDamage() {
		return damage;
	}

	public void setJumps(int j) {
		jumps = j;
	}

	public double getJumps() {
		return jumps;
	}

	public void setTarget(Combatant t) {
		target = t;
	}

	public Combatant getTarget() {
		return target;
	}

	Combatant target;
	private int jumps;
	private Map map;
	private double damage = .1;
	private int lives = 3;
	private int wins = 0;
	private Direction movDir;
	private double vx = 0;
	private double vy = 0;
}
