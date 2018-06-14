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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameStage extends Stage {
	Clip opening = null, boss1 = null, boss2 = null, boss3 = null, boss4 = null, boss5 = null, boss6 = null;
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
			
			try {
				//opening = AudioSystem.getClip();
				//opening.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Aladdin - Genesis - Boss Tune.wav"))); //opens the given file for the clip
				//opening.start();
				boss1 = AudioSystem.getClip();
				boss1.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Sonic the Hedgehog 1 - Boss Theme.wav"))); //opens the given file for the clip
				boss1.start();
				boss2 = AudioSystem.getClip();
				boss2.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Aladdin - Genesis - Boss Tune.wav")));
				boss3 = AudioSystem.getClip();
				boss3.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Zelda - A Link to the Past - Boss Theme.wav")));
				boss4 = AudioSystem.getClip();
				boss4.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Super Smash Bros Brawl - Battlefield - Melee.wav")));
				boss5 = AudioSystem.getClip();
				boss5.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Pokemon Conquest - Boss Battle.wav")));
				boss6 = AudioSystem.getClip();
				boss6.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Super Smash Bros Melee Orchestra - Planet Corneria.wav")));
			} catch (LineUnavailableException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
            	try {
					Clip theme = AudioSystem.getClip();
					theme.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Fire 2.wav"))); //opens the given file for the clip
					theme.start();

				} catch (Exception e1) {
					e1.printStackTrace();
				}
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

            Coordinates c = new Coordinates(event.getX(), event.getY());

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
					if (p.health.isDead()) {
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
						p.setColor(Color.WHITE);
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
					for (int i = 0; i < b.laser.size(); i++) {
						if (b.laser.get(i).fire) {
							b.laser.remove(i);
						}
					}
					b.behavior(p.playerLocationX, p.playerLocationY);
					if (p.health.isDead() || b.health.isDead()) {
						draw(gc);
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
        if (b.stgNum == 2) {
        	boss1.stop();
        	boss2.start();
        }
        else if (b.stgNum == 3) {
        	boss2.stop();
        	boss3.start();
        }
        else if (b.stgNum == 4) {
        	boss3.stop();
        	boss4.start();
        }
        else if (b.stgNum == 5) {
        	boss4.stop();
        	boss5.start();
        }
        else if (b.stgNum == 6) {
        	boss5.stop();
        	boss6.start();
        }

    }


	public void hitDetection() {
		//TODO Rishab change the hit detection to fit graphics
		// player bullet hits boss
		for (int i = 0; i < pbullet.size(); i++) {
			double xdif = pbullet.get(i).cx - b.playerLocationX;
			double ydif = pbullet.get(i).cy - b.playerLocationY;
			double rSum = pbullet.get(i).radius + b.radius;
			if (Math.abs(xdif) <= rSum && Math.abs(ydif) <= rSum) {
				pbullet.remove(i);
                try {
                    Clip theme = AudioSystem.getClip();
                    theme.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/sfx_damage_hit10.wav"))); //opens the given file for the clip
                    theme.start();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
				b.health.decrease(10);
			}
		}
		//laser hits player 
		for (int i = 0; i < b.laser.size(); i++) {
			if (b.laser.get(i).fire) {
				double rise = b.laser.get(i).y2 -  b.laser.get(i).y1;
				double run = b.laser.get(i).x2 -  b.laser.get(i).x1;
				double nXDirection = -rise;
				double nYDirection = run;
				double dist = (Math.abs((b.laser.get(i).x1 - p.xPoints[0]) * nXDirection + 
						(b.laser.get(i).y1 - p.yPoints[0]) * nYDirection)) 
						/(Math.sqrt(Math.pow(nXDirection, 2) + Math.pow(nYDirection, 2)));

				//System.out.println(dist);
				if (dist < b.laser.get(i).atkWidth) {
					b.laser.remove(i);
					p.health.decrease(5);
				}
				else {
					dist = (Math.abs((b.laser.get(i).x1 + p.xPoints[1]) * nXDirection + 
							(b.laser.get(i).y1 + p.yPoints[1]) * nYDirection)) 
							/(Math.sqrt(Math.pow(nXDirection, 2) + Math.pow(nYDirection, 2)));
					if (dist < b.laser.get(i).atkWidth) {
						b.laser.remove(i);
						p.health.decrease(5);
					}
					else {
						dist = (Math.abs((b.laser.get(i).x1 + p.xPoints[2]) * nXDirection + 
								(b.laser.get(i).y1 + p.yPoints[2]) * nYDirection)) 
								/(Math.sqrt(Math.pow(nXDirection, 2) + Math.pow(nYDirection, 2)));
						if (dist < b.laser.get(i).atkWidth) {
							b.laser.remove(i);
							p.health.decrease(5);
						}
					}
				}
			}
		}

        // boss bullet hits player
        for (int i = 0; i < b.bullet.size(); i++) {
            double xdif = b.bullet.get(i).playerLocationX - p.xPoints[0];
            double ydif = b.bullet.get(i).playerLocationY - p.yPoints[0];
            double rad = b.bullet.get(i).radius;
            if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
                p.health.decrease(10);
                b.bullet.remove(i);
            }
            xdif = b.bullet.get(i).playerLocationX - p.xPoints[1];
            ydif = b.bullet.get(i).playerLocationY - p.yPoints[1];
            if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
                p.health.decrease(10);
                b.bullet.remove(i);
            }
            xdif = b.bullet.get(i).playerLocationX - p.xPoints[2];
            ydif = b.bullet.get(i).playerLocationY - p.yPoints[2];
            if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
                p.health.decrease(10);
                b.bullet.remove(i);
            }
        }

        //player hits boss
        double xdif = b.playerLocationX - p.xPoints[0];
        double ydif = b.playerLocationY - p.yPoints[0];
        double rad = b.radius;
        if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
            p.health.decrease(10);
        }
        xdif = b.playerLocationX - p.xPoints[1];
        ydif = b.playerLocationY - p.yPoints[1];
        if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
            p.health.decrease(10);
        }
        xdif = b.playerLocationX - p.xPoints[2];
        ydif = b.playerLocationY - p.yPoints[2];
        if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
            p.health.decrease(10);
        }
    }

	/**
	 * Clears the screen and paints the balls.
	 */
	public void draw(GraphicsContext gc) {
		if (p.health.isDead()) {
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
			Font small = new Font(10);
			gc.setFont(small);
			//gc.fillText("" + + p.health.getHealth(), 10, 10);
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
