package culminating;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameStage extends Stage {

    Timer _timer;

	/**
	 * Buffer for double buffering.
	 */
	Image buffer;
	/**
	 * The number of balls on the screen.
	 */
	final int numBalls = 0;
	/**
	 * The pause between repainting (should be set for about 30 frames per
	 * second).
	 */
	int pauseDuration = 8;
	/**
	 * An array of balls.
	 */
	//Canvas canvas;
	//GraphicsContext gc;
	int timer = 0;
	long currentTime = 0;
	boolean win = false;
	Bullet[] bullet = new Bullet[numBalls];
	ArrayList<PBullet> pbullet = new ArrayList<PBullet>();
	Player p;
	Boss b;
	Barrier barrier = new Barrier();
	boolean finished = false;
	boolean isBarrierCreated = false, isBarrierBeingCreated = false;

    Timer t = new Timer();

	GameStage() {

		this.setTitle(MainApp.GAME_NAME);
		this.setResizable(false);

		Group group = new Group();
		Canvas canvas = new Canvas(MainApp.GAME_WIDTH, MainApp.GAME_HEIGHT);

		final GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);

		startGame(canvas, gc);

		group.getChildren().addAll(canvas);

		this.setScene(new Scene(group));
		this.show();

	}

	public void startGame(Canvas canvas, GraphicsContext gc) {

		//create the balls for the game
		for (int i = 0; i < numBalls; i++) {
			bullet[i] = new Bullet(50, 50, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), false);
			bullet[i].setXSpeed(Math.random() * 16 - 8);
			bullet[i].setYSpeed(Math.random() * 16 - 8);
			bullet[i].setColor(new Color(Math.random(), Math.random(), Math.random(), 1.0));
		}

		//create the boss
		b = new Boss(900, 500, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), true);

		//Create the Player
		p = new Player(500, 500, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), true);

		//moves the player based on the key pressed
		canvas.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.W) {
				p.setYSpeed(-10);
			}
			if (event.getCode() == KeyCode.A) {
				p.setXSpeed(-10);
			}
			if (event.getCode() == KeyCode.S) {
				p.setYSpeed(10);
			}
			if (event.getCode() == KeyCode.D) {
				p.setXSpeed(10);
			}
		});

		//stops the player's movemen when the corresponding key is released
		canvas.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.W) {
				p.setYSpeed(0);
			}
			if (event.getCode() == KeyCode.A) {
				p.setXSpeed(0);
			}
			if (event.getCode() == KeyCode.S) {
				p.setYSpeed(0);
			}
			if (event.getCode() == KeyCode.D) {
				p.setXSpeed(0);
			}
		});

		//changes the coordinants of the mouse in the canvas when it is moved.
		canvas.setOnMouseMoved(event -> {
			p.mouseLocationX = event.getX();
			p.mouseLocationY = event.getY();
		});

		canvas.setOnMouseClicked(event -> {
			if (pbullet.size() < 5) {
				PBullet bl = new PBullet(p.playerLocationX, p.playerLocationY, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), false);
				double xdist = event.getX() - p.playerLocationX;
				double ydist = event.getY() - p.playerLocationY;
				double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
				int cycleNum = (int) dist / 10;
				bl.setXSpeed(xdist / cycleNum);
				bl.setYSpeed(ydist / cycleNum);
				bl.color = Color.BLACK;
				pbullet.add(bl);
			}
		});



        canvas.setOnMouseDragged(event -> {

            if (isBarrierCreated) {
                return;
            }

            System.out.println(isBarrierBeingCreated);

            Coordinates c = new Coordinates(event.getX(), event.getY(), timer);

            barrier.c.add(c);
            isBarrierBeingCreated = true;

        });
        canvas.setOnMouseReleased(event -> {
            if (!isBarrierCreated && isBarrierBeingCreated) {
                t.startTimer();
                isBarrierCreated = true;
                isBarrierBeingCreated = false;
            }

        });


        new Thread(new Runnable() {
            /**
             * Repaints the canvas periodically.
             */
            @Override
            public void run() {
                while (!finished) {

                    if (isBarrierCreated) {
                        if (t.hasBeenSeconds(3)) {
                            isBarrierCreated = false;
                            isBarrierBeingCreated = false;
                            barrier.clear();
                        }
                    }
                    if (p.dead) {
                        finished = true;
                    }
                    if (b.health.isDead()) {
                        if (b.stgNum == 6) {
                            finished = true;
                        } else {
                            resetStg();
                            pauseDuration = 8;
                        }
                    }
                    if (b.stgNum == 4) {
                        p.color = Color.WHITE;
                        for (int j = 0; j < pbullet.size(); j++) {
                            pbullet.get(j).color = Color.WHITE;
                        }
                    }
                    for (int i = 0; i < b.bullet.size(); i++) {
                        if (b.bullet.get(i).edge) {
                            b.bullet.remove(i);
                        }
                    }
                    for (int i = 0; i < pbullet.size(); i++) {
                        if (pbullet.get(i).edge) {
                            pbullet.remove(i);
                        }
                    }
                    draw(gc);
                    hitDetection();
                    b.behavior(p.playerLocationX, p.playerLocationY);
                    if (p.dead || b.health.isDead()) {
                        draw(gc);
//						if (b.health.isDead()) {
//							resetStg();
//						}
                        pauseDuration = 1000;
                    }
                    try {
                        Thread.sleep(pauseDuration);
                    } catch (InterruptedException e) {
                    }
                }


			}
		}).start();


	}

	public void resetStg() {
		for (int i = 0; i < pbullet.size(); i++) {
			pbullet.remove(i);
		}

		b.nextLv();
		b.health.setHealth(10);
		b.setX(100);
		b.setY(100);
		p.setX(500);
		p.setX(500);

	}

	public void hitDetection() {
		//TODO Rishab change the hit detection to fit graphics
		// player bullet hits boss
		for (int i = 0; i < pbullet.size(); i++) {
			double xdif = pbullet.get(i).cx - b.cx;
			double ydif = pbullet.get(i).cy - b.cy;
			double rSum = pbullet.get(i).radius + b.radius;
			if (Math.abs(xdif) <= rSum && Math.abs(ydif) <= rSum) {
				pbullet.remove(i);
				b.health.decrease(10);
			}
		}
		// boss bullet hits player
		for (int i = 0; i < b.bullet.size(); i++) {
			double xdif = b.bullet.get(i).playerLocationX - p.xPoints[0];
			double ydif = b.bullet.get(i).playerLocationY - p.yPoints[0];
			double rad = b.bullet.get(i).radius;
			if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
				p.setColor(Color.WHITE);
				p.moving = false;
				p.dead = true;
			}
		}
		for (int i = 0; i < b.bullet.size(); i++) {
			double xdif = b.bullet.get(i).playerLocationX - p.xPoints[1];
			double ydif = b.bullet.get(i).playerLocationY - p.yPoints[1];
			double rad = b.bullet.get(i).radius;
			if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
				p.setColor(Color.WHITE);
				p.moving = false;
				p.dead = true;
			}
		}
		for (int i = 0; i < b.bullet.size(); i++) {
			double xdif = b.bullet.get(i).playerLocationX - p.xPoints[2];
			double ydif = b.bullet.get(i).playerLocationY - p.yPoints[2];
			double rad = b.bullet.get(i).radius;
			if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
				p.setColor(Color.WHITE);
				p.moving = false;
				p.dead = true;
			}
		}
		//player hits boss
	}

	/**
	 * Clears the screen and paints the balls.
	 */
	public void draw(GraphicsContext gc) {
		if (p.dead) {
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
			gc.setFill(Color.WHITE);
			Font big = new Font(80);
			gc.setFont(big);
			gc.fillText("You Died", 800, 500);
		} else if (b.health.isDead()) {
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
			gc.setFill(Color.WHITE);
			Font big = new Font(80);
			gc.setFont(big);
			gc.fillText("Stage Won", 800, 500);
		} else {
			if (b.stgNum == 4) {
				gc.setFill(Color.BLACK);
			} else {
				gc.setFill(Color.WHITE);
			}
			gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
			gc.setFill(Color.BLACK);
			gc.fillText("" + finished, 10, 10);
			//gc.fillText("Use 'W,A,S,D' and the mouse to move the triangle", 10, 30);
			//gc.fillText("You die if a bullet hits you in any place other than the tip", 10, 50);


            for (int i = 0; i < b.laser.size(); i++) {
                b.laser.get(i).draw(gc);
            }
            for (int i = 0; i < numBalls; i++) {
                bullet[i].draw(gc);
            }
            for (int i = 0; i < b.bullet.size(); i++) {
                b.bullet.get(i).draw(gc);
            }
            for (int i = 0; i < pbullet.size(); i++) {
                pbullet.get(i).draw(gc);
            }
            b.draw(gc);
            p.draw(gc);
            barrier.draw(gc);
            gc.drawImage(buffer, 0, 0); // double buffering
        }
    }
}
