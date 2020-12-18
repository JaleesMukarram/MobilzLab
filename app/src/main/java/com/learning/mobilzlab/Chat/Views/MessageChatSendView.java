package com.learning.mobilzlab.Chat.Views;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Chat.Core.ChatCore;
import com.learning.mobilzlab.Chat.Modals.MessageChat;
import com.learning.mobilzlab.R;

public class MessageChatSendView extends RecyclerView.ViewHolder {

    private TextView MessageTV;
    private TextView MessageSentDate;

    private RelativeLayout AfterSentContainer;
    private RelativeLayout ProgressBarContainer;
    private RelativeLayout ChatSendFailedContainer;

    private RelativeLayout MessageContainer;


    private ImageView MessageSentStatus;

    private ChatCore chatCore;

    public MessageChatSendView(@NonNull View itemView, ChatCore chatCore) {
        super(itemView);

        this.chatCore = chatCore;

        ProgressBarContainer = itemView.findViewById(R.id.RLProgressContainer);
        AfterSentContainer = itemView.findViewById(R.id.RLSendOptionsContainer);
        ChatSendFailedContainer = itemView.findViewById(R.id.RLSendFailedOptionsContainer);

        MessageContainer = itemView.findViewById(R.id.RLTopContainer);

        MessageTV = itemView.findViewById(R.id.TVChatMessage);
        MessageSentDate = itemView.findViewById(R.id.TVMessageSentDate);
        MessageSentStatus = itemView.findViewById(R.id.IVMessageSentStatus);

    }

    @SuppressLint("SetTextI18n")
    public void setData(MessageChat chat) {

        MessageTV.setText(chat.getMessage());

        setPopUpListener(chat.getChatID());

        // If the chat is still sending or has been read
        if (chat.isChatSent()) {

            if (chat.getReadDate() != null) {

                chatRead();
                MessageSentDate.setText(chatCore.getRelativeDate(chat.getSentDate()));


            } else if (chat.getSentDate() != null) {

//                    Log.d(TAG, "CHAT " + chat.getMessage() + " CHAT SENT " + chat.getSentDate());

                chatSent();
                String date = chatCore.getRelativeDate(chat.getSentDate());
                MessageSentDate.setText(date);

            } else {

//                    Log.d(TAG, "STILL SENDING");
                chatStillSending();
            }

        }

        // If the chat has been sending failed
        else {

            chatSentFailed(chat);


        }
    }

    private void chatRead() {

        ProgressBarContainer.setVisibility(View.INVISIBLE);
        AfterSentContainer.setVisibility(View.VISIBLE);

        MessageSentStatus.setImageDrawable(ContextCompat.getDrawable(chatCore.homeActivity, R.drawable.ic_tick_received));

    }

    private void chatSent() {

        ProgressBarContainer.setVisibility(View.INVISIBLE);
        AfterSentContainer.setVisibility(View.VISIBLE);

        MessageSentStatus.setImageDrawable(ContextCompat.getDrawable(chatCore.homeActivity, R.drawable.ic_tick_sent));


    }

    private void chatStillSending() {

        ProgressBarContainer.setVisibility(View.VISIBLE);
        AfterSentContainer.setVisibility(View.INVISIBLE);
        ChatSendFailedContainer.setVisibility(View.INVISIBLE);

    }

    private void chatSentFailed(final MessageChat chat) {

        ProgressBarContainer.setVisibility(View.INVISIBLE);
        AfterSentContainer.setVisibility(View.INVISIBLE);

        ChatSendFailedContainer.setVisibility(View.VISIBLE);

        ChatSendFailedContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatCore.uploadThisMessageChatToDatabase(chat);
                chat.setChatSent(true);
                chatCore.adapter.notifyDataSetChanged();
            }
        });

    }

    private void setPopUpListener(final int chatID) {

        MessageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu popupMenu = new PopupMenu(chatCore.homeActivity, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.MIdelete) {
                            chatCore.deleteThisChatFromDatabase(chatID);
                        }
                        return false;
                    }
                });

                popupMenu.inflate(R.menu.message_chat_option_menu);
                popupMenu.show();

                return true;
            }
        });
    }
}

