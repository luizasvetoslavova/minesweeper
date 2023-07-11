package presenter.gameplay;

import model.levels.Easy;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private int seconds;

    public void start() {
        timer = new Timer();
        seconds = 0;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
            }
        }, 1000, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public int getSeconds() {
        return seconds;
    }
}
