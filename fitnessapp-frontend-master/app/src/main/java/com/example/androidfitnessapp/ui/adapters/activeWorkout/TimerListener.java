package com.example.androidfitnessapp.ui.adapters.activeWorkout;

import com.example.androidfitnessapp.ui.activeWorkout.SetTimerListener;

public interface TimerListener
{
    void onTimeOut();
    void onSetTimeButtonPressed(SetTimerListener setTimerListener);
    void onTick(long l);
}
