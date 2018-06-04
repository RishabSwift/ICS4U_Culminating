package culminating;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Laser {
	double atkWidth;
	double width;
	boolean fire = false;
	Color color;
	double x1;
	double y1;
	double x2;
	double y2;
	
	public Laser(double x1, double y1, double x2, double y2, double width, Color colour) {
		this.width = width;
		atkWidth = width;
		this.color = color;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public void draw(GraphicsContext gc) {
		gc.setStroke(color);
		gc.setLineWidth(width);
		gc.strokeLine(x1, y1, x2, y2);
		animateOneStep();
		if (width < 0 && !fire) {
			width = atkWidth;
			fire = true;
		}
	}

	public void animateOneStep() {
		width -= 1;
	}

}
