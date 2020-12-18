package com.learning.mobilzlab.Chat.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Activities.SendChat;
import com.learning.mobilzlab.Chat.Core.ReceivedChatShowingCore;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

public class SingleUserChatView extends RecyclerView.ViewHolder {

    private TextView UserName;
    private TextView UnreadMessageShowing;
    private TextView TotalMessagesShowing;
    private TextView QueryTime;

    private RelativeLayout MainContainer;

    private ReceivedChatShowingCore receivedChatShowingCore;

    public SingleUserChatView(@NonNull View itemView, ReceivedChatShowingCore receivedChatShowingCore) {
        super(itemView);

        this.receivedChatShowingCore = receivedChatShowingCore;

        UserName = itemView.findViewById(R.id.TVQueryUserName);
        UnreadMessageShowing = itemView.findViewById(R.id.TVQueryUserUnreadMessages);
        TotalMessagesShowing = itemView.findViewById(R.id.TVQueryUserTotalMessages);
        QueryTime = itemView.findViewById(R.id.TVQueryLastDate);

        MainContainer = itemView.findViewById(R.id.RLQueryUserMainContainer);

    }

    @SuppressLint("SetTextI18n")
    public void setData(int position) {

        setUserName(position);

        setMainListener(position);

        setTotalMessages(position);

        setUnreadMessages(position);

        QueryTime.setText(receivedChatShowingCore.
                getRelativeDate(receivedChatShowingCore.repairUserList.get(position).getLastMessageDate()));

    }


    private void setUnreadMessages(int position) {

        if (receivedChatShowingCore.repairUserList.get(position).isUnreadMessage()) {

            UnreadMessageShowing.setVisibility(View.VISIBLE);

        } else {
            UnreadMessageShowing.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTotalMessages(int position) {

        TotalMessagesShowing.setText(receivedChatShowingCore.repairUserList.get(position).getTotalMessages() + " messages received");

    }

    private void setMainListener(final int position) {

        MainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(receivedChatShowingCore.homeScreen, SendChat.class);

                intent.putExtra(DataSharedPrefs.SENDER_INTENT_KEY, DataSharedPrefs.ADMIN_ID);
                intent.putExtra(DataSharedPrefs.RECEIVER_INTENT_KEY, receivedChatShowingCore.repairUserList.get(position).getUserID());

                receivedChatShowingCore.homeScreen.startActivity(intent);

            }
        });
    }

    private void setUserName(int position) {

        String userName = "User " + receivedChatShowingCore.repairUserList.get(position).getUserID();

        UserName.setText(userName);

    }


}
