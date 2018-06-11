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
	

	public Boss(double x, double y, int left, int right, int top, int bottom, boolean bounce) {
		super(x, y, left, right, top, bottom, bounce);
		radius = 30;
		this.cx = x - radius;
		this.cy = y - radius;
		health = new Health(100);
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
	}

	public void behavior(double playerX, double playerY) {
		timeCount++;
		if (timeCount == 100) {
			atkBehavior(playerX, playerY);		
			timeCount = 0;
			//movtBehavior(playerX, playerY);
		}
	}

	public void movtBehavior(double playerX, double playerY) {
		switch 	(stgNum) {
		case 1:
			move1();
			//standard move and attack
			return;
		case 2:
			//bouncer
			move1();
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
			atk3(playerX, playerY);
			//atk2(1, playerX, playerY, false);
			//standard attack
			return;
		case 2:
			//bounce balls
			atk1(45, true);
			return;
		case 3:
			//destroy wall attack
			return;
		case 4:
			//charger

			return;
		case 5:
			//area of effect
			return;
		case 6:
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

	public void atk2(int projectileType, double tx, double ty, boolean bounce) {
		if (projectileType == 1) {
			
			Bullet bu = new Bullet(cx, cy, left, right, top, bottom, bounce);
			double xdist = tx-x;
			double ydist = ty-y;
			double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
			int cycleNum = (int) dist/10;
			bu.setXSpeed(xdist/cycleNum);
			bu.setYSpeed(ydist/cycleNum);
			bu.color = color;
			bullet.add(bu);
			
			bu = new Bullet(cx, cy, left, right, top, bottom, bounce);
			xdist = tx-x + 20;
			ydist = ty-y;
			dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
			cycleNum = (int) dist/10;
			bu.setXSpeed(xdist/cycleNum);
			bu.setYSpeed(ydist/cycleNum);
			bu.color = color;
			bullet.add(bu);
			
			bu = new Bullet(cx, cy, left, right, top, bottom, bounce);
			xdist = tx-x - 20;
			ydist = ty-y;
			dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
			cycleNum = (int) dist/10;
			bu.setXSpeed(xdist/cycleNum);
			bu.setYSpeed(ydist/cycleNum);
			bu.color = color;
			bullet.add(bu);
			
		}
		else {

		}
		//cone attack
	}
	public void atk3(double tx, double ty) {
		//line laser
		double xdist = tx-x;
		double ydist = ty-y;
		double m = ydist/xdist;
		double k = 0;
		boolean edge = false;
		while (!edge) {
			if (k * xdist <=  0 || k * xdist >=  right) {
				edge = true;
			}
			else {
				k++;
			}
		}
		
		Laser l = new Laser(x,y,x + (xdist), y + + (ydist), 20, this.color);
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
