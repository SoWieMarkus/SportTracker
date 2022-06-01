package markus.wieland.sporttracker.helper;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimer extends Timer {

    private static final long TIME_INTERVAL = 1000;
    private long currentTime;
    private final CountUpListener countUpListener;
    private Timer timer;

    public CountUpTimer (CountUpListener countUpListener) {
        this.countUpListener = countUpListener;
        currentTime = 0;
    }

    public void start(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentTime += TIME_INTERVAL;
                countUpListener.onCountUpSecond(currentTime);
            }
        }, 0,1000L);
    }

    public void stop(){
        timer.cancel();
    }

}
