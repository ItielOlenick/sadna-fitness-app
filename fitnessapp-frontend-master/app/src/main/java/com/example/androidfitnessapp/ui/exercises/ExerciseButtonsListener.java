package com.example.androidfitnessapp.ui.exercises;

import android.view.View;

public interface ExerciseButtonsListener
{
    void onAddConfirmButtonClicked(String exName);

    void onEditConfirmButtonClicked(int id, String exName);

    void onAddCustomExerciseButtonClicked();

    void onDeleteButtonClicked(View view, int id);

    void onEditButtonClicked(int id);
}
