package culminating;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Boss.java
 * This class will store data and create a boss.
 * June 18, 2018
 */
public class Boss extends MovingObject {

	int stgNum = 1;
	int radius;
	double cornerX;
	double cornerY;
	double speed;
	Health health;
	boolean attacking;
	ArrayList<Bullet> bullet = new  ArrayList<Bullet>();
	ArrayList<Laser> laser = new  ArrayList<Laser>();
	int timeCount = 0;
	int coneShotCount = 0;
	boolean laserAtk = false;
	boolean coneAtk = false;
	
	/**
	 * Constructs a new boss that stores information.
	 * @param x
	 *            Initial x position.
	 * @param y
	 *            Initial y position.
	 * @param left
	 *            Left edge for bouncing.
	 * @param right
	 *            Right edge for bouncing.
	 * @param top
	 *            Top edge for bouncing.
	 * @param bottom
	 *            Bottom edge for bouncing.
	 */
	public Boss(double x, double y, int left, int right, int top, int bottom, boolean bounce) {
		super(x, y, left, right, top, bottom, bounce);
		radius = 30;
		this.cornerX = x - radius;
		this.cornerY = y - radius;
		health = new Health(100);
		switch 	(stgNum) {
		case 1: color = Color.PURPLE;
		speed = 10;
		return;
		case 2: color = Color.BLUE;
		speed = 10;
		return;
		case 3: color = Color.GREEN;
		return;
		case 4: color = Color.YELLOW;
		return;
		case 5: color = Color.ORANGE;
		return;
		case 6: color = Color.RED;
		return;
		}
	}

	@Override
	/**
	 * draws the Boss on the canvas
	 */
	public void draw(GraphicsContext gc) {
		cornerX = x - radius;
		cornerY = y - radius;
		int drawX = (int) cornerX;
		int drawY = (int) cornerY;
		gc.setFill(color);
		gc.fillOval(drawX, drawY, radius * 2, radius * 2);
		//gc.fillText("" + coneCount, 10, 30);
		//gc.fillText("" + coneAtk, 10, 50);

		//				gc.setStroke(color.BLACK);
		//				gc.setLineWidth(5);
		//				gc.strokeLine(x, y, x + 10000, y);
		//				gc.strokeLine(x, y, x - 10000, y);
		//				gc.strokeLine(x, y, x, y + 10000);
		//				gc.strokeLine(x, y, x, y - 10000);

	}

	/**
	 * determines the boss behavior
	 * @param playerX the x coordinant of the player
	 * @param playerY the y coordinant of the player
	 */
	public void behavior(double playerX, double playerY) {
		timeCount++;
		atkBehavior(playerX, playerY);		
		movementBehavior(playerX, playerY);
	}
	/**
	 * determines the boss movement behavior
	 * @param playerX the x coordinant of the player
	 * @param playerY the y coordinant of the player
	 */
	public void movementBehavior(double playerX, double playerY) {
		if (laserAtk || coneAtk) {
			xSpeed = 0;
			ySpeed = 0;
		}
		else {
			switch 	(stgNum) {
			case 1:
				if (timeCount%100 == 0) {
					move1();
				}
				return;
			case 2:
				if (timeCount%100 == 0) {
					move1();
				}			return;
			case 3:
				if (timeCount%100 == 0) {
					move1();
				}			return;
			case 4:
				if (timeCount%100 == 0) {
					move1();
				}			return;
			case 5:
				if (timeCount%100 == 0) {
					move1();
				}			return;
			case 6: 
				if (timeCount%100 == 0) {
					move1();
				}
			}
		}
	}

