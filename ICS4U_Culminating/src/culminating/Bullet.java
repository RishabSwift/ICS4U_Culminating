package culminating;

import javafx.scene.canvas.GraphicsContext;

/**
 * This class implements a flashing bullet. Feel free to change this, implement a
 * new object, change the animation, etc.
 */
public class Bullet extends MovingObject {

    /**
     * The radius of the bullet.
     */
    int radius;
    /**
     * Counts the frames between flash on/off.
     */
    int counter;
    /**
     * The number of frames to wait before toggling the flash.
     */
    int flashSpeed;
    /**
     * Controls the flash - true if the bullet is filled in, false if it is an
     * outline.
     */
    boolean filledIn;


    /**
     * Calls the superclass constructor, plus sets radius, and flash parameters.
     *
     * @param x      The x location.
     * @param y      The y location.
     * @param left   The left edge.
     * @param right  The right edge.
     * @param top    The top edge.
     * @param bottom The bottom edge.
     */
    public Bullet(double x, double y, int left, int right, int top, int bottom, boolean bounce) {
        super(x, y, left + 10, right - 10, top + 10, bottom - 10, bounce);
        // numbers above must match the radius
        radius = 10;
        counter = 0;
    }

    /**
     * Controls the animation parameters. (Called once every time the bullet
     * position is updated.)
     */
    public void animateOneStep() {
    }

    /**
     * Draws the bullet
     *
     * @param g The graphics context.
     */
    public void draw(GraphicsContext gc) {
        int drawX = (int) x - radius;
        int drawY = (int) y - radius;
        gc.setFill(color);
        gc.fillOval(drawX, drawY, radius * 2, radius * 2);
    }
}
