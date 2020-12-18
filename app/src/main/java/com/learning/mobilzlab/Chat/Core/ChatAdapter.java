package com.learning.mobilzlab.Chat.Core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Chat.Modals.ImageChat;
import com.learning.mobilzlab.Chat.Modals.MessageChat;
import com.learning.mobilzlab.Chat.Views.ImageChatReceiveView;
import com.learning.mobilzlab.Chat.Views.ImageChatSendView;
import com.learning.mobilzlab.Chat.Views.MessageChatReceiveView;
import com.learning.mobilzlab.Chat.Views.MessageChatSendView;
import com.learning.mobilzlab.R;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MESSAGE_CHAT_SEND = 2;
    public static final int MESSAGE_CHAT_RECEIVED = 4;
    public static final int IMAGE_CHAT_SENT = 8;
    public static final int IMAGE_CHAT_RECEIVED = 16;


    private ChatCore chatCore;

    public ChatAdapter(ChatCore chatCore) {
        this.chatCore = chatCore;
    }

    @Override
    public int getItemViewType(int position) {

        if (chatCore.chatList.get(position) instanceof MessageChat) {


            if (chatCore.chatList.get(position).getSendingUserID() == chatCore.SENDING_USER) {

                return MESSAGE_CHAT_SEND;

            } else if (chatCore.chatList.get(position).getSendingUserID() == chatCore.RECEIVING_USER) {

                return MESSAGE_CHAT_RECEIVED;
            }

        } else if (chatCore.chatList.get(position) instanceof ImageChat) {

            if (chatCore.chatList.get(position).getSendingUserID() == chatCore.SENDING_USER) {

                return IMAGE_CHAT_SENT;

            } else if (chatCore.chatList.get(position).getSendingUserID() == chatCore.RECEIVING_USER) {

                return IMAGE_CHAT_RECEIVED;
            }
        }

        return 2;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == MESSAGE_CHAT_SEND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_chat_send, parent, false);
            return new MessageChatSendView(view, chatCore);

        } else if (viewType == MESSAGE_CHAT_RECEIVED) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_chat_receive, parent, false);
            return new MessageChatReceiveView(view, chatCore);

        } else if (viewType == IMAGE_CHAT_SENT) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_chat_send, parent, false);
            return new ImageChatSendView(view, chatCore);

        } else if (viewType == IMAGE_CHAT_RECEIVED) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_chat_receive, parent, false);
            return new ImageChatReceiveView(view, chatCore);

        }

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_chat_send, parent, false);
        return new MessageChatSendView(view, chatCore);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MessageChatSendView) {

            MessageChat chat = (MessageChat) chatCore.chatList.get(position);
            ((MessageChatSendView) holder).setData(chat);

        } else if (holder instanceof MessageChatReceiveView) {

            MessageChat chat = (MessageChat) chatCore.chatList.get(position);
            ((MessageChatReceiveView) holder).setData(chat);

        } else if (holder instanceof ImageChatSendView) {

            ImageChat chat = (ImageChat) chatCore.chatList.get(position);
            ((ImageChatSendView) holder).setData(chat);

        } else if (holder instanceof ImageChatReceiveView) {

            ImageChat chat = (ImageChat) chatCore.chatList.get(position);
            ((ImageChatReceiveView) holder).setData(chat);

        }

    }

    @Override
    public int getItemCount() {

        return chatCore.chatList.size();
    }

}