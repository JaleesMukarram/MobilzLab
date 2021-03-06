package com.learning.mobilzlab.HomeFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {


    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0:
                return new HomeFragment();
            case 1:
                return new ServicesFragment();
            case 2:
                return new AboutUsFragment();
            default:
                return new ContactUsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}