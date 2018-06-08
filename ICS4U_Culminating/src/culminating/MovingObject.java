package culminating;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Superclass for a generic moving object. This class takes care of the playerLocationX and playerLocationY
 * location (should be the centre of the object), the playerLocationX and playerLocationY speed, the color,
 * and the top, bottom, left, and right edges of the screen. <br>
 * <br>
 * 
 * The class implements a thread that updates the objects' position every 10 ms
 * (by default). At each step, the playerLocationX and playerLocationY position are updated based on the playerLocationX
 * and playerLocationY speed. Then collisions with the edge of the screen are checked, and the
 * playerLocationX and playerLocationY speeds are reversed if necessary.<br>
 * <br>
 * 
 * The class also calls animateOneStep() for each update. If the object designer
 * wishes, they can put code in this method that controls the appearance of the
 * object, causing it to morph in some way as it moves.<br>
 * <br>
 * 
 * <b>Requirements:</b> Object designers must create a subclass of
 * MovingObject. They must make use the MovingObject constructor (or no thread
 * will start). They must implement the draw() and animateOneStep() methods.<br>
 * <br>
 * 
 * December 9, 2007.
 * 
 * @author Sam Scott
 */
public abstract class MovingObject implements Runnable {

	/**
	 * The playerLocationX location of the object.
	 */
	double playerLocationX;
	/**
	 * The playerLocationY location of the object.
	 */
	double playerLocationY;
	/**
	 * The playerLocationX speed of the object.
	 */
	double xSpeed;
	/**
	 * The playerLocationY speed of the object.
	 */
	double ySpeed;
	/**
	 * The left edge for bouncing.
	 */
	int left;
	/**
	 * The right edge for bouncing.
	 */
	int right;
	/**
	 * The top edge for bouncing.
	 */
	int top;
	/**
	 * The bottom edge for bouncing.
	 */
	int bottom;
	/**
	 * Length of pause between position updates. Related to speed of object.
	 * (Defaults to 10).
	 */
	int pauseDuration;
	/**
	 * Object color (defaults to black)
	 */
	Color color;
	/**
	 * Set to false to stop the thread.
	 */
	boolean moving;

	boolean bounce;
	boolean edge;

	/**
	 * Sets default color and pauseDuration values. Sets speed to 0. Starts
	 * thread. Every subclass of MovingObject must use this constructor.
	 * 
	 * @param x
	 *            Initial playerLocationX position.
	 * @param y
	 *            Initial playerLocationY position.
	 * @param left
	 *            Left edge for bouncing.
	 * @param right
	 *            Right edge for bouncing.
	 * @param top
	 *            Top edge for bouncing.
	 * @param bottom
	 *            Bottom edge for bouncing.
	 */
	public MovingObject(double x, double y, int left, int right, int top,
			int bottom, boolean bounce) {
		this.pauseDuration = 40;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.color = Color.BLACK;
		this.playerLocationX = x;
		this.playerLocationY = y;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.bounce = bounce;
		startThread();
	}

	/**
	 * Starts the movement thread.
	 */
	public void startThread() {
		moving = true;
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Stops the movement thread by terminating the main loop in run().
	 */
	public void stopThread() {
		moving = false;
	}

	/**
	 * Updates the playerLocationX and playerLocationY values in an infinite loop. If object hits an edge, playerLocationX
	 * or playerLocationY speed is reversed as appropriate.
	 */
	public void run() {
		while (moving) {
			playerLocationX += xSpeed;
			playerLocationY += ySpeed;
			if (playerLocationX >= right | playerLocationX <= left) {
				if (bounce) {
					xSpeed *= -1;
				}
				else {
					edge = true;
				}
			}
			if (playerLocationY >= bottom | playerLocationY <= top) {
				if (bounce) {
					ySpeed *= -1;
				}
				else {
					edge = true;
				}
			}
			try {
				Thread.sleep(pauseDuration);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Draws the object.
	 * 
	 * @param g
	 *            The graphics context
	 */
	abstract public void draw(GraphicsContext gc);

	/**
	 * Performs one step of animation.
	 */
	abstract public void animateOneStep();

	/**
	 * Sets the playerLocationX speed.
	 * 
	 * @param xSpeed
	 *            New playerLocationX speed.
	 */
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * Sets the playerLocationY speed.
	 * 
	 * @param ySpeed
	 *            New playerLocationY speed.
	 */
	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * Sets the playerLocationX location.
	 * 
	 * @param x
	 *            New playerLocationX location.
	 */
	public void setX(int x) {
		this.playerLocationX = x;
	}

	/**
	 * Sets the playerLocationY location.
	 * 
	 * @param y
	 *            New playerLocationY location.
	 */
	public void setY(int y) {
		this.playerLocationY = y;
	}

	/**
	 * Sets color of object.
	 * 
	 * @param color
	 *            New color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
