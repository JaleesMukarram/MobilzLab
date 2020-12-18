package com.learning.mobilzlab.Chat.Views;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Chat.Core.ChatCore;
import com.learning.mobilzlab.Chat.Modals.ImageChat;
import com.learning.mobilzlab.R;
import com.squareup.picasso.Picasso;

public class ImageChatReceiveView extends RecyclerView.ViewHolder {

    private ImageView ImageIV;
    private TextView MessageSentDate;

    private RelativeLayout MessageContainer;

    private ChatCore chatCore;


    public ImageChatReceiveView(@NonNull View itemView, ChatCore chatCore) {
        super(itemView);

        this.chatCore = chatCore;
         ImageIV = itemView.findViewById(R.id.IVImageChat);
        MessageSentDate = itemView.findViewById(R.id.TVMessageSentDate);

        MessageContainer = itemView.findViewById(R.id.RLTopContainer);

    }

    public void setData(ImageChat chat) {

        Picasso.get().load(chat.getImageUri())
                .resize(400, 400)
                .into(ImageIV);

        setMessageContainerListener(chat.getImageUri());


        MessageSentDate.setText(chatCore.getRelativeDate(chat.getSentDate()));

    }

    private void setMessageContainerListener(final String uri) {

        MessageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatCore.fullScreenImageShowingDialogue(uri);

            }
        });

    }

}
