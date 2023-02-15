package com.example.androidfitnessapp.ui.adapters.activeWorkout;

import android.os.CountDownTimer;

public class Timer
{
    private TimerListener listener;
    private long duration;
    private long timeLeft;
    private static final long interval = 1000;
    private boolean isRunning = false;
    private CountDownTimer timer;

    public Timer(TimerListener listener, long duration)
    {
        this.listener = listener;
        this.duration = duration;
        this.timeLeft = duration;
        listener.onTick(duration);
    }

    public void start()
    {
        timer = createTimer(timeLeft).start();
        isRunning = true;
    }

    public void pause()
    {
        if(timer != null)
            timer.cancel();
        isRunning = false;
    }

    public void reset()
    {
        if(isRunning)
            timer.cancel();

        timer = createTimer(duration);
        timeLeft = duration;
        listener.onTick(duration);

        if(isRunning)
            timer.start();
    }


    private CountDownTimer createTimer(long duration)
    {
        return new CountDownTimer(duration, interval)
        {
            @Override
            public void onTick(long l)
            {
                timeLeft = l;
                listener.onTick(l);
            }

            @Override
            public void onFinish()
            {
                listener.onTimeOut();
            }
        };
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }
}
