package culminating;

public class Health {

    int health;

    public Health(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int h) {
        health = h;
    }

    public void decrease(int h) {
        health -= h;
    }

    public boolean isDead() {
        if (health <= 0) {
            return true;
        } else {
            return false;
        }
    }
}


