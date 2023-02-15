package com.example.androidfitnessapp.ui.exercises;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.androidfitnessapp.R;

public class CreateExerciseFragment extends DialogFragment {

    private ExerciseButtonsListener listener;

    public CreateExerciseFragment(ExerciseButtonsListener listener)
    {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_window_add_exercise, container, false);
        Button saveButton = rootView.findViewById(R.id.btn_cancel_delete_exercise);
        EditText etExName = rootView.findViewById(R.id.et_exercise_name);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.onAddConfirmButtonClicked(etExName.getText().toString());
                dismiss();
            }
        });

        getDialog().setTitle("Create new Exercise");
        rootView.getBackground().setColorFilter(getResources().getColor(R.color.colorDim), PorterDuff.Mode.MULTIPLY);

        return rootView;
    }


}


