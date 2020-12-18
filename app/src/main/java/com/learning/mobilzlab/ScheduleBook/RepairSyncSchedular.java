package com.learning.mobilzlab.ScheduleBook;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.Notifications.NotificationsUtils;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

import static com.learning.mobilzlab.Utils.ScheduleBookUtils.FORM_REFERENCE;

public class RepairSyncSchedular {

    private static final String TAG = "RepairSyncTAG";
    DataSharedPrefs dataSharedPrefs;
    private boolean firstTimeDone;
    private Context context;

    public RepairSyncSchedular(Context context) {
        this.context = context;

        Log.d(TAG, "Repair Sync Created");
    }

    public void listenForRepair() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FORM_REFERENCE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (firstTimeDone) {

                    if (snapshot.exists()) {

                        Log.d(TAG,"snapshot with request came");
                        repairRequestReceived();


                    }

                } else {

                    firstTimeDone = true;
                    Log.d(TAG, "First Time done");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void repairRequestReceived() {

        Log.d(TAG, "Unread Request notified");

        NotificationsUtils notificationsUtils = new NotificationsUtils(context);
        notificationsUtils.createSimpleNotification("New Repair Request Received", "Open app to check");

    }

}
