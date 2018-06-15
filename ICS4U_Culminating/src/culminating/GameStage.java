package culminating;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameStage extends Stage {

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
	Player player;
	Boss boss;
	Barrier barrier = new Barrier();
	boolean finished = false;
	boolean isBarrierCreated = false, isBarrierBeingCreated = false;

	ProgressBar bossHealthProgress;
	ProgressBar playerHealthProgress;

	Clip opening = null, boss1 = null, boss2 = null, boss3 = null, boss4 = null, boss5 = null, boss6 = null;

	Timer t = new Timer();

	BossProgressTaskService bossHealthBossProgressTaskService;
	PlayerProgressTaskService playerHealthBossProgressTaskService;

	GameStage() {

		this.setTitle(MainApp.GAME_NAME);
		this.setResizable(false);

		Group group = new Group();
		Canvas canvas = new Canvas(MainApp.GAME_WIDTH, MainApp.GAME_HEIGHT);

		final GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);


		startGame(canvas, gc);

		bossHealthProgress = new ProgressBar(100);
		playerHealthProgress = new ProgressBar(100);

		bossHealthProgress.setLayoutX(50);
		bossHealthProgress.setLayoutY(10);
		bossHealthProgress.setMinWidth(150);
		Text bossHealthText = new Text(80, 45, "Enemy Health");

		playerHealthProgress.setMinWidth(150);
		playerHealthProgress.setLayoutX(MainApp.GAME_WIDTH - 180);
		playerHealthProgress.setLayoutY(10);
		Text playerHealthText = new Text(MainApp.GAME_WIDTH - 150, 45, "Your Health");


		group.getChildren().addAll(canvas, bossHealthProgress, playerHealthProgress, bossHealthText, playerHealthText);

		bossHealthProgress.progressProperty().unbind();
		playerHealthProgress.progressProperty().unbind();

		bossHealthBossProgressTaskService = new BossProgressTaskService();
		bossHealthProgress.progressProperty().bind(bossHealthBossProgressTaskService.progressProperty());
		playerHealthBossProgressTaskService = new PlayerProgressTaskService();
		playerHealthProgress.progressProperty().bind(playerHealthBossProgressTaskService.progressProperty());


		this.setScene(new Scene(group));
		this.show();
		bossHealthBossProgressTaskService.start();
		playerHealthBossProgressTaskService.start();

	}

	public void startGame(Canvas canvas, GraphicsContext gc) {

		try {
			//opening = AudioSystem.getClip();
			//opening.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/Aladdin - Genesis - Boss Tune.wav"))); //opens the given file for the clip
			//opening.start();
			boss1 = AudioSystem.getClip();
			boss1.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/sonic_boss_theme.wav"))); //opens the given file for the clip
			boss1.start();
			boss2 = AudioSystem.getClip();
			boss2.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/aladdin_boss_theme.wav")));
			boss3 = AudioSystem.getClip();
			boss3.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/zelda_boss_theme.wav")));
			boss4 = AudioSystem.getClip();
			boss4.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/smash_boss_theme.wav")));
			boss5 = AudioSystem.getClip();
			boss5.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/pokemon_boss_theme.wav")));
			boss6 = AudioSystem.getClip();
			boss6.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/opening2.wav")));
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

		//create the boss
		boss = new Boss(900, 500, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), true);
		//Create the Player
		player = new Player(500, 500, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), true);

		//moves the player based on the key pressed
		canvas.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.W) {
				player.setYSpeed(-10);
			}
			if (event.getCode() == KeyCode.A) {
				player.setXSpeed(-10);
			}
			if (event.getCode() == KeyCode.S) {
				player.setYSpeed(10);
			}
			if (event.getCode() == KeyCode.D) {
				player.setXSpeed(10);
			}
		});

		//stops the player's movemen when the corresponding key is released
		canvas.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.W) {
				player.setYSpeed(0);
			}
			if (event.getCode() == KeyCode.A) {
				player.setXSpeed(0);
			}
			if (event.getCode() == KeyCode.S) {
				player.setYSpeed(0);
			}
			if (event.getCode() == KeyCode.D) {
				player.setXSpeed(0);
			}
		});

		//changes the coordinants of the mouse in the canvas when it is moved.
		canvas.setOnMouseMoved(event -> {
			player.mouseLocationX = event.getX();
			player.mouseLocationY = event.getY();
		});

		/**
		 * player attacks
		 */
		canvas.setOnMouseClicked(event -> {
			if (pbullet.size() < 5) {
				try {
					Clip theme = AudioSystem.getClip();
					theme.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/fire2.wav"))); //opens the given file for the clip
					theme.start();

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				PBullet bl = new PBullet(player.x, player.y, 0, (int) canvas.getWidth(), 0, (int) canvas.getHeight(), false);
				double xdist = event.getX() - player.x;
				double ydist = event.getY() - player.y;
				double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
				int cycleNum = (int) dist / 10;
				bl.setXSpeed(xdist / cycleNum);
				bl.setYSpeed(ydist / cycleNum);
				bl.color = Color.BLACK;
				pbullet.add(bl);
			}
		});

		/**
		 * create barrier
		 */
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
			 * Runs the game
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
					if (player.health.isDead()) {
						finished = true;
					}
					if (boss.health.isDead()) {
						if (boss.stgNum == 6) {
							finished = true;
						} else {
							resetStg();
							pauseDuration = 8;
						}
					}
					if (boss.stgNum == 4) {
						player.setColor(Color.WHITE);
					} else {
						player.setColor(Color.BLACK);
					}
					for (int i = 0; i < boss.bullet.size(); i++) {
						if (boss.bullet.get(i).edge) {
							boss.bullet.remove(i);
						}
					}
					for (int i = 0; i < pbullet.size(); i++) {
						if (pbullet.get(i).edge) {
							pbullet.remove(i);
						}
					}
					draw(gc);
					hitDetection();
					for (int i = 0; i < boss.laser.size(); i++) {
						if (boss.laser.get(i).hasFired) {
							boss.laser.remove(i);
						}
					}
					boss.behavior(player.x, player.y);
					if (player.health.isDead() || boss.health.isDead()) {
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

	/**
	 * resets the stage for next level
	 */
	public void resetStg() {
		for (int i = 0; i < pbullet.size(); i++) {
			pbullet.remove(i);
		}

		boss.nextLv();
		boss.health.setHealth(100);
		boss.setX(100);
		boss.setY(100);
		player.setX(500);
		player.setX(500);
		//        if (boss.stgNum == 2) {
		//            boss1.stop();
		//            boss2.start();
		//        } else if (boss.stgNum == 3) {
		//            boss2.stop();
		//            boss3.start();
		//        } else if (boss.stgNum == 4) {
		//            boss3.stop();
		//            boss4.start();
		//        } else if (boss.stgNum == 5) {
		//            boss4.stop();
		//            boss5.start();
		//        } else if (boss.stgNum == 6) {
		//            boss5.stop();
		//            boss6.start();
		//        }

		resetBossHealth();
	}

	/**
	 * resets the boss health
	 */
	public void resetBossHealth() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				bossHealthBossProgressTaskService.restart();
			}
		});
	}

	/**
	 * detects whether the player or the boss has been hit by an attack
	 */
	public void hitDetection() {
		//TODO Rishab change the hit detection to fit graphics
		// player bullet hits boss
		for (int i = 0; i < pbullet.size(); i++) {
			double xdif = pbullet.get(i).cx - boss.x;
			double ydif = pbullet.get(i).cy - boss.y;
			double rSum = pbullet.get(i).radius + boss.radius;
			if (Math.abs(xdif) <= rSum && Math.abs(ydif) <= rSum) {
				pbullet.remove(i);
				try {
					Clip theme = AudioSystem.getClip();
					theme.open(AudioSystem.getAudioInputStream(new File("src/assets/sounds/sfx_damage_hit10.wav"))); //opens the given file for the clip
					theme.start();

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boss.health.decrease(10);
			}
		}
		//laser hits player
		for (int i = 0; i < boss.laser.size(); i++) {
			if (boss.laser.get(i).hasFired) {
				double rise = boss.laser.get(i).y2 - boss.laser.get(i).y1;
				double run = boss.laser.get(i).x2 - boss.laser.get(i).x1;
				double nXDirection = -rise;
				double nYDirection = run;
				double dist = (Math.abs((boss.laser.get(i).x1 - player.xPoints[0]) * nXDirection +
						(boss.laser.get(i).y1 - player.yPoints[0]) * nYDirection))
						/ (Math.sqrt(Math.pow(nXDirection, 2) + Math.pow(nYDirection, 2)));

				//System.out.println(dist);
				if (dist < boss.laser.get(i).laserAttackWidth) {
					boss.laser.remove(i);
					player.health.decrease(5);
				} else {
					dist = (Math.abs((boss.laser.get(i).x1 + player.xPoints[1]) * nXDirection +
							(boss.laser.get(i).y1 + player.yPoints[1]) * nYDirection))
							/ (Math.sqrt(Math.pow(nXDirection, 2) + Math.pow(nYDirection, 2)));
					if (dist < boss.laser.get(i).laserAttackWidth) {
						boss.laser.remove(i);
						player.health.decrease(5);
					} else {
						dist = (Math.abs((boss.laser.get(i).x1 + player.xPoints[2]) * nXDirection +
								(boss.laser.get(i).y1 + player.yPoints[2]) * nYDirection))
								/ (Math.sqrt(Math.pow(nXDirection, 2) + Math.pow(nYDirection, 2)));
						if (dist < boss.laser.get(i).laserAttackWidth) {
							boss.laser.remove(i);
							player.health.decrease(5);
						}
					}
				}
			}
		}

		// boss bullet hits player
		for (int i = 0; i < boss.bullet.size(); i++) {
			double xdif = boss.bullet.get(i).x - player.xPoints[0];
			double ydif = boss.bullet.get(i).y - player.yPoints[0];
			double rad = boss.bullet.get(i).radius;
			if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
				player.health.decrease(10);
				boss.bullet.remove(i);
			}
			xdif = boss.bullet.get(i).x - player.xPoints[1];
			ydif = boss.bullet.get(i).y - player.yPoints[1];
			if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
				player.health.decrease(10);
				boss.bullet.remove(i);
			}
			xdif = boss.bullet.get(i).x - player.xPoints[2];
			ydif = boss.bullet.get(i).y - player.yPoints[2];
			if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
				player.health.decrease(10);
				boss.bullet.remove(i);
			}
		}

		//player hits boss
		double xdif = boss.x - player.xPoints[0];
		double ydif = boss.y - player.yPoints[0];
		double rad = boss.radius;
		if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
			player.health.decrease(10);
		}
		xdif = boss.x - player.xPoints[1];
		ydif = boss.y - player.yPoints[1];
		if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
			player.health.decrease(10);
		}
		xdif = boss.x - player.xPoints[2];
		ydif = boss.y - player.yPoints[2];
		if (Math.abs(xdif) <= rad && Math.abs(ydif) <= rad) {
			player.health.decrease(10);
		}
	}

	/**
	 * Clears the screen and paints the balls.
	 */
	public void draw(GraphicsContext gc) {
		if (player.health.isDead()) {
			fillScreenWithText("You Died", gc);
			try {
				Thread.sleep(2000);
				Platform.runLater(() -> {
					this.close();
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (boss.health.isDead()) {
			fillScreenWithText("Stage Won", gc);
		} else {
			if (boss.stgNum == 4) {
				gc.setFill(Color.BLACK);
			} else {
				gc.setFill(Color.WHITE);
			}
			gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
			gc.setFill(Color.BLACK);
			Font small = new Font(10);
			gc.setFont(small);
			//gc.fillText("" + + player.health.getHealth(), 10, 10);
			//gc.fillText("Use 'W,A,S,D' and the mouse to move the triangle", 10, 30);
			//gc.fillText("You die if a bullet hits you in any place other than the tip", 10, 50);

			for (int i = 0; i < boss.laser.size(); i++) {
				boss.laser.get(i).draw(gc);
			}
			for (int i = 0; i < numBalls; i++) {
				bullet[i].draw(gc);
			}
			for (int i = 0; i < boss.bullet.size(); i++) {
				boss.bullet.get(i).draw(gc);
			}
			for (int i = 0; i < pbullet.size(); i++) {

				if (boss.stgNum == 4) {
					pbullet.get(i).draw(gc, Color.WHITE);
				} else {
					pbullet.get(i).draw(gc);
				}
			}
			boss.draw(gc);
			player.draw(gc);
			barrier.draw(gc);
			gc.drawImage(buffer, 0, 0); // double buffering
		}
	}


	private void fillScreenWithText(String text, GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		gc.setFill(Color.WHITE);
		Font big = new Font(80);
		gc.setFont(big);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(text, MainApp.GAME_WIDTH / 2, MainApp.GAME_HEIGHT / 2);
	}

	private class BossProgressTaskService extends Service<Void> {

		@Override
		protected Task<Void> createTask() {
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {

					while (boss.health.getHealth() > 0) {
						if (finished) {
							return null;
						}
						updateProgress(boss.health.getHealth(), 100);
					}

					return null;

				}
			};
			return task;
		}
	}

	private class PlayerProgressTaskService extends Service<Void> {

		@Override
		protected Task<Void> createTask() {
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {

					while (player.health.getHealth() > 0) {
						if (finished) {
							return null;
						}
						updateProgress(player.health.getHealth(), 100);
					}

					return null;

				}
			};
			return task;
		}
	}
}
