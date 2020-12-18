package com.learning.mobilzlab.Chat.Core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Chat.Views.SingleUserChatView;
import com.learning.mobilzlab.R;

public class UsersChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ReceivedChatShowingCore receivedChatShowingCore;

    public UsersChatAdapter(ReceivedChatShowingCore receivedChatShowingCore) {
        this.receivedChatShowingCore = receivedChatShowingCore;
    }

    @NonNull
    @Override
    public SingleUserChatView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_query_user_chat, parent, false);

        return new SingleUserChatView(view, receivedChatShowingCore);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        ((SingleUserChatView) holder).setData(position);

    }

    @Override
    public int getItemCount() {

        return receivedChatShowingCore.repairUserList.size();
    }
}