	/**
	 * determines the attack pattern the boss uses
	 * @param playerX the x coordinant of the player
	 * @param playerY the y coordinant of the player
	 */
	public void atkBehavior(double playerX, double playerY) {
		switch 	(stgNum) {
		case 1:
			if (timeCount%100 == 0) {
				atk1(45, false);
			}
			//atk3(playerX, playerY);
			//atk2(2, playerX, playerY, 20, 10, false);
			//standard attack
			return;
		case 2:
			if (timeCount%100 == 0) {
				atk1(45, true);
			}
			return;
		case 3:
			if (timeCount%100 == 0) {
				atk1(45, false);
				if (timeCount%6 == 0 && !coneAtk) {
					atk2(1, playerX, playerY, 20, 10, true);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(1, playerX, playerY, 20, 10, true);
			}
			return;
		case 4:
			if (timeCount%100 == 0) {
				atk1(45, false);
				if (timeCount%3 == 0 && !coneAtk) {
					atk2(2, playerX, playerY, 5, 10, true);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(2, playerX, playerY, 5, 10, true);
			}
			return;
		case 5:
			if (timeCount%100 == 0) {
				atk1(45, true);
				if (timeCount%3 == 0 && !coneAtk) {
					atk2(1, playerX, playerY, 20, 10, true);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(1, playerX, playerY, 20, 10, true);
			}
			if (timeCount%300 == 0) {
				atk3(playerX, playerY);
			}
			return;
		case 6:
			if (timeCount%100 == 0) {
				atk1(45, true);
				if (timeCount%3 == 0 && !coneAtk) {
					atk2(1, playerX, playerY, 20, 10, true);	
				}
				if (timeCount%5 == 0 && !coneAtk) {
					atk2(2, playerX, playerY, 5, 10, true);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(1, playerX, playerY, 20, 10, true);
			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(2, playerX, playerY, 5, 10, true);
			}
			if (timeCount%300 == 0) {
				atk3(playerX, playerY);
			}
			return;
		}
	}

	@Override
	/**
	 * animation for the boss
	 */
	public void animateOneStep() {
		//TODO boss moving graphics

	}

	/**
	 * resets the boss for the next level of the game
	 */
	public void nextLv() {
		for (int i = bullet.size() -1; i > 0; i--) {
			bullet.remove(i);
		}
		stgNum++;
		timeCount = 0;
		xSpeed = 0;
		ySpeed = 0;
		//TODO starting boss graphics
		switch 	(stgNum) {
		case 1: color = Color.PURPLE;
		return;
		case 2: color = Color.BLUE;
		return;
		case 3: color = Color.GREEN;
		return;
		case 4: color = Color.YELLOW;
		return;
		case 5: color = Color.ORANGE;
		return;
		case 6: color = Color.RED;
		return;
		}
	}

	/**
	 * shoots bullets in at angles around the boss
	 * @param angle the angle between the bullet vectors
	 * @param bounce whether or not the bullet will bounce on collision with the edge of the canvas
	 */
	public void atk1(double angle, boolean bounce) {
		//balls in many directions
		for (int i = 0; i < 360; i++) {
			if (i % angle == 0) {
				Bullet bu = new Bullet(x, y, left, right, top, bottom, bounce);

				if (angle > 45 && angle < 135  ||  angle > 225 && angle < 315) {
					bu.ySpeed = Math.cos(Math.toRadians(i)) * 10;
					bu.xSpeed = Math.sin(Math.toRadians(i)) * 10;
				}
				else {
					bu.xSpeed = Math.cos(Math.toRadians(i)) * 10;
					bu.ySpeed = Math.sin(Math.toRadians(i)) * 10;
				}
				bu.color = color;
				bullet.add(bu);
			}
		}
	}

	/**
	 * attacks in a cone directed towards the character
	 * @param projectileType the type of attack cone; laser or projectile
	 * @param playerX the x location of the player
	 * @param playerY the y location of the player
	 * @param shots the number of shots to be made in the cone attack
	 * @param anglewidth the angle width of the cone
	 * @param bounce whether or not the bullet will bounce on collision with the edge of the canvas
	 */
	public void atk2(int projectileType, double playerX, double playerY, int shots, double anglewidth, boolean bounce) {
		if (!coneAtk) {
			coneShotCount = shots;
			coneAtk = true;
		}
		else if (coneShotCount > 0) {
			if (projectileType == 1) {
				coneShotCount--;
				Bullet bu = new Bullet(cornerX, cornerY, left, right, top, bottom, bounce);
				double xdist = playerX-x;
				double ydist = playerY-y;
				double m = ydist/xdist;
				double angle = Math.toDegrees(Math.atan(m));
				double endx = 0;
				double endy = 0;
				if (angle < 0) {
					angle += 360;
				}
				if (xdist < 0) {
					angle += 180;
				}
				angle += (Math.random()*anglewidth) - anglewidth/2;

				double xcycle = 10 * Math.cos(Math.toRadians(angle));
				double ycycle = 10 * Math.sin(Math.toRadians(angle));

				bu.setXSpeed(xcycle);
				bu.setYSpeed(ycycle);
				bu.color = color;
				bullet.add(bu);
			}
			else {
				coneShotCount--;
				double xdist = playerX-x;
				double ydist = playerY-y;
				double m = ydist/xdist;
				double angle = Math.toDegrees(Math.atan(m));
				double endx = 0;
				double endy = 0;
				if (angle < 0) {
					angle += 360;
				}
				if (xdist < 0) {
					angle += 180;
				}
				angle += (Math.random()*anglewidth) - anglewidth/2;

				endx = x + 10000 * Math.cos(Math.toRadians(angle));
				endy = y + 10000 * Math.sin(Math.toRadians(angle));

				Laser l = new Laser(x,y,endx, endy, 20, this.color);
				laser.add(l);
			}
			//cone attack
		}
		else {
			coneAtk = false;
		}
	}

	/**
	 * makes a laser attack at the player
	 * @param playerX the x location of the player
	 * @param playerY the y location of the player
	 */
	public void atk3(double playerX, double playerY) {
		laserAtk = true;
		double xdist = playerX- x;
		double ydist = playerY- y;
		double laserSlope = ydist/xdist;
		double angle = Math.toDegrees(Math.atan(laserSlope));
		double laserEndXValue = 0;
		double laserEndYValue = 0;
		if (angle < 0) {
			angle += 360;
		}
		if (xdist < 0) {
			angle += 180;
		}
		laserEndXValue = x + 10000 * Math.cos(Math.toRadians(angle));
		laserEndYValue = y + 10000 * Math.sin(Math.toRadians(angle));

		Laser l = new Laser(x,y,laserEndXValue, laserEndYValue, 20, this.color);
		laser.add(l);

	}

	/**
	 * sets the boss to move in a random direction
	 */
	public void move1() {
		//random movement
		xSpeed = (speed - (Math.random() * 20));
		ySpeed = (speed - (Math.random() * 20));
	}
}
