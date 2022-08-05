package com.example.appsale.presentation.view.activity.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale.R;
import com.example.appsale.data.model.Food;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.presentation.view.activity.home.HomeViewModel;
import com.example.appsale.presentation.view.adapter.FoodAdapter;


import java.util.List;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    RecyclerView rcvFood;
    LinearLayout layoutLoading;
    FoodAdapter foodAdapter;
    Toolbar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControls();
        observerData();
        events();
    }

    private void observerData() {
        homeViewModel.getFoods().observe(this, foodAppResource -> {
            switch (foodAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    foodAdapter.updateListProduct(foodAppResource.data);
                    break;
                case ERROR:
                    Toast.makeText(HomeActivity.this, foodAppResource.message, Toast.LENGTH_SHORT).show();
                    layoutLoading.setVisibility(View.GONE);
                    break;
            }
        });
    }
    private void events() {
        homeViewModel.fetchFoods();
    }

    private void addControls() {
        layoutLoading = findViewById(R.id.layout_loading);

        toolBar = findViewById(R.id.toolbar_home);
        toolBar.setTitle("Food");
        toolBar.setTitleTextColor(getResources().getColor(R.color.primary));

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel();
            }
        }).get(HomeViewModel.class);
        foodAdapter = new FoodAdapter();

        // Setup RecyclerView
        rcvFood = findViewById(R.id.recycler_view_food);
        rcvFood.setAdapter(foodAdapter);
        rcvFood.setHasFixedSize(true);
    }
}
