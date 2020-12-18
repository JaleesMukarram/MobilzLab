package com.learning.mobilzlab.Activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.learning.mobilzlab.HomeFragments.ViewPager2Adapter;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.NavigationUtils;

public class HomeScreen extends AppCompatActivity implements ActivityCustomHooks {

    public static final String TAG = "HomeScreenTAG";

    private ViewPager2 mViewPager2;
    private ViewPager2Adapter mAdapter;
    private NavigationUtils mNavUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        callHooks();

    }

    @Override
    public void callHooks() {

        handleIntent();
        initializeViews();
        initializeListeners();
        startProcessing();

    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void initializeViews() {

        mViewPager2 = findViewById(R.id.VPHomeScreenViewPagerFragments);
        mAdapter = new ViewPager2Adapter(this);
        mNavUtils = new NavigationUtils(this, mViewPager2);

        mViewPager2.setAdapter(mAdapter);


    }

    @Override
    public void initializeListeners() {

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                mNavUtils.onPageSelected(position);
            }
        });


    }

    @Override
    public void startProcessing() {

    }


}