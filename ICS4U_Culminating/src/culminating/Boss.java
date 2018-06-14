package culminating;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
		health = new Health(100);
		switch 	(stgNum) {
		case 1: color = Color.PURPLE;
		speed = 10;
		/*try {
			Clip theme = AudioSystem.getClip();
			theme.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Aladdin - Genesis - Boss Tune.wav"))); //opens the given file for the clip
			theme.start();
			theme.stop();
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		return;
		case 2: color = Color.BLUE;
		/*try {
			Clip theme = AudioSystem.getClip();
			theme.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Aladdin - Genesis - Boss Tune.wav"))); //opens the given file for the clip
			theme.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
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
	public void draw(GraphicsContext gc) {
		cx = playerLocationX - radius;
		cy = playerLocationY - radius;
		int drawX = (int) cx;
		int drawY = (int) cy;
		gc.setFill(color);
		gc.fillOval(drawX, drawY, radius * 2, radius * 2);
		//gc.fillText("" + coneCount, 10, 30);
		//gc.fillText("" + coneAtk, 10, 50);

		//				gc.setStroke(color.BLACK);
		//				gc.setLineWidth(5);
		//				gc.strokeLine(playerLocationX, playerLocationY, playerLocationX + 10000, playerLocationY);
		//				gc.strokeLine(playerLocationX, playerLocationY, playerLocationX - 10000, playerLocationY);
		//				gc.strokeLine(playerLocationX, playerLocationY, playerLocationX, playerLocationY + 10000);
		//				gc.strokeLine(playerLocationX, playerLocationY, playerLocationX, playerLocationY - 10000);



	}

	public void behavior(double playerX, double playerY) {
		timeCount++;
		atkBehavior(playerX, playerY);		
//		movtBehavior(playerX, playerY);

	}

	public void movtBehavior(double playerX, double playerY) {
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
				//sniper
				if (timeCount%100 == 0) {
					move1();
				}			return;
			case 4:
				//charger
				if (timeCount%100 == 0) {
					move1();
				}			return;
			case 5:
				//slow mover
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
					atk2(2, playerX, playerY, 20, 10, true);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(2, playerX, playerY, 20, 10, true);
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
					atk2(2, playerX, playerY, 20, 10, true);	
				}

			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(1, playerX, playerY, 20, 10, true);
			}
			if (timeCount%15 == 0 && coneAtk) {
				atk2(2, playerX, playerY, 20, 10, true);
			}
			if (timeCount%300 == 0) {
				atk3(playerX, playerY);
			}
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

	public void atk1(double angle, boolean bounce) {
		//balls in many directions
		for (int i = 0; i < 360; i++) {
			if (i % angle == 0) {
				Bullet bu = new Bullet(playerLocationX, playerLocationY, left, right, top, bottom, bounce);

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
		else if (coneCount > 0) {
			if (projectileType == 1) {
				coneCount--;
				Bullet bu = new Bullet(cx, cy, left, right, top, bottom, bounce);
				double xdist = tx-playerLocationX;
				double ydist = ty-playerLocationY;
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
				double xdist = tx-playerLocationX;
				double ydist = ty-playerLocationY;
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

				endx = playerLocationX + 10000 * Math.cos(Math.toRadians(angle));
				endy = playerLocationY + 10000 * Math.sin(Math.toRadians(angle));

				Laser l = new Laser(playerLocationX,playerLocationY,endx, endy, 20, this.color);
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
		double xdist = tx- playerLocationX;
		double ydist = ty- playerLocationY;
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
		endx = playerLocationX + 10000 * Math.cos(Math.toRadians(angle));
		endy = playerLocationY + 10000 * Math.sin(Math.toRadians(angle));

		Laser l = new Laser(playerLocationX,playerLocationY,endx, endy, 20, this.color);
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
