package culminating;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Laser.java
 * This class will store data and create a laser.
 * June 18, 2018
 */
public class Laser {
	double laserAttackWidth;
	double width;
	boolean hasFired = false;
	Color color = Color.BLACK;
	double x1;
	double y1;
	double x2;
	double y2;
	/**
	 * Constructs a new laser that stores information.
	 * @param x1 the X coordinate of the starting point of the line.
	 * @param y1 the Y coordinate of the starting point of the line.
	 * @param x2 the X coordinate of the ending point of the line.
	 * @param y2 the Y coordinate of the ending point of the line.
	 * @param width the width of the laser.
	 * @param color the colour of the laser.
	 */
	public Laser(double x1, double y1, double x2, double y2, double width, Color color) {
		this.width = width;
		laserAttackWidth = width;
		this.color = color;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
/**
 * Draws the laser.
 * @param gc the graphics context to allow drawing.
 */
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
