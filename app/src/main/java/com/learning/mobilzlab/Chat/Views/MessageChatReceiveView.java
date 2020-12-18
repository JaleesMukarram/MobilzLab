package com.learning.mobilzlab.Chat.Views;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Chat.Core.ChatCore;
import com.learning.mobilzlab.Chat.Modals.MessageChat;
import com.learning.mobilzlab.R;

public class MessageChatReceiveView extends RecyclerView.ViewHolder {

    private TextView MessageTV;
    private TextView MessageSentDate;

    private ChatCore chatCore;

    public MessageChatReceiveView(@NonNull View itemView, ChatCore chatCore) {
        super(itemView);
        this.chatCore = chatCore;

        MessageTV = itemView.findViewById(R.id.TVChatMessage);
        MessageSentDate = itemView.findViewById(R.id.TVMessageSentDate);
    }


    public void setData(MessageChat chat) {

        MessageTV.setText(chat.getMessage());
        MessageSentDate.setText(chatCore.getRelativeDate(chat.getSentDate()));

    }

}
