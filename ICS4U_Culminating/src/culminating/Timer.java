package culminating;

/**
 * Timer.java
 * This class will store data and create a timer.
 * June 18, 2018
 */
public class Timer {

    private long currentTime;
    private long oldTime;

    public Timer() {

    }

    public long getOldTime() {
        return oldTime;
    }

    public long getCurrentTime() {
        return currentTime = System.currentTimeMillis();
    }

    public void startTimer() {
        oldTime = System.currentTimeMillis();
    }

    /**
     * @param seconds the number of seconds elapsed.
     * @return
     */
    public boolean hasBeenSeconds(int seconds) {
        getCurrentTime();
        if (currentTime - oldTime > seconds * 1000) {
            return true;
        }
        return false;
    }

}
