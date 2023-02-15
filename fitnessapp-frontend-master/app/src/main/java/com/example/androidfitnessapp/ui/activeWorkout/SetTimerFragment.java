package com.example.androidfitnessapp.ui.activeWorkout;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.androidfitnessapp.R;


public class SetTimerFragment extends DialogFragment
{
    private SetTimerListener listener;

    private static final int MAX_HOURS = 23;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_SECONDS = 59;

    public SetTimerFragment(SetTimerListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_window_timer_set, container, false);
        Button setButton = rootView.findViewById(R.id.btn_set);
        EditText etHours = rootView.findViewById(R.id.et_hrs);
        EditText etMins = rootView.findViewById(R.id.et_mins);
        EditText etSecs = rootView.findViewById(R.id.et_secs);

        etHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(etHours.getText().length() > 0)
                {
                    int temp = Integer.parseInt(etHours.getText().toString());
                    if(temp > MAX_HOURS)
                    {
                        etHours.setText("" +MAX_HOURS);
                    }
                    else if(temp < 0)
                    {
                        etHours.setText("" + 0);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });

        etMins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(etMins.getText().length() > 0)
                {
                    int temp = Integer.parseInt(etMins.getText().toString());
                    if(temp > MAX_MINUTES)
                    {
                        etMins.setText(""+MAX_MINUTES);
                    }
                    else if(temp < 0)
                    {
                        etMins.setText("" + 0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etSecs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(etSecs.getText().length() > 0)
                {
                    int temp = Integer.parseInt(etSecs.getText().toString());
                    if(temp > MAX_SECONDS)
                    {
                        etSecs.setText(""+MAX_SECONDS);
                    }
                    else if(temp < 0)
                    {
                        etSecs.setText("" + 0);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hours = 0, mins = 0, secs = 0;

                if (etHours.getText().length() > 0)
                {
                    hours = Integer.parseInt(etHours.getText().toString());
                }
                if (etMins.getText().length() > 0)
                {
                    mins = Integer.parseInt(etMins.getText().toString());
                }
                if (etSecs.getText().length() > 0) {
                    secs = Integer.parseInt(etSecs.getText().toString());
                }
                listener.onSetTime(hours, mins, secs);
                dismiss();
            }
        });

        getDialog().setTitle("Create new Exercise");
        rootView.getBackground().setColorFilter(getResources().getColor(R.color.colorDim), PorterDuff.Mode.MULTIPLY);

        return rootView;
    }
}
