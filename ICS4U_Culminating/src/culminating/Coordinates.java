package culminating;

/**
 * Coordinates.java
 * This class will store data and create a coordinates object.
 * June 18, 2018
 */
public class Coordinates {

    private double x;
    private double y;

    public Coordinates(double x, double y) {
        super();
        this.setX(x);
        this.setY(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


}
