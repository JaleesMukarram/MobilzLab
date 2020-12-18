package com.learning.mobilzlab.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.mobilzlab.AdminRepairReuqest.RepairRequestDetailsUtils;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.ScheduleBook.RepairRequest;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

public class AdminProblemDetails extends AppCompatActivity implements ActivityCustomHooks {

    private RepairRequest mRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_problem_details);

        callHooks();
    }

    @Override
    public void callHooks() {

        handleIntent();
        initializeViews();

    }

    @Override
    public void handleIntent() {

        Intent intent = getIntent();

        mRepair = (RepairRequest) intent.getSerializableExtra(DataSharedPrefs.REPAIR_INTENT_KEY);

        if (mRepair == null){

            finish();
        }
        
    }

    @Override
    public void initializeViews() {

        RepairRequestDetailsUtils utils = new RepairRequestDetailsUtils(this, mRepair);
        utils.callHooks();


    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void startProcessing() {

    }
}