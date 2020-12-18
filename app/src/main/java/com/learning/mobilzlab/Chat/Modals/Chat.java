package com.learning.mobilzlab.Chat.Modals;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Random;

public class Chat {

    public static final int CHAT_TYPE_MESSAGE = 2;
    public static final int CHAT_TYPE_IMAGE = 4;
    public static final int CHAT_TYPE_DOCUMENTS = 8;

     private int chatID;

    private int sendingUserID;
    private int receivingUserID;

    private int chatType;

    private boolean chatSent = true;

    private Date sentDate;
    private Date readDate;
    private Date typedDate;

    public Chat() {

    }

    public Chat( int sendingUserID, int receivingUserID, int chatType) {

         this.sendingUserID = sendingUserID;
        this.receivingUserID = receivingUserID;
        this.chatType = chatType;

        this.typedDate = new Date();
        this.chatID = 10000000 + new Random().nextInt(89999999);
    }

    public Chat( int sendingUserID, int receivingUserID, Date sentDate, Date readDate) {
         this.sendingUserID = sendingUserID;
        this.receivingUserID = receivingUserID;
        this.sentDate = sentDate;
        this.readDate = readDate;
    }


    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getSendingUserID() {
        return sendingUserID;
    }

    public void setSendingUserID(int sendingUserID) {
        this.sendingUserID = sendingUserID;
    }

    public int getReceivingUserID() {
        return receivingUserID;
    }

    public void setReceivingUserID(int receivingUserID) {
        this.receivingUserID = receivingUserID;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }


    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public Date getTypedDate() {
        return typedDate;
    }

    public void setTypedDate(Date typedDate) {
        this.typedDate = typedDate;
    }

    public boolean isChatSent() {
        return chatSent;
    }

    public void setChatSent(boolean chatSent) {
        this.chatSent = chatSent;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

}
