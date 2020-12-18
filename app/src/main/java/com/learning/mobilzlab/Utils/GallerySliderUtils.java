package com.learning.mobilzlab.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GallerySliderUtils implements UtilsHooks {

    public static final String TAG = "SliderUtilsTAG";

    private int currentIndex;

    private List<Bitmap> imageBitmapsLists;

    private ImageView imageShowing;
    private View leftSlide;
    private View rightSlide;


    private Activity homeScreen;

    public GallerySliderUtils(Activity homeScreen) {

        this.homeScreen = homeScreen;
        this.imageBitmapsLists = new ArrayList<>();

    }


    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();

    }

    @Override
    public void initializeViews() {

        this.imageShowing = homeScreen.findViewById(R.id.IVImageSliderSliderImage);
        this.rightSlide = homeScreen.findViewById(R.id.RVImageSliderRightContainer);
        this.leftSlide = homeScreen.findViewById(R.id.RVImageSliderLeftContainer);

    }

    @Override
    public void initializeListeners() {

        this.rightSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Right slide called");
                simulateImageSliding(true);
            }
        });

        this.leftSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Left slide called");
                simulateImageSliding(false);
            }
        });


    }

    @Override
    public void startProcessing() {

        addItems();
        setTimer();


    }

    private void addItems() {

        Bitmap bitmap1 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_5);
        Bitmap bitmap6 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_6);
        Bitmap bitmap7 = BitmapFactory.decodeResource(homeScreen.getResources(), R.drawable.gallery_7);

        imageBitmapsLists.add(bitmap1);
        imageBitmapsLists.add(bitmap2);
        imageBitmapsLists.add(bitmap3);
        imageBitmapsLists.add(bitmap4);
        imageBitmapsLists.add(bitmap5);
        imageBitmapsLists.add(bitmap6);
        imageBitmapsLists.add(bitmap7);

    }


    private void setTimer() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                homeScreen.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simulateImageSliding(true);
                        validateSideSliders();
                        Log.d(TAG, "CALLED");
                    }
                });
            }
        }, 5000, 5000);

    }

    private void validateSideSliders() {

        if (imageBitmapsLists.size() > 1) {

            leftSlide.setVisibility(View.VISIBLE);
            rightSlide.setVisibility(View.VISIBLE);

        } else {

            leftSlide.setVisibility(View.GONE);
            rightSlide.setVisibility(View.GONE);
        }

    }

    private void simulateImageSliding(boolean next) {

        if (imageBitmapsLists.size() <= 1) {

            Log.d(TAG, "No Image to Slide. Returning");

            return;
        }

        if (next) {

            currentIndex++;
            if (currentIndex > imageBitmapsLists.size() - 1) {
                currentIndex = 0;
            }

            Animation leftAnimation = AnimationUtils.loadAnimation(homeScreen, R.anim.left_animation);
            imageShowing.setAnimation(leftAnimation);

        } else {

            currentIndex--;
            if (currentIndex < 0) {
                currentIndex = imageBitmapsLists.size() - 1;
            }

            Animation rightAnimation = AnimationUtils.loadAnimation(homeScreen, R.anim.right_animation);
            imageShowing.setAnimation(rightAnimation);

        }
        Log.d(TAG, "Asking to set the image position: " + currentIndex);
        setSliderImage(currentIndex);

    }

    private void setSliderImage(int position) {

        imageShowing.setImageBitmap(imageBitmapsLists.get(position));
        Log.d(TAG, "Slider set to position image position: " + currentIndex);

    }


}