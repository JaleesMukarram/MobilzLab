package com.learning.mobilzlab.Feedback;

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

public class FeedbackDialogue implements UtilsHooks {

    public static final String FEEDBACK_REFERENCE = "FEEDBACK_REFERENCE";

    private EditText nameET;
    private EditText feedbackET;
    private Button enterBTN;

    private AlertDialog.Builder mBuilder;
    private Dialog mDialog;
    private View mDialogueView;


    private Activity homeScreen;

    public FeedbackDialogue(Activity homeScreen) {

        this.homeScreen = homeScreen;

    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();
    }

    @Override
    public void initializeViews() {

        mDialogueView = homeScreen.getLayoutInflater().inflate(R.layout.feedback_dialogue, null, false);

        nameET = mDialogueView.findViewById(R.id.ETFeedbackDialogueNameAsking);
        feedbackET = mDialogueView.findViewById(R.id.ETFeedbackDialogueFeedbackAsking);
        enterBTN = mDialogueView.findViewById(R.id.BTNFeedbackDialogueSend);

        mBuilder = new AlertDialog.Builder(homeScreen);

        mBuilder.setView(mDialogueView);


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

    @Override
    public void startProcessing() {

        mDialog = mBuilder.create();
        mDialog.show();

    }

    private void buttonClicked() {

        String name = nameET.getText().toString().trim();
        String feedback = feedbackET.getText().toString().trim();

        if (checkLocalValidations(name,feedback )) {

             putFeedbackInDatabase(name , feedback);


        }

    }


    private void putFeedbackInDatabase(String name, String feedback) {

        Feedback feed = new Feedback(name, feedback);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FEEDBACK_REFERENCE);

        reference.child(String.valueOf(feed.getmID()))
                .setValue(feed)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(homeScreen, "Feedback sent", Toast.LENGTH_SHORT).show();
                        mDialog.cancel();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(homeScreen, "Failed to send feedback", Toast.LENGTH_SHORT).show();

                    }
                })

                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        Toast.makeText(homeScreen, "Failed to send feedback", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private boolean checkLocalValidations(String name, String feedback) {

        if (name == null || "".equals(name)) {

            Toast.makeText(homeScreen, "Empty Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (feedback.length() < 3 || feedback.length() > 200) {

            Toast.makeText(homeScreen, "Feedback length should between 3 to 200", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
