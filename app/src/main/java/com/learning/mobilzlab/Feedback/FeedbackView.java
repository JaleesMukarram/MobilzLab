package com.learning.mobilzlab.Feedback;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.R;

public class FeedbackView extends RecyclerView.ViewHolder {

    private FeedbackCore feedbackCore;

    private TextView nameShowingTV;
    private TextView timeShowingTV;

    private TextView feedbackShowingTV;

    public FeedbackView(@NonNull View itemView, FeedbackCore feedbackCore) {
        super(itemView);

        this.feedbackCore = feedbackCore;

        nameShowingTV = itemView.findViewById(R.id.TVSingleFeedbackNameShowing);
        timeShowingTV = itemView.findViewById(R.id.TVSingleFeedbackDateShowing);
        feedbackShowingTV = itemView.findViewById(R.id.TVSingleFeedbackFeedbackShowing);

    }

    public void setData(int position) {

        Feedback feedback = feedbackCore.feedbackList.get(position);

        nameShowingTV.setText(feedback.getmName());
        timeShowingTV.setText(feedbackCore.getRelativeDate(feedback.getmDate()));
        feedbackShowingTV.setText(feedback.getFeedback());

    }
}
