package culminating;

/**
 * Health.java
 * This class will store health for the boss and player.
 * June 18, 2018
 */
public class Health {

    int health;

    public Health(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    /**
     * Set the health to a number
     *
     * @param h
     */
    public void setHealth(int h) {
        health = h;
    }

    /**
     * Decrease health
     *
     * @param h
     */
    public void decrease(int h) {
        health -= h;
    }

    /**
     * If the health is 0, then the player / boss is dead
     *
     * @return
     */
    public boolean isDead() {
        if (health <= 0) {
            return true;
        } else {
            return false;
        }
    }
}


