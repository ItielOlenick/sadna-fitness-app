package com.example.androidfitnessapp.ui.adapters.log;

public enum LogCellType
{
    Header,
    Exercise,
    Notes;

    public int cellType()
    {
        return ordinal();
    }
}
