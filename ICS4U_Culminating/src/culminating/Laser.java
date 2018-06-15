package culminating;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Laser {
	double laserAttackWidth;
	double width;
	boolean hasFired = false;
	Color color = Color.BLACK;
	double x1;
	double y1;
	double x2;
	double y2;
	
	public Laser(double x1, double y1, double x2, double y2, double width, Color color) {
		this.width = width;
		laserAttackWidth = width;
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
		if (width < 0 && !hasFired) {
			width = laserAttackWidth;
			hasFired = true;
		}
	}

	public void animateOneStep() {
		width -= 0.25;
	}

}
