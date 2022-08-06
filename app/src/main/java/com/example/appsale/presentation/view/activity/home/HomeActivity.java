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
import com.example.appsale.data.model.Order;
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
    TextView tvCountCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControls();
        observerData();
        events();
    }

    private void observerData() {
        homeViewModel.getFoods().observe(this, new Observer<AppResource<List<Food>>>() {
            @Override
            public void onChanged(AppResource<List<Food>> foodAppResource) {
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
            }
        });
        homeViewModel.getOrder().observe(this, (Observer<AppResource<Order>>) orderAppResource -> {
            switch (orderAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    int quantities = getQuantity(orderAppResource.data == null ? null :  orderAppResource.data.getFoods());
                    setupBadge(quantities);
                    break;
                case ERROR:
                    Toast.makeText(HomeActivity.this, orderAppResource.message, Toast.LENGTH_SHORT).show();
                    layoutLoading.setVisibility(View.GONE);
                    break;
            }
        });

    }
    private void events() {
        homeViewModel.fetchFoods();
        foodAdapter.setOnItemClickFood(position -> homeViewModel.fetchOrder(foodAdapter.getListFoods().get(position).getId()));
    }

    private void addControls() {
        layoutLoading = findViewById(R.id.layout_loading);
        toolBar = findViewById(R.id.toolbar_home);
        toolBar.setTitle("Food");
        toolBar.setTitleTextColor(getResources().getColor(R.color.primary, null));
        setSupportActionBar(toolBar);

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(HomeActivity.this);
            }
        }).get(HomeViewModel.class);
        foodAdapter = new FoodAdapter();

        // Setup RecyclerView
        rcvFood = findViewById(R.id.recycler_view_food);
        rcvFood.setAdapter(foodAdapter);
        rcvFood.setHasFixedSize(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        tvCountCart = actionView.findViewById(R.id.text_cart_badge);
        setupBadge(0);
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getQuantity(List<Food> listFoods) {
        if (listFoods == null || listFoods.size() == 0) {
            return 0;
        }
        int totalQuantities = 0;
        for (Food food: listFoods) {
            totalQuantities += food.getQuantity();
        }
        return totalQuantities;
    }

    private void setupBadge(int quantities) {
        if (quantities == 0) {
            tvCountCart.setVisibility(View.GONE);
        } else {
            tvCountCart.setVisibility(View.VISIBLE);
            tvCountCart.setText(String.valueOf(Math.min(quantities, 99)));
        }
    }
}
