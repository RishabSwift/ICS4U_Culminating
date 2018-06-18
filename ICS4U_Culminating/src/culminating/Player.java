package culminating;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Player.java
 * This class will store data and create a player.
 * June 18, 2018
 */
public class Player extends MovingObject {

    int base;
    double[] xPoints = new double[3];
    double[] yPoints = new double[3];
    double mouseLocationX;
    double mouseLocationY;
    double playerWidth = 7.5;
    double playerHeight = 30;
    boolean dead = false;
    Health health;

    /**
     * Constructs a new Player that stores information.
     *
     * @param x      Initial x position.
     * @param y      Initial y position.
     * @param left   Left edge for bouncing.
     * @param right  Right edge for bouncing.
     * @param top    Top edge for bouncing.
     * @param bottom Bottom edge for bouncing.
     */
    public Player(double x, double y, int left, int right, int top, int bottom, boolean bounce) {
        super(x, y, left, right, top, bottom, bounce);
        health = new Health(100);
        xPoints[0] = x - 5;
        xPoints[1] = x;
        xPoints[2] = x + 5;

        yPoints[0] = y;
        yPoints[1] = y + 20;
        yPoints[2] = y;

        dead = false;

    }

    int ptNum = 3;


    @Override
    /**
     * draws the player's triangle character
     */
    public void draw(GraphicsContext gc) {

        double xdiff = mouseLocationX - x;
        double ydiff = mouseLocationY - y;

        double angle = Math.atan(ydiff / xdiff);


        if (xdiff == 0) {
            xPoints[0] = x - playerWidth;
            xPoints[1] = x;
            xPoints[2] = x + playerWidth;

            yPoints[0] = y;
            yPoints[1] = y + playerHeight;
            yPoints[2] = y;
        }
        //cartesian quadrant 1
        if (xdiff > 0 && ydiff < 0) {

            xPoints[0] = x - playerWidth * Math.cos(angle + (Math.PI / 2));
            xPoints[1] = x + playerHeight * Math.cos(angle);
            xPoints[2] = x + playerWidth * Math.cos(angle + (Math.PI / 2));

            yPoints[0] = y - playerWidth * Math.sin(angle + (Math.PI / 2));
            yPoints[1] = y + playerHeight * Math.sin(angle);
            yPoints[2] = y + playerWidth * Math.sin(angle + (Math.PI / 2));
        }
        //cartesian quadrant 4
        if (xdiff > 0 && ydiff > 0) {

            xPoints[0] = x + playerWidth * Math.cos(angle - (Math.PI / 2));
            xPoints[1] = x + playerHeight * Math.cos(angle);
            xPoints[2] = x + playerWidth * Math.cos(angle + (Math.PI / 2));

            yPoints[0] = y + playerWidth * Math.sin(angle - (Math.PI / 2));
            yPoints[1] = y + playerHeight * Math.sin(angle);
            yPoints[2] = y + playerWidth * Math.sin(angle + (Math.PI / 2));
        }
        //cartesian quadrant 2
        if (xdiff < 0 && ydiff < 0) {

            xPoints[0] = x + playerWidth * Math.cos(angle + (-Math.PI / 2));
            xPoints[1] = x - playerHeight * Math.cos(angle);
            xPoints[2] = x + playerWidth * Math.cos(angle + (Math.PI / 2));

            yPoints[0] = y + playerWidth * Math.sin(angle + (-Math.PI / 2));
            yPoints[1] = y - playerHeight * Math.sin(angle);
            yPoints[2] = y + playerWidth * Math.sin(angle + (Math.PI / 2));
        }
        //cartesian quadrant 3
        if (xdiff < 0 && ydiff > 0) {

            xPoints[0] = x + playerWidth * Math.cos(angle + (Math.PI / 2));
            xPoints[1] = x - playerHeight * Math.cos(angle);
            xPoints[2] = x - playerWidth * Math.cos(angle + (Math.PI / 2));

            yPoints[0] = y + playerWidth * Math.sin(angle + (Math.PI / 2));
            yPoints[1] = y - playerHeight * Math.sin(angle);
            yPoints[2] = y - playerWidth * Math.sin(angle + (Math.PI / 2));
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
