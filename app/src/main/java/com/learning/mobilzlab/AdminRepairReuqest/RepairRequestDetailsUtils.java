package com.learning.mobilzlab.AdminRepairReuqest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.learning.mobilzlab.Activities.SendChat;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.ScheduleBook.RepairRequest;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

import java.util.Objects;

import static com.learning.mobilzlab.HomeFragments.ContactUsFragment.CALL_PERMISSION_REQUEST;

public class RepairRequestDetailsUtils implements UtilsHooks {

    private TextView nameTV;
    private TextView numberTV;
    private TextView addressTV;

    private TextView mobileTV;

    private TextView problemTV;

    private Button callBTN;
    private Button chatBTN;

    private RepairRequest mRepair;

    private Activity homeScreen;


    public RepairRequestDetailsUtils(Activity homeScreen, RepairRequest mRepair) {

        this.homeScreen = homeScreen;
        this.mRepair = mRepair;

    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();

    }

    @Override
    public void initializeViews() {

        nameTV = homeScreen.findViewById(R.id.TVRepairRequestAdminOpenNameShowing);
        numberTV = homeScreen.findViewById(R.id.TVRepairRequestAdminOpenNumberShowing);
        addressTV = homeScreen.findViewById(R.id.TVRepairRequestAdminOpenAddressShowing);

        mobileTV = homeScreen.findViewById(R.id.TVRepairRequestAdminOpenMobileShowing);
        problemTV = homeScreen.findViewById(R.id.TVRepairRequestAdminOpenProblemShowing);

        callBTN = homeScreen.findViewById(R.id.BTNRepairRequestAdminOpenCall);
        chatBTN = homeScreen.findViewById(R.id.BTNRepairRequestAdminOpenChat);

    }

    @Override
    public void initializeListeners() {

        chatBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatWithThis();

            }
        });

        callBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callWithThis();


            }
        });


    }


    @Override
    public void startProcessing() {


        nameTV.setText(mRepair.getmUserName());
        numberTV.setText(mRepair.getmUserNumber());

        if (mRepair.getmUserAddress() == null || "".equals(mRepair.getmUserAddress())) {

            addressTV.setText(R.string.empty);
        } else {

            addressTV.setText(mRepair.getmUserAddress());
        }

        mobileTV.setText(mRepair.getmUserMobile());
        problemTV.setText(mRepair.getmUserMessage());


    }

    private void callWithThis() {

        if (checkIfCallPermissionIsGranted()){

            makeCall();
        }else{

            getCallPermission();
        }


    }

    private void chatWithThis() {

        Intent intent = new Intent(homeScreen, SendChat.class);
        intent.putExtra(DataSharedPrefs.SENDER_INTENT_KEY, DataSharedPrefs.ADMIN_ID);
        intent.putExtra(DataSharedPrefs.RECEIVER_INTENT_KEY, mRepair.getmUserID());

        homeScreen.startActivity(intent);

    }


    private boolean checkIfCallPermissionIsGranted() {

        // If device is on Marshmallow or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // If permission for using camera is not granted
            return Objects.requireNonNull(homeScreen.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);

        }

        return true;
    }

    private void getCallPermission() {

        // If device is on Marshmallow or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] permissions = {Manifest.permission.CALL_PHONE};
            homeScreen.requestPermissions(permissions, CALL_PERMISSION_REQUEST);

        }
    }

    private void makeCall() {

        String uri = "tel: " + mRepair.getmUserNumber();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        homeScreen.startActivity(intent);

    }

}
