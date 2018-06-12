package culminating;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends MovingObject{

	int base;
	double[] xPoints = new double[3];
	double[] yPoints = new double[3];
	double mouseLocationX;
	double mouseLocationY;
	double shrt = 7.5;
	double lng = 30;
	boolean dead = false;
	Health health;

	public Player(double x, double y, int left, int right, int top, int bottom, boolean bounce) {
		super(x, y, left, right, top, bottom, bounce);
		health = new Health(100);
		xPoints[0] =x- 5;
		xPoints[1] =x;
		xPoints[2] =x+ 5;

		yPoints[0] =y;
		yPoints[1] =y+ 20;
		yPoints[2] =y;

		dead = false;

	}

	int ptNum = 3;



	@Override
	/**
	 * draws the player's triangle character
	 */
	public void draw(GraphicsContext gc) {

//        Image imageNewGame = new Image(getClass().getResourceAsStream("/assets/images/car.png"));

        double xdiff = mouseLocationX - playerLocationX;
		double ydiff = mouseLocationY - playerLocationY;
		
		double angle = Math.atan(ydiff/xdiff);


		if (xdiff == 0) {
			xPoints[0] = playerLocationX - shrt;
			xPoints[1] = playerLocationX;
			xPoints[2] = playerLocationX + shrt;

			yPoints[0] = playerLocationY;
			yPoints[1] = playerLocationY + lng;
			yPoints[2] = playerLocationY;
		}
		//q 1
		if (xdiff > 0 && ydiff < 0) {

			xPoints[0] = playerLocationX - shrt * Math.cos(angle + (Math.PI/2));
			xPoints[1] = playerLocationX + lng * Math.cos(angle);
			xPoints[2] = playerLocationX + shrt * Math.cos(angle + (Math.PI/2));

			yPoints[0] = playerLocationY - shrt * Math.sin(angle + (Math.PI/2));
			yPoints[1] = playerLocationY + lng * Math.sin(angle);
			yPoints[2] = playerLocationY + shrt * Math.sin(angle + (Math.PI/2));
		}
		//q 4
		if (xdiff > 0 && ydiff > 0) {

			xPoints[0] = playerLocationX + shrt * Math.cos(angle - (Math.PI/2));
			xPoints[1] = playerLocationX + lng * Math.cos(angle);
			xPoints[2] = playerLocationX + shrt * Math.cos(angle + (Math.PI/2));

			yPoints[0] = playerLocationY + shrt * Math.sin(angle - (Math.PI/2));
			yPoints[1] = playerLocationY + lng * Math.sin(angle);
			yPoints[2] = playerLocationY + shrt * Math.sin(angle + (Math.PI/2));
		}
		//q 2
		if (xdiff < 0 && ydiff < 0) {

			xPoints[0] = playerLocationX + shrt * Math.cos(angle + (-Math.PI/2));
			xPoints[1] = playerLocationX - lng * Math.cos(angle);
			xPoints[2] = playerLocationX + shrt * Math.cos(angle + (Math.PI/2));

			yPoints[0] = playerLocationY + shrt * Math.sin(angle + (-Math.PI/2));
			yPoints[1] = playerLocationY - lng * Math.sin(angle);
			yPoints[2] = playerLocationY + shrt * Math.sin(angle + (Math.PI/2));
		}
		// q 3
		if (xdiff < 0 && ydiff > 0) {

			xPoints[0] = playerLocationX + shrt * Math.cos(angle + (Math.PI/2));
			xPoints[1] = playerLocationX - lng * Math.cos(angle);
			xPoints[2] = playerLocationX - shrt * Math.cos(angle + (Math.PI/2));

			yPoints[0] = playerLocationY + shrt * Math.sin(angle + (Math.PI/2));
			yPoints[1] = playerLocationY - lng * Math.sin(angle);
			yPoints[2] = playerLocationY - shrt * Math.sin(angle + (Math.PI/2));
		}


		gc.setFill(color);
		gc.fillPolygon(xPoints, yPoints, ptNum);

	}


	@Override
	//no animation for the player object
	public void animateOneStep() {
		// TODO Auto-generated method stub

	}
}
