package culminating;

public class Coordinates {

    private double x;
    private double y;
    private int timer;

    public Coordinates(double x, double y, int timer) {
        super();
        this.setX(x);
        this.setY(y);
        //this.setTimer(timer);
			/*if (timer < 5) {
				xCoordinates.add(x);
				yCoordinates.add(y);
			}
			else {
				Barrier barrier = new Barrier (xCoordinates, yCoordinates);
				xCoordinates.clear();
				yCoordinates.clear();
				//barrier.draw(gc);
			}

			*/
    }

    public void setX (double x) {
        this.x = x;
    }
    public void setY (double y) {
        this.y = y;
    }
    public void setTimer (int timer) {
        this.timer = timer;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getTimer() {
        return timer;
    }

}
