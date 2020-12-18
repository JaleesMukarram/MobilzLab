package com.learning.mobilzlab.Activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Services.FirebaseSyncService;

public class Splash extends AppCompatActivity {

    private static final String TAG = "SplashTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startJob();
        moveToHome();

    }

    private void moveToHome() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    startActivity(new Intent(Splash.this, HomeScreen.class));
                }

            }
        };

        thread.start();
    }

    private void startJob() {


        ComponentName componentName = new ComponentName(getApplication(), FirebaseSyncService.class);

        JobInfo jobInfo = new JobInfo.Builder(FirebaseSyncService.SERVICE_ID, componentName)
                 .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

         if (jobScheduler == null){

             Log.d(TAG, "JobScheduler null");

         }else{


             int status = jobScheduler.schedule(jobInfo);

             if (status == JobScheduler.RESULT_SUCCESS){

                 Log.d(TAG, "Service scheduled");
             }else{

                 Log.d(TAG,"Service schedule failed");
             }
         }


    }


}