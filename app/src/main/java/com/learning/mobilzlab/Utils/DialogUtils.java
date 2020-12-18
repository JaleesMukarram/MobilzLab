package com.learning.mobilzlab.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.Activities.AdminView;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;

public class DialogUtils implements UtilsHooks {

    public static final int MODE_DIAL_PASSWORD = 1;
    public static final int MODE_CHANGE_PASSWORD = 2;
    public static final String ADMIN_PASSWORD_REFERENCE = "ADMIN";

    private EditText passwordET;
    private Button enterBTN;

    private AlertDialog.Builder mBuilder;
    private Dialog mDialog;
    private View mDialogueView;

    private int currentMode;


    private Activity homeScreen;

    public DialogUtils(Activity homeScreen, int currentMode) {

        this.homeScreen = homeScreen;
        this.currentMode = currentMode;

    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();
    }

    @Override
    public void initializeViews() {

        mDialogueView = homeScreen.getLayoutInflater().inflate(R.layout.admin_dialogue, null, false);

        passwordET = mDialogueView.findViewById(R.id.ETAdminDialoguePasswordAsking);
        enterBTN = mDialogueView.findViewById(R.id.BTNSignUpRegisterNow);

        mBuilder = new AlertDialog.Builder(homeScreen);

        mBuilder.setView(mDialogueView);

        if (currentMode == MODE_DIAL_PASSWORD) {

            enterBTN.setText(R.string.enter);


        } else if (currentMode == MODE_CHANGE_PASSWORD) {

            enterBTN.setText(R.string.update);
        }


    }

    @Override
    public void initializeListeners() {

        enterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked();
            }
        });

    }

    private void buttonClicked() {

        String password = passwordET.getText().toString().trim();

        if (checkLocalValidations(password)) {

            if (currentMode == MODE_DIAL_PASSWORD) {

                validateEnterPasswordFromDatabase(password);

            } else if (currentMode == MODE_CHANGE_PASSWORD) {

                changeEnterPasswordAfterValidation(password);
            }


        }

    }

    private void changeEnterPasswordAfterValidation(String password) {

        if (true) {

            updatePasswordInDatabase(password);
        }
    }

    private void updatePasswordInDatabase(String password) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(ADMIN_PASSWORD_REFERENCE);

        reference.child("PASSWORD")
                .setValue(password)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(homeScreen, "Password updated", Toast.LENGTH_SHORT).show();
                        mDialog.cancel();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(homeScreen, "Failed to update password", Toast.LENGTH_SHORT).show();

                    }
                })

                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        Toast.makeText(homeScreen, "Failed to update password", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void validateEnterPasswordFromDatabase(final String password) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(ADMIN_PASSWORD_REFERENCE);

        reference.child("PASSWORD")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            String serverPassword = (String) snapshot.getValue();

                            if (password.equals(serverPassword)){

                                homeScreen.startActivity(new Intent(homeScreen, AdminView.class));
                                homeScreen.finish();

                                Toast.makeText(homeScreen, "Password Matched", Toast.LENGTH_SHORT).show();
                            }else{

                                Toast.makeText(homeScreen, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(homeScreen, "Error while communicating to the server", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private boolean checkLocalValidations(String password) {

        if (password == null || "".equals(password)) {

            Toast.makeText(homeScreen, "Empty password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8 || password.length() > 20) {

            Toast.makeText(homeScreen, "Password length should between 8 to 20", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void startProcessing() {

        mDialog = mBuilder.create();
        mDialog.show();

    }
}
