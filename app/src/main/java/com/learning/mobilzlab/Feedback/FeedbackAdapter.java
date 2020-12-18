package com.learning.mobilzlab.Feedback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.R;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackView> {

    private FeedbackCore feedbackCore;

    public FeedbackAdapter(FeedbackCore feedbackCore) {
        this.feedbackCore = feedbackCore;
    }

    @NonNull
    @Override
    public FeedbackView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(feedbackCore.homeScreen).inflate(R.layout.view_single_feedback, null,false);

        return new FeedbackView(view, feedbackCore);

    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackView holder, int position) {

        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return feedbackCore.feedbackList.size();
    }
}
