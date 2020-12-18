package com.learning.mobilzlab.Utils;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.learning.mobilzlab.Activities.HomeScreen;
import com.learning.mobilzlab.HomeFragments.ViewPager2Adapter;
import com.learning.mobilzlab.R;

public class NavigationUtils {

    public static final String TAG = "HomeScreenTAG2";


    private int SELECTED_COLOR;
    private int UNSELECTED_COLOR;

    private LinearLayout Home;
    private LinearLayout Services;
    private LinearLayout AboutUs;
    private LinearLayout ContactUs;

    private TextView textHome;
    private TextView textServices;
    private TextView textAboutUs;
    private TextView textContactUs;

    private ImageView imageHome;
    private ImageView imageServices;
    private ImageView imageAboutUs;
    private ImageView imageContactUs;


    private AppCompatActivity homeScreen;
    private ViewPager2 viewPager2;

    public NavigationUtils(HomeScreen homeScreen, ViewPager2 viewPager2) {

        this.homeScreen = homeScreen;
        this.viewPager2 = viewPager2;

        this.SELECTED_COLOR = homeScreen.getColor(R.color.colorPrimary);
        this.UNSELECTED_COLOR = homeScreen.getColor(R.color.colorSecondPrimary);

        initializeComponents();
        initializeListeners();
    }

    // Initialize
    private void initializeComponents() {

        Home = homeScreen.findViewById(R.id.LLBottomMenuHomeContainer);
        Services = homeScreen.findViewById(R.id.LLBottomMenuServicesContainer);
        AboutUs = homeScreen.findViewById(R.id.LLBottomMenuAboutUsContainer);
        ContactUs = homeScreen.findViewById(R.id.LLBottomMenuContactUsContainer);

        textHome = homeScreen.findViewById(R.id.TVBottomMenuHome);
        textServices = homeScreen.findViewById(R.id.TVBottomMenuServices);
        textAboutUs = homeScreen.findViewById(R.id.TVBottomMenuAboutUs);
        textContactUs = homeScreen.findViewById(R.id.TVBottomMenuContactUs);

        imageHome = homeScreen.findViewById(R.id.IVBottomMenuHome);
        imageServices = homeScreen.findViewById(R.id.IVBottomMenuServices);
        imageAboutUs = homeScreen.findViewById(R.id.IVBottomMenuAboutUs);
        imageContactUs = homeScreen.findViewById(R.id.IVBottomMenuContactUs);

    }

    private void initializeListeners(){


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager2.setCurrentItem(0);

            }
        });

        Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager2.setCurrentItem(1);

            }
        });

        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager2.setCurrentItem(2);

            }
        });

        ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager2.setCurrentItem(3);

            }
        });
    }

    // Changes
    public void onPageSelected(int position) {

        Log.d(TAG, "Position from " + position);

        unSelectAllImages();
        unSelectAllTexts();

        selectThisPosition(position);

    }

    private void selectThisPosition(int position){

        switch (position){

            case 0:
                selectHome();
                break;
            case 1:
                selectServices();
                break;
            case 2:
                selectAboutUs();
                break;
            default:
                selectContactUs();

        }
    }


    // Particular Selection
    private void selectHome(){

        textHome.setTextColor(SELECTED_COLOR);
        imageHome.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_home_selected));

    }

    private void selectServices(){

        textServices.setTextColor(SELECTED_COLOR);
        imageServices.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_our_services_selected));


    }

    private void selectAboutUs(){

        textAboutUs.setTextColor(SELECTED_COLOR);
        imageAboutUs.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_about_us_selected));

    }

    private void selectContactUs(){

        textContactUs.setTextColor(SELECTED_COLOR);
        imageContactUs.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_contact_us_selected));


    }

    // UnSelection
    private void unSelectAllTexts() {

        textHome.setTextColor(UNSELECTED_COLOR);
        textServices.setTextColor(UNSELECTED_COLOR);
        textAboutUs.setTextColor(UNSELECTED_COLOR);
        textContactUs.setTextColor(UNSELECTED_COLOR);

    }

    private void unSelectAllImages() {

        imageHome.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_home_un_selected));
        imageServices.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_our_services_un_selected));
        imageAboutUs.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_about_us_un_selected));
        imageContactUs.setImageDrawable(ContextCompat.getDrawable(homeScreen, R.drawable.ic_contact_us_un_selected));

    }



}
