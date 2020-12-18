package com.learning.mobilzlab.Utils;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.ScheduleBook.MobileTypes;
import com.learning.mobilzlab.ScheduleBook.RepairRequest;

public class ScheduleBookUtils implements UtilsHooks {

    public static final String FORM_REFERENCE = "FORM_REFERENCE";
    private static final String TAG = "ClientFormUtilsTAG";
    private EditText nameET;
    private EditText numberET;
    private EditText addressET;
    private EditText problemET;

    private RelativeLayout progressBarContainerRL;

    private Spinner phoneSPR;

    private TextView statusTV;
    private Button bookBTN;

    private String mName;
    private String mNumber;
    private String mAddress;
    private String mProblem;

    private String mMobile;

    private Activity homeActivity;

    public ScheduleBookUtils(Activity homeActivity) {

        this.homeActivity = homeActivity;

    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();

    }

    @Override
    public void initializeViews() {

        nameET = homeActivity.findViewById(R.id.ETSignUpUsernameAsking);
        numberET = homeActivity.findViewById(R.id.ETSignUpNumberAsking);
        addressET = homeActivity.findViewById(R.id.ETSignUpAddressAsking);
        problemET = homeActivity.findViewById(R.id.ETSignUpMessageAsking);

        phoneSPR = homeActivity.findViewById(R.id.SPRSignUpPhoneSpinner);


        statusTV = homeActivity.findViewById(R.id.TVNSignUpStatusShowing);

        bookBTN = homeActivity.findViewById(R.id.BTNSignUpRegisterNow);

        progressBarContainerRL = homeActivity.findViewById(R.id.PBSignUpButtonBusyContainer);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(homeActivity, R.layout.support_simple_spinner_dropdown_item, MobileTypes.allMobiles);

        phoneSPR.setAdapter(arrayAdapter);

    }

    @Override
    public void initializeListeners() {

        bookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyFormAndSubmit();
            }
        });

    }

    @Override
    public void startProcessing() {

    }


    private void verifyFormAndSubmit() {

        clearStatus();

        mName = nameET.getText().toString().trim();
        mNumber = numberET.getText().toString().trim();
        mAddress = addressET.getText().toString().trim();
        mProblem = problemET.getText().toString().trim();

        mMobile = MobileTypes.allMobiles[phoneSPR.getSelectedItemPosition()];


        if (verifyETFields()) {

            createSchedule();


        }

    }


    private boolean verifyETFields() {

        if (mName == null || mName.equals("")) {

            setErrorStatus("Please enter your name");
            return false;

        }

        if (mNumber.length() < 10) {

            setErrorStatus("Please enter your right phone number. MobilzLab may need to contact you");
            return false;

        }
        if (mProblem == null || mProblem.equals("")) {

            setErrorStatus("Please enter your problem Description");
            return false;

        }

        return true;
    }

    private void createSchedule() {

        setBusy();

        DataSharedPrefs sharedPrefs = new DataSharedPrefs(homeActivity.getApplicationContext());
        int id = sharedPrefs.getUserID();

        RepairRequest requestRepair = new RepairRequest(id, mName, mNumber, mMobile, mProblem);

        if (mAddress != null && !mAddress.equals("")) {

            requestRepair.setmUserAddress(mAddress);

        }

        sendFormToServer(requestRepair);


    }

    private void setErrorStatus(String error) {


        statusTV.setTextColor(ContextCompat.getColor(homeActivity, R.color.colorRed));
        statusTV.setText(error);


    }

    private void setFormAsSent() {


        statusTV.setTextColor(ContextCompat.getColor(homeActivity, R.color.colorGreen));
        statusTV.setText(R.string.status_sent);

        resetAll();


    }

    private void resetAll() {

        nameET.setText("");
        numberET.setText("");
        addressET.setText("");
        problemET.setText("");
    }

    private void clearStatus() {


        statusTV.setTextColor(ContextCompat.getColor(homeActivity, R.color.colorGreen));
        statusTV.setText("");

    }

    private void setBusy() {

        progressBarContainerRL.setVisibility(View.VISIBLE);


    }

    private void setUnBusy() {

        progressBarContainerRL.setVisibility(View.INVISIBLE);


    }


    private void sendFormToServer(RepairRequest requestRepair) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FORM_REFERENCE);

        reference.child(String.valueOf(requestRepair.getmScheduleID()))
                .setValue(requestRepair)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        setUnBusy();
                        setFormAsSent();

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        setErrorStatus("There was an error while sending to server");

                    }
                })

                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        setErrorStatus("There was an error while sending to server");

                    }
                });
    }
}
