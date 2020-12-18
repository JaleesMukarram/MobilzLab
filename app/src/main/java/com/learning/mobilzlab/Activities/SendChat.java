package com.learning.mobilzlab.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.learning.mobilzlab.Chat.Core.ChatCore;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;

import static com.learning.mobilzlab.Utils.DataSharedPrefs.ADMIN_ID;
import static com.learning.mobilzlab.Utils.DataSharedPrefs.RECEIVER_INTENT_KEY;
import static com.learning.mobilzlab.Utils.DataSharedPrefs.SENDER_INTENT_KEY;

public class SendChat extends AppCompatActivity implements ActivityCustomHooks {

    private ChatCore chatCore;
    private int sendingID;
    private int receivingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_chat);


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

        this.sendingID = intent.getIntExtra(SENDER_INTENT_KEY, 1);
        this.receivingID = intent.getIntExtra(RECEIVER_INTENT_KEY, 1);
        validateIntentKeys();
    }

    @Override
    public void initializeViews() {

        this.chatCore = new ChatCore(this, sendingID, receivingID);
        this.chatCore.callHooks();

    }

    @Override
    public void initializeListeners() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);


    }

    @Override
    public void startProcessing() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chatCore.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        chatCore.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void validateIntentKeys() {

        if (sendingID != ADMIN_ID && receivingID != ADMIN_ID) {

            finish();

        } else {

            if (sendingID < 100000 || receivingID < 100000) {

                finish();
            }


        }
    }

}