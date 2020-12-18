package com.learning.mobilzlab.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.ScheduleBookUtils;

public class ClientForm extends AppCompatActivity implements ActivityCustomHooks {

    private static final String TAG = "ClientFormTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);

        callHooks();

    }

    @Override
    public void callHooks() {

        initializeViews();

    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void initializeViews() {

        ScheduleBookUtils utils = new ScheduleBookUtils(this);
        utils.callHooks();

    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void startProcessing() {

    }
}