package com.learning.mobilzlab.Chat.Views;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Chat.Core.ChatCore;
import com.learning.mobilzlab.Chat.Modals.ImageChat;
import com.learning.mobilzlab.R;
import com.squareup.picasso.Picasso;

public class ImageChatSendView extends RecyclerView.ViewHolder {

    private ImageView ImageIV;
    private TextView MessageSentDate;

    private ImageView MessageSentStatus;
    private ProgressBar UploadProgressBar;

    private RelativeLayout AfterSentContainer;
    private RelativeLayout ProgressBarContainer;
    private RelativeLayout ChatSendFailedContainer;

    private RelativeLayout MessageContainer;

    private ChatCore chatCore;

    public ImageChatSendView(@NonNull View itemView, ChatCore chatCore) {
        super(itemView);
        this.chatCore = chatCore;

        ImageIV = itemView.findViewById(R.id.IVImageChat);
        MessageSentDate = itemView.findViewById(R.id.TVMessageSentDate);
        MessageSentStatus = itemView.findViewById(R.id.IVMessageSentStatus);

        UploadProgressBar = itemView.findViewById(R.id.PBSendingProgress);

        ProgressBarContainer = itemView.findViewById(R.id.RLProgressContainer);
        AfterSentContainer = itemView.findViewById(R.id.RLSendOptionsContainer);
        ChatSendFailedContainer = itemView.findViewById(R.id.RLSendFailedOptionsContainer);

        MessageContainer = itemView.findViewById(R.id.RLTopContainer);

    }



    public void setData(ImageChat chat) {

        Picasso.get().load(chat.getImageUri())
                .resize(400, 400)
                .into(ImageIV);

        setMessageContainerListener(chat.getImageUri());

        if (chat.isChatSent()) {

            if (chat.getReadDate() != null) {

                chatRead();
                MessageSentDate.setText(chatCore.getRelativeDate(chat.getSentDate()));

            } else if (chat.getSentDate() != null) {

                chatSent();
                MessageSentDate.setText(chatCore.getRelativeDate(chat.getSentDate()));

            } else {

                chatStillSending(chat.getProgress());

            }
        } else {

            chatSentFailed(chat);

        }
    }

    private void setMessageContainerListener(final String uri) {

        MessageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatCore.fullScreenImageShowingDialogue(uri);

            }
        });

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

    private void chatSentFailed(final ImageChat chat) {

        ProgressBarContainer.setVisibility(View.INVISIBLE);
        AfterSentContainer.setVisibility(View.INVISIBLE);
        ChatSendFailedContainer.setVisibility(View.VISIBLE);

        ChatSendFailedContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatCore.uploadImageChatToDatabase(chat);
                chat.setChatSent(true);
                chatCore.adapter.notifyDataSetChanged();
            }
        });

    }

    private void chatStillSending(int progress) {

        ProgressBarContainer.setVisibility(View.VISIBLE);
        AfterSentContainer.setVisibility(View.INVISIBLE);
        ChatSendFailedContainer.setVisibility(View.INVISIBLE);

        UploadProgressBar.setProgress(progress);

    }

}
