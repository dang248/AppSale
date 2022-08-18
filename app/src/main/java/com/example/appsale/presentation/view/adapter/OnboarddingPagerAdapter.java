package com.example.appsale.presentation.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appsale.presentation.view.fragment.onboard.EatHealthyFragment;
import com.example.appsale.presentation.view.fragment.onboard.HealthyRecipesFragment;

public class OnboarddingPagerAdapter extends FragmentStateAdapter {
    private Fragment eatHealthyFragment, healthyRecipesFragment;

    public OnboarddingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0:
               return  new EatHealthyFragment();
           default:
               return  new HealthyRecipesFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
