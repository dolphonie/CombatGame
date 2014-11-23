import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;


public class Map extends Group{
	private static final double PLAT_HEIGHT = 50;
	
	public Map(){
		double botPlaty = CombatGame.WINDOW_HEIGHT/3*2;
		double botPlatWidth = CombatGame.WINDOW_WIDTH/3;
		Rectangle platLeft = new Rectangle(0,botPlaty,botPlatWidth,PLAT_HEIGHT);
		Rectangle platRight = new Rectangle(CombatGame.WINDOW_WIDTH - botPlatWidth,botPlaty,botPlatWidth,PLAT_HEIGHT);
		double platCenterWidth = CombatGame.WINDOW_WIDTH/2;
		Rectangle platCenter = new Rectangle(CombatGame.WINDOW_WIDTH/2-platCenterWidth/2,CombatGame.WINDOW_HEIGHT/3,platCenterWidth,PLAT_HEIGHT);
		getChildren().addAll(platLeft,platRight,platCenter);
	}
	
	public Node hitsMap(Point2D colPt){
		for(Object value:getChildren().toArray()){
			if(((Node) value).contains(colPt)) return (Node) value;
		}
		return null;
	}
	
}
