package com.learning.mobilzlab.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.learning.mobilzlab.AdminRepairReuqest.RepairRequestCore;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;

public class AdminScheduleBookView extends AppCompatActivity implements ActivityCustomHooks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule_book_view);

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

        RepairRequestCore core = new RepairRequestCore(this);
        core.callHooks();

    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void startProcessing() {

    }
}