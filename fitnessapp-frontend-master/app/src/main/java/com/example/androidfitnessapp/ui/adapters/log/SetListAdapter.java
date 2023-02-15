package com.example.androidfitnessapp.ui.adapters.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfitnessapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SetListAdapter extends RecyclerView.Adapter
{
    private String exerciseName;
    private List<String> sets = new ArrayList<>();
    private Context context;

    public SetListAdapter(Context context, List<String> sets)
    {
        this.sets = sets;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sets, parent, false);
        return new SetListViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof SetListViewHolder)
        {
            ((SetListViewHolder) holder).bind(sets.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        return sets.size();
    }

    public static class SetListViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        public SetListViewHolder(Context context, @NonNull View itemView)
        {
            super(itemView);
            this.context = context;
        }

        private final TextView textSet = itemView.findViewById(R.id.text_set);

        private void bind(String set)
        {
            textSet.setText(set);
        }
    }

}
