package culminating;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Barrier {
	public ArrayList<Coordinates> c = new ArrayList<Coordinates>();
	GraphicsContext gc;
	public Barrier() {

	}

	public void draw(GraphicsContext gc) {
		this.gc = gc;
		int y = 0;
		int x = 0;
		int b = 0;
		double m = 0;
		
		//gc.setFill(Color.RED);
		gc.setStroke(Color.RED);
		for (int i = 0; i < c.size()-1; i++) {
			m = (c.get(i+1).getY() - c.get(i).getY())/(c.get(i+1).getX() - c.get(i).getX());
			gc.strokeLine(c.get(i).getX(), c.get(i).getY(), c.get(i+1).getX(), c.get(i+1).getY());
		}
		gc.setStroke(Color.BLACK);
		
	}
	public void clear() {
		for (int i = 0; i < c.size()-1; i++) {
			gc.setStroke(Color.WHITE);
			gc.strokeLine(c.get(i).getX(), c.get(i).getY(), c.get(i+1).getX(), c.get(i+1).getY());
			c.clear();
		}
	}
}
