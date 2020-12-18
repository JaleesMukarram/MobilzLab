package com.learning.mobilzlab.Utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.learning.mobilzlab.R;

import java.util.Timer;
import java.util.TimerTask;

public class SliderUtils {

    public static final String TAG = "HomeScreenTAGSlider";


    public static final int MAX_SIZE = 3;
    public static final int MIN_SIZE = 1;

    private int currentIndex;

    private String text1;
    private String text2;
    private String text3;


    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;

    private ImageView imageShowing;
    private ImageView leftScroll;
    private ImageView rightScroll;

    private TextView mainTextShowing;

    private Activity homeScreen;
    private View view;

    public SliderUtils(Activity homeScreen, View view) {

        this.homeScreen = homeScreen;
        this.view = view;


        initializeComponents();
        initializeListeners();
        setTimer();

    }


    public void initializeComponents() {

        this.text1 = homeScreen.getString(R.string.slider_1);
        this.text2 = homeScreen.getString(R.string.slider_2);
        this.text3 = homeScreen.getString(R.string.slider_3);


        this.drawable1 = ContextCompat.getDrawable(homeScreen, R.drawable.slider_1);
        this.drawable2 = ContextCompat.getDrawable(homeScreen, R.drawable.slider_2);
        this.drawable3 = ContextCompat.getDrawable(homeScreen, R.drawable.slider_3);

        this.imageShowing = view.findViewById(R.id.IVImageSliderSliderImage);

        this.rightScroll = view.findViewById(R.id.IVmageSliderRightSlider);
        this.leftScroll = view.findViewById(R.id.IVmageSliderLeftSlider);

        this.mainTextShowing = view.findViewById(R.id.TVImageSliderMainText);


    }

    private void initializeListeners() {

        this.rightScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                simulateImageSliding(true);
            }
        });

        this.leftScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                simulateImageSliding(false);
            }
        });


    }

    private void setTimer() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                homeScreen.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simulateImageSliding(true);
                        Log.d(TAG, "CALLED");
                    }
                });
            }
        }, 5000, 5000);

    }

    private void simulateImageSliding(boolean next) {

        if (next) {

            currentIndex++;
            if (currentIndex > MAX_SIZE) {
                currentIndex = MIN_SIZE;
            }

            Animation leftAnimation = AnimationUtils.loadAnimation(homeScreen,R.anim.left_animation);
            imageShowing.setAnimation(leftAnimation);

        }

        else {

            currentIndex--;
            if (currentIndex < MIN_SIZE) {
                currentIndex = MAX_SIZE;
            }

            Animation rightAnimation = AnimationUtils.loadAnimation(homeScreen,R.anim.right_animation);
            imageShowing.setAnimation(rightAnimation);

        }




        switch (currentIndex) {

            case 1:
                setFirstSlider();
                break;

            case 2:
                setSecondSlider();
                break;

            default:
                setThirdSlider();
        }


    }

    private void setFirstSlider() {

        this.imageShowing.setImageDrawable(drawable1);
        this.mainTextShowing.setText(text1);


    }

    private void setSecondSlider() {

        this.imageShowing.setImageDrawable(drawable2);
        this.mainTextShowing.setText(text2);

    }

    private void setThirdSlider() {

        this.imageShowing.setImageDrawable(drawable3);
        this.mainTextShowing.setText(text3);

    }

}
