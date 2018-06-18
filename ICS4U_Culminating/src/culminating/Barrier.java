package culminating;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Barrier.java
 * This class will store data and create a barrier.
 * June 18, 2018
 */
public class Barrier {

    public ArrayList<Coordinates> c = new ArrayList<Coordinates>();
    GraphicsContext gc;

    public Barrier() {

    }

    /**
     * Draws the barrier.
     *
     * @param gc - the graphics context to allow drawing
     */
    public void draw(GraphicsContext gc) {
        this.gc = gc;
        gc.setStroke(Color.RED);
        for (int i = 0; i < c.size() - 1; i++) {
            gc.strokeLine(c.get(i).getX(), c.get(i).getY(), c.get(i + 1).getX(), c.get(i + 1).getY());
        }
        gc.setStroke(Color.BLACK);

    }

    /**
     * Clears the barrier.
     */
    public void clear() {
        for (int i = 0; i < c.size() - 1; i++) {
            gc.setStroke(Color.WHITE); //White colour erases the barrier.
            gc.strokeLine(c.get(i).getX(), c.get(i).getY(), c.get(i + 1).getX(), c.get(i + 1).getY());
            c.clear();
        }
    }
}
