package presenter.gameplay;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private int secondsTotal;

    public void start() {
        timer = new Timer();
        secondsTotal = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsTotal++;
            }
        }, 1000, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public int getSecondsConverted() {
        int seconds = secondsTotal;
        if (getMinutesConverted() > 0) seconds = secondsTotal - 60 * getMinutesConverted();
        return seconds;
    }

    public int getMinutesConverted() {
        if (getHours() > 0) return getHours() - 60 * secondsTotal / 60;
        return secondsTotal / 60;
    }

    public int getHours() {
        return secondsTotal / 3600;
    }

    public int getHours(int seconds) {
        return seconds / 3600;
    }

    public int getMinutes(int seconds, int hours) {
        if (hours > 0) {
            return hours - 60 * seconds / 60;
        }
        return seconds / 60;
    }

    public int getSeconds(int seconds, int minutes) {
        if (minutes > 0) return seconds - 60 * minutes;
        return seconds;
    }

    public int getSecondsTotal() {
        return secondsTotal;
    }
}