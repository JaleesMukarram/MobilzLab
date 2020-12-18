package com.learning.mobilzlab.Chat.Modals;

import androidx.annotation.NonNull;

import java.util.Date;

public class ImageChat extends Chat implements Cloneable{

    private String imageUri;
    private int chatType = CHAT_TYPE_IMAGE;
    private int progress = 100;

    public ImageChat() {
    }

    public ImageChat(String imageUri) {
        this.imageUri = imageUri;
    }

    public ImageChat(  int sendingUserID, int receivingUserID, String imageUri) {
        super(  sendingUserID, receivingUserID, CHAT_TYPE_IMAGE);
        this.imageUri = imageUri;
    }

    public ImageChat(  int sendingUserID, int receivingUserID, Date sentDate, Date receivedDate, Date readDate, String imageUri) {
        super(  sendingUserID, receivingUserID, sentDate, readDate);
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getChatType() {
        return chatType;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
