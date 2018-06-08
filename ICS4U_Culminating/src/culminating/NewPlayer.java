package culminating;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class NewPlayer  implements  Runnable{

    int base;
    double[] xPoints = new double[3];
    double[] yPoints = new double[3];
    double mouseLocationX;
    double mouseLocationY;
    double shrt = 7.5;
    double lng = 30;
    boolean dead = false;



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



    public Image image() {
        return new Image(getClass().getResourceAsStream("/assets/images/car.png"));
    }


    public NewPlayer(double x, double y, int left, int right, int top, int bottom, boolean bounce) {

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

        xPoints[0] =x- 5;
        xPoints[1] =x;
        xPoints[2] =x+ 5;

        yPoints[0] =y;
        yPoints[1] =y+ 20;
        yPoints[2] =y;

        dead = false;

    }

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
     * Starts the movement thread.
     */
    public void startThread() {
        moving = true;
        Thread t = new Thread(this);
        t.start();
    }


    @Override
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
}
