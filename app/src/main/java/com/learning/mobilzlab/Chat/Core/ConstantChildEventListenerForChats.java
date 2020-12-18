package com.learning.mobilzlab.Chat.Core;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ConstantChildEventListenerForChats implements ChildEventListener {

    public static final String TAG = "SendChatCLTAG";

    private ChatCore chatCore;

    public ConstantChildEventListenerForChats(ChatCore chatCore) {
        this.chatCore = chatCore;
    }


    @Override
    public void onChildAdded(@NonNull DataSnapshot chat, @Nullable String s) {

        Log.d(TAG, "CHAT SNAPSHOT CHILD ADDED: " + chat);

        if (chat.exists()) {

            int sendingUserID = getSendingUserID(chat);
            int receivingUserID = getReceivingUserID(chat);


            // If I sent to him
            if (!chatCore.firstMessageSent && sendingUserID == chatCore.SENDING_USER && receivingUserID == chatCore.RECEIVING_USER) {

                chatCore.thisAddedChatSnapshotIsForMe(chat);
                Log.d(TAG, "I sent");

            }

            //  If he sent to me
            else if (sendingUserID == chatCore.RECEIVING_USER && receivingUserID == chatCore.SENDING_USER) {

                chatCore.thisAddedChatSnapshotIsForMe(chat);
                Log.d(TAG, "I received");
            }

        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot chat, @Nullable String s) {

        if (chat.exists()) {

            int sendingUserID = getSendingUserID(chat);
            int receivingUserID = getReceivingUserID(chat);

            //If change is made on my chat things
            if (sendingUserID == chatCore.SENDING_USER && receivingUserID == chatCore.RECEIVING_USER) {

                chatCore.thisUpdatedSnapshotIsForMe(chat);
                Log.d(TAG, "my chat things got updated");

            }

            //If change is made on his chat things
            else if (sendingUserID == chatCore.RECEIVING_USER && receivingUserID == chatCore.SENDING_USER) {

                chatCore.thisUpdatedSnapshotIsForMe(chat);
                Log.d(TAG, "his chat things got updated");
            }

        }

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot chat) {

        Log.d(TAG, "CHAT DELETED " + chat);


        if (chat.exists()) {


            int sendingUserID = getSendingUserID(chat);
            int receivingUserID = getReceivingUserID(chat);


            // If I deleted for him
            if (sendingUserID == chatCore.SENDING_USER && receivingUserID == chatCore.RECEIVING_USER) {

                chatCore.thisDeletedSnapShotIsForMe(chat);
                Log.d(TAG, "I deleted");

            }

            // If he deletes for me
            else if (sendingUserID == chatCore.RECEIVING_USER && receivingUserID == chatCore.SENDING_USER) {

                chatCore.thisDeletedSnapShotIsForMe(chat);
                Log.d(TAG, "he deleted");

            }

        } else {

            Log.d(TAG, "Not existing");
        }

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


    // Helping Methods
    private int getSendingUserID(DataSnapshot chat) {

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("sendingUserID".equals(attribute.getKey())) {

                return (int) ((long) attribute.getValue());

            }
        }

        return 0;

    }

    private int getReceivingUserID(DataSnapshot chat) {

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("receivingUserID".equals(attribute.getKey())) {

                return (int) ((long) attribute.getValue());
            }
        }

        return 0;
    }

}
