package com.learning.mobilzlab.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.DialogUtils;

public class AdminView extends AppCompatActivity implements ActivityCustomHooks {

    private Button chatBTN;
    private Button repairRequestBTN;
    private Button updateBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        callHooks();
    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();

    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void initializeViews() {

        chatBTN = findViewById(R.id.BTNAdminViewSeeChats);
        repairRequestBTN = findViewById(R.id.BTNAdminViewSeeScheduleBookings);
        updateBTN = findViewById(R.id.BTNAdminViewPasswordChange);

    }

    @Override
    public void initializeListeners() {

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils utils = new DialogUtils(AdminView.this, DialogUtils.MODE_CHANGE_PASSWORD);
                utils.callHooks();

            }
        });

        repairRequestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminView.this, AdminScheduleBookView.class));
            }
        });

        chatBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminView.this, AdminAllChatsView.class));
            }
        });


    }

    @Override
    public void startProcessing() {

    }
}