package com.example.androidfitnessapp.ui.adapters.workouts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidfitnessapp.R;
import com.example.androidfitnessapp.data.WorkoutData;

import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<SummarisedWorkoutData> workouts;
    private List<SummarisedWorkoutData> sampleRoutines;
    private WorkoutsButtonsListener listener;
    public WorkoutsAdapter(List<SummarisedWorkoutData> data, List<SummarisedWorkoutData> sampleRoutines, WorkoutsButtonsListener listener)
    {
        this.workouts = data;
        this.sampleRoutines = sampleRoutines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == WorkoutCellType.Workout.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_workout, parent, false);
            return new WorkoutsViewHolder(view);

        }
        if(viewType == WorkoutCellType.Sample.cellType())
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sample_routine, parent, false);
            return new SampleViewHolder(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_start_empty_workout, parent, false);
            return new WorkoutsButtonsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof WorkoutsViewHolder)
        {
            ((WorkoutsViewHolder)holder).bind(workouts.get(position - 1), listener);
        }
        if(holder instanceof SampleViewHolder)
        {
            ((SampleViewHolder) holder).bind(sampleRoutines.get(position - workouts.size() -1), listener);
        }
        else if (holder instanceof WorkoutsButtonsViewHolder)
        {
            ((WorkoutsButtonsViewHolder)holder).bind(listener);
        }
    }

    @Override
    public int getItemCount()
    {
        return this.workouts.size() + sampleRoutines.size()+ 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return WorkoutCellType.Buttons.cellType();
        }
        else if(position <= workouts.size())
        {
            return WorkoutCellType.Workout.cellType();
        }
        else
        {
            return WorkoutCellType.Sample.cellType();
        }
    }

    public static class WorkoutsViewHolder extends RecyclerView.ViewHolder
    {
        public WorkoutsViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private final TextView textWorkoutName = itemView.findViewById(R.id.text_workout_name);
        private ImageButton editWorkoutButton = itemView.findViewById(R.id.btn_edit_workout);
        private final RecyclerView exerciseList = itemView.findViewById(R.id.log_exercise_recycler_view);

        private void bind(SummarisedWorkoutData data, WorkoutsButtonsListener listener)
        {
            View.OnClickListener clickListener = new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    listener.onStartActiveWorkout(data.getData());
                }
            };

            textWorkoutName.setText(data.getName());
            exerciseList.setAdapter(new ExerciseListAdapter(data.exercises, clickListener));

            itemView.setOnClickListener(clickListener);
            /*itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onStartActiveWorkout(data.getData());
                    //listener.onStartActiveWorkoutButtonPressed(data.getId());
                }
            });
*/
            editWorkoutButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    showEditPopup(view, data.getId(), listener);
                }
            });

        }

        private void showEditPopup(View view, int id, WorkoutsButtonsListener listener)
        {
            Context context = view.getContext();
            PopupWindow popup = new PopupWindow(context);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window_edit_workout, null);
            popup.setContentView(popupView);
            popup.setOutsideTouchable(true);
            popup.showAsDropDown(view);

            Button editButton = popupView.findViewById(R.id.btn_edit);
            Button deleteButton = popupView.findViewById(R.id.btn_delete);

            editButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("EDIT", "onClick: ");
                    listener.onCreateWorkoutTemplateButtonPressed(id);
                    popup.dismiss();
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View button)
                {
                    Log.d("DELETE", "onClick: ");
                    listener.onDeleteWorkoutButtonPressed(view, id);
                    popup.dismiss();
                }
            });

        }
    }


    public static class SampleViewHolder extends RecyclerView.ViewHolder
    {
        public SampleViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private final TextView textWorkoutName = itemView.findViewById(R.id.text_workout_name);
        private final RecyclerView exerciseList = itemView.findViewById(R.id.log_exercise_recycler_view);

        private void bind(SummarisedWorkoutData data, WorkoutsButtonsListener listener)
        {
            textWorkoutName.setText(data.getName());

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    listener.onStartActiveWorkout(data.getData());
                }
            };
            exerciseList.setAdapter(new ExerciseListAdapter(data.exercises, clickListener));

            itemView.setOnClickListener(clickListener);
            /*itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onStartActiveWorkout(data.getData());
                    //listener.onStartActiveWorkoutButtonPressed(data.getId());
                }
            });*/
        }
    }

    public static class WorkoutsButtonsViewHolder extends RecyclerView.ViewHolder {

        public WorkoutsButtonsViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        private Button activeWorkoutButton = itemView.findViewById(R.id.btn_empty_workout);
        private Button workoutTemplateButton = itemView.findViewById(R.id.btn_create_routine);

        private void bind(WorkoutsButtonsListener listener)
        {
            activeWorkoutButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onStartActiveWorkout(new WorkoutData());

                    //listener.onStartActiveWorkoutButtonPressed(-1);
                }
            });

            workoutTemplateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    listener.onStartWorkoutTemplate(new WorkoutData());
                    //listener.onCreateWorkoutTemplateButtonPressed(-1);
                }
            });
        }
    }
}
