package culminating;

public class Coordinates {

    private double x;
    private double y;

    public Coordinates(double x, double y) {
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
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    

}
