package com.learning.mobilzlab.Chat.Sync;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.Chat.Firebase.FirebaseGlobals;
import com.learning.mobilzlab.Chat.Modals.Chat;
import com.learning.mobilzlab.Notifications.NotificationsUtils;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

import java.util.Date;

public class ChatSyncScheduler {

    public static final int LISTEN_MODE_ADMIN = 1;
    public static final int LISTEN_MODE_USER = 2;


    private static final String TAG = "ChatSyncTAG";
    DataSharedPrefs dataSharedPrefs;
    private Context context;
    private int currentListenMode;

    public ChatSyncScheduler(Context context, int currentListenMode) {
        this.context = context;
        this.currentListenMode = currentListenMode;

        if (currentListenMode == LISTEN_MODE_USER) {

            dataSharedPrefs = new DataSharedPrefs(context);
        }

    }

    public void listenForMessages() {

        Log.d(TAG, "Listener for Admin Scheduled");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseGlobals.Database.QUERY_CHAT_REFERENCE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot chat : dataSnapshot.getChildren()) {


                        int receivingUserID = getReceivingUserID(chat);

                        if (currentListenMode == LISTEN_MODE_ADMIN) {

                            if (receivingUserID == DataSharedPrefs.ADMIN_ID) {

                                Log.d(TAG, "Chat received by admin");
                                ThisChatSnapshotIsForMe(chat);
                            }
                        } else if (currentListenMode == LISTEN_MODE_USER) {


                            if (receivingUserID == dataSharedPrefs.getUserID()) {

                                Log.d(TAG, "Chat received by user");
                                ThisChatSnapshotIsForMe(chat);

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ThisChatSnapshotIsForMe(DataSnapshot snapshot) {

        Chat chat = snapshot.getValue(Chat.class);

        if (chat != null) {

            if (this.checkIfChatIsUnread(chat)) {

                chatReceived();
            }
        }
    }

    private void chatReceived() {


        Log.d(TAG,"Unread Message notified");

        NotificationsUtils notificationsUtils = new NotificationsUtils(context);
        notificationsUtils.createSimpleNotification("New Message Received", "Open app to check");

    }

    private int getReceivingUserID(DataSnapshot chat) {

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("receivingUserID".equals(attribute.getKey())) {

                return (int) ((long) attribute.getValue());
            }
        }

        return 0;
    }

    private boolean checkIfChatIsUnread(Chat chat) {

        Date readDate = chat.getReadDate();
        return readDate == null;

    }

}

