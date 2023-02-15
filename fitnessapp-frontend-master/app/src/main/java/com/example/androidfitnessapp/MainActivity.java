package com.example.androidfitnessapp;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidfitnessapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private boolean isSelectedByUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_logs, R.id.navigation_workouts, R.id.navigation_exercises, R.id.navigation_profile).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle)
            {
                int id = navDestination.getId();
                if(isSelectedByUser)
                {
                    isSelectedByUser = false;

                    if(id == R.id.navigation_active_workout || id == R.id.navigation_workouts || id == R.id.navigation_workout_template || id == R.id.navigation_exercise_picker1 || id == R.id.navigation_exercise_picker_2)
                    {
                        navView.setSelectedItemId(R.id.navigation_workouts);
                    }
                    else if(id == R.id.navigation_logs || id == R.id.navigation_log)
                    {
                        navView.setSelectedItemId(R.id.navigation_logs);
                    }
                    else if(id == R.id.navigation_exercises)
                    {
                        navView.setSelectedItemId(R.id.navigation_exercises);

                    }
                    else if(id == R.id.navigation_profile)
                    {
                        navView.setSelectedItemId(R.id.navigation_profile);
                    }
                }
                isSelectedByUser = true;
            }
        });

        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if(isSelectedByUser)
                {
                    isSelectedByUser = false;
                    navController.navigate(item.getItemId());
                }
                return true;
            }
        });


        //NavigationUI.setupWithNavController(navView, navController);



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        boolean isOnLowLevelDest = !appBarConfiguration.getTopLevelDestinations().contains(navController.getCurrentDestination().getId());
        if (isOnLowLevelDest)
        {
            navController.navigateUp();
            return true;
        }
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}