package com.learning.mobilzlab.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.GallerySliderUtils;

public class Gallery extends AppCompatActivity implements UtilsHooks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        callHooks();
    }

    @Override
    public void callHooks() {

        initializeViews();

    }

    @Override
    public void initializeViews() {


        GallerySliderUtils gallerySliderUtils = new GallerySliderUtils(this);
        gallerySliderUtils.callHooks();

    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void startProcessing() {

    }
}