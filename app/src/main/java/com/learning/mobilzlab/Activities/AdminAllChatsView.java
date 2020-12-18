package com.learning.mobilzlab.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.learning.mobilzlab.Chat.Core.ReceivedChatShowingCore;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;

public class AdminAllChatsView extends AppCompatActivity implements ActivityCustomHooks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_chats_view);

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

        ReceivedChatShowingCore receivedChatShowingCore = new ReceivedChatShowingCore(this);
        receivedChatShowingCore.callHooks();

    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void startProcessing() {

    }
}