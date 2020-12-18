package com.learning.mobilzlab.Feedback;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.learning.mobilzlab.Feedback.FeedbackDialogue.FEEDBACK_REFERENCE;

public class FeedbackCore implements UtilsHooks {

    private static final String TAG = "FeedBackCoreTAG";
    public List<Feedback> feedbackList;
    Activity homeScreen;
    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    private View mView;

    public FeedbackCore(Activity homeScreen, View mView) {
        this.homeScreen = homeScreen;
        this.mView = mView;

    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();

    }

    @Override
    public void initializeViews() {

        feedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(this);

        recyclerView = mView.findViewById(R.id.RVFragmentServicesAllFeedbackShowing);

        LinearLayoutManager layoutManager = new LinearLayoutManager(homeScreen);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView.setAdapter(feedbackAdapter);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void initializeListeners() {


    }

    @Override
    public void startProcessing() {

        fetchFeedbackFromDatabase();
    }

    private void fetchFeedbackFromDatabase() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FEEDBACK_REFERENCE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot feedback : snapshot.getChildren()) {


                        Feedback feed = feedback.getValue(Feedback.class);
                        checkIfFeedbackIsNew(feed);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkIfFeedbackIsNew(Feedback feed) {

        for (Feedback fb : feedbackList) {

            if (fb.getmID() == feed.getmID()) {

                return;
            }
        }

        Log.d(TAG, "Added: " + feed.getmID());

        feedbackList.add(feed);
        sortAllFeedback();

     }


    // Others
    public String getRelativeDate(Date date) {

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;

        Date currentDate = new Date();


        long difference = currentDate.getTime() - date.getTime();

        if (difference > day) {

            return ((int) (Math.floor(1f * difference / day))) + "d";

        } else if (difference > hour) {

            return ((int) (Math.floor(1f * difference / hour))) + "h";

        } else if (difference > minute) {

            return ((int) (Math.floor(1f * difference / minute))) + "m";
        } else {

            int diff = ((int) (Math.floor(1f * difference / second)));

            if (diff > 0) {

                return diff + "s";

            } else {

                return "now";
            }
        }

    }

    void sortAllFeedback() {

        Collections.sort(feedbackList, new Comparator<Feedback>() {
            @Override
            public int compare(Feedback feedback, Feedback t1) {

                return t1.getmDate().compareTo(feedback.getmDate());

             }
        });

        feedbackAdapter.notifyDataSetChanged();

    }

}
