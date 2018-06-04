package culminating;

public class Timer {

    private long currentTime;
    private long oldTime;

    public Timer() {
        oldTime = System.currentTimeMillis();
    }

    public long getOldTime() {
        return oldTime;
    }

    public long getCurrentTime() {
        return currentTime = System.currentTimeMillis();
    }

    public boolean hasBeenSeconds(int seconds) {
        getCurrentTime();
        if (currentTime - oldTime > seconds * 1000) {
            return true;
        }
        return false;
    }

}
