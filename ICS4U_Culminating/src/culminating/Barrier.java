package culminating;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Barrier {
	public ArrayList<Coordinates> c = new ArrayList<Coordinates>();
	public Barrier() {

	}

	public void draw(GraphicsContext gc) {
		if (c.isEmpty() == true) {
			
		}
		gc.setFill(Color.RED);
		gc.setStroke(Color.RED);
		
		for (int i = 0; i < c.size(); i++) {
			if(c.get(i).getTimer() >= 30) {
				//gc.setStroke(Color.WHITE);
				//gc.strokeLine(c.get(i).getX(), c.get(i).getY(), c.get(i++).getX(), c.get(i++).getY());
				gc.setFill(Color.WHITE);
				gc.fillOval(c.get(i).getX(), c.get(i).getY(), 50, 50);
				c.clear();
			}
			else {
				gc.fillOval(c.get(i).getX(), c.get(i).getY(), 10, 20);
				//gc.strokeLine(c.get(i).getX(), c.get(i).getY(), c.get(i++).getX(), c.get(i++).getY());
			}
			
		}
	}
}
