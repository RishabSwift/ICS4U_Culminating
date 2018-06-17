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
		double y = 0;
		double x = 0;
		double b = 0;
		double m = 0;
		double _x = 0;
		
		//gc.setFill(Color.RED);
		gc.setStroke(Color.RED);
		for (int i = 0; i < c.size()-1; i++) {
			/*_x = c.get(i).getX();
			x = c.get(i).getX();
			m = (c.get(i+1).getY() - c.get(i).getY())/(c.get(i+1).getX() - c.get(i).getX());
			b = c.get(i).getY() - (m*c.get(i).getX());
			if (c.get(i).getX() > c.get(i+1).getX()) {
				while (_x >= c.get(i+1).getX()) {
					y = m*x + b;
					Coordinates C = new Coordinates(_x, y);
					c.add(C);
					_x--;
				}
			}
			else if (c.get(i).getX() < c.get(i+1).getX()) {
				while (_x <= c.get(i+1).getX()) {
					y = m*x + b;
					Coordinates C = new Coordinates(_x, y);
					c.add(C);
					_x++;
				}
			}
			*/
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
