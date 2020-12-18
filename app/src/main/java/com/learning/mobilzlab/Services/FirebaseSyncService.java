package com.learning.mobilzlab.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.learning.mobilzlab.Chat.Sync.ChatSyncScheduler;
import com.learning.mobilzlab.Notifications.NotificationsUtils;
import com.learning.mobilzlab.ScheduleBook.RepairSyncSchedular;

public class FirebaseSyncService extends JobService {

    public static final int SERVICE_ID = 128;
    private static final String TAG = "JOBServiceTAG";
    private boolean canceledByOS;


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate called");
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        Log.d(TAG, "onStartJob called");

        ChatSyncScheduler chatSyncScheduler = new ChatSyncScheduler(this, ChatSyncScheduler.LISTEN_MODE_ADMIN);
        chatSyncScheduler.listenForMessages();

        RepairSyncSchedular repairSyncSchedular = new RepairSyncSchedular(this);
        repairSyncSchedular.listenForRepair();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob called");

        canceledByOS = true;
        return true;
    }




}
