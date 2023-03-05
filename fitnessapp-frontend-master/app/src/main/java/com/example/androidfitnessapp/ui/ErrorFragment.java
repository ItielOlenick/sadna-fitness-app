package com.example.androidfitnessapp.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.androidfitnessapp.R;

public class ErrorFragment extends DialogFragment
{

    private String errorMsg;

    public ErrorFragment(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_window_error, container, false);
        TextView text = rootView.findViewById(R.id.text_error);
        text.setText("ERROR: " + errorMsg);

        getDialog().setTitle("ERROR!");
        rootView.getBackground().setColorFilter(getResources().getColor(R.color.colorDim), PorterDuff.Mode.MULTIPLY);

        return rootView;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }


}


