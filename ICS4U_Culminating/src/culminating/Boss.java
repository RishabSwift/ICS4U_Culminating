package culminating;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Boss extends MovingObject {

	int stgNum = 1;
	int radius;
	double cx;
	double cy;
	double speed;
	Health health;
	boolean attacking;
	ArrayList<Bullet> bullet = new  ArrayList<Bullet>();
	ArrayList<Laser> laser = new  ArrayList<Laser>();
	int timeCount = 0;
	int coneCount = 0;
	boolean laserAtk = false;
	boolean coneAtk = false;

	public Boss(double x, double y, int left, int right, int top, int bottom, boolean bounce) {
		super(x, y, left, right, top, bottom, bounce);
		radius = 30;
		this.cx = x - radius;
		this.cy = y - radius;
		health = new Health(10);
		switch 	(stgNum) {
		case 1: color = Color.PURPLE;
		speed = 10;
		return;
		case 2: color = Color.BLUE;
		speed = 10;
		return;
		case 3: color = Color.GREEN;
		speed = 10;
		return;
		case 4: color = Color.YELLOW;
		speed = 30;
		return;
		case 5: color = Color.ORANGE;
		speed = 5;
		return;
		case 6: color = Color.RED;
		speed = 10;
		return;
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		cx = x - radius;
		cy = y - radius;
		int drawX = (int) cx;
		int drawY = (int) cy;
		gc.setFill(color);
		gc.fillOval(drawX, drawY, radius * 2, radius * 2);

		//		gc.setStroke(color.BLACK);
		//		gc.setLineWidth(5);
		//		gc.strokeLine(x, y, x + 10000, y);
		//		gc.strokeLine(x, y, x - 10000, y);
		//		gc.strokeLine(x, y, x, y + 10000);
		//		gc.strokeLine(x, y, x, y - 10000);



	}

	public void behavior(double playerX, double playerY) {
		timeCount++;
		atkBehavior(playerX, playerY);		
		//movtBehavior(playerX, playerY);
	}

	public void movtBehavior(double playerX, double playerY) {
		if (laserAtk || coneAtk) {
			xSpeed = 0;
			ySpeed = 0;
		}
		switch 	(stgNum) {
		case 1:
			move1();
			//standard move and attack
			return;
		case 2:
			//bouncer
			//move1();
			return;
		case 3:
			//sniper
			return;
		case 4:
			//charger
			return;
		case 5:
			//slow mover
			return;
		case 6: color = Color.RED;
		}
	}

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
			//	atk1(45, true);
			}
			return;
		case 3:
			if (timeCount%100 == 0) {
			//	atk1(45, true);
				if (timeCount%3 == 0 && !coneAtk) {
					atk2(2, playerX, playerY, 20, 10, false);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(2, playerX, playerY, 20, 10, false);
			}
			return;
		case 4:
			if (timeCount%100 == 0) {
				atk1(45, true);
				if (timeCount%3 == 0 && !coneAtk) {
					atk2(2, playerX, playerY, 20, 10, false);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(2, playerX, playerY, 20, 10, false);
			}
			if (timeCount%300 == 0) {
				atk3(playerX, playerY);
			}
			return;
		case 5:
			atk1(45, true);
			atk2(2, playerX, playerY, 5, 20, false);
			atk3(playerX, playerY);
			return;
		case 6:
			atk1(45, true);
			atk2(1, playerX, playerY, 5, 20, true);
			atk2(2, playerX, playerY, 5, 20, false);
			atk3(playerX, playerY);
			return;
		}
	}

	@Override
	public void animateOneStep() {
		//TODO boss moving graphics

	}

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
		health.setHealth(10);
		return;
		case 3: color = Color.GREEN;
		health.setHealth(10);
		return;
		case 4: color = Color.YELLOW;
		health.setHealth(10);
		return;
		case 5: color = Color.ORANGE;
		health.setHealth(10);
		return;
		case 6: color = Color.RED;
		health.setHealth(10);
		return;
		}
	}

	public void atk1(double angle, boolean bounce) {
		//balls in many directions
		for (int i = 0; i < 360; i++) {
			if (i % angle == 0) {
				Bullet bu = new Bullet(cx, cy, left, right, top, bottom, bounce);

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

	public void atk2(int projectileType, double tx, double ty, int shots, double anglewidth, boolean bounce) {
		if (!coneAtk) {
			coneCount = shots;
			coneAtk = true;
		}
		if (coneCount > 0) {
			if (projectileType == 1) {
				coneCount--;
				Bullet bu = new Bullet(cx, cy, left, right, top, bottom, bounce);
				double xdist = tx-x;
				double ydist = ty-y;
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
				coneCount--;
				double xdist = tx-x;
				double ydist = ty-y;
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
	public void atk3(double tx, double ty) {
		laserAtk = true;
		//line laser
		double xdist = tx-x;
		double ydist = ty-y;
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
		endx = x + 10000 * Math.cos(Math.toRadians(angle));
		endy = y + 10000 * Math.sin(Math.toRadians(angle));

		Laser l = new Laser(x,y,endx, endy, 20, this.color);
		laser.add(l);

	}


	public void move1() {
		//random movement
		xSpeed = (speed - (Math.random() * 20));
		ySpeed = (speed - (Math.random() * 20));
	}
	public void move2() {
		//farther
	}

	public void move3() {
		//closer

	}
	public void move4() {
		//dodge
	}
	public void move5() {
		//teleport
	}
}
