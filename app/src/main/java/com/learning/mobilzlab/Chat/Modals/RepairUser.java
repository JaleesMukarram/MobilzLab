package com.learning.mobilzlab.Chat.Modals;

import java.util.Date;

public class RepairUser {

    private int UserID;
    private int totalMessages;
    private boolean unreadMessage;

    private Date lastMessageDate;

    public RepairUser() {
    }

    public RepairUser(int userID, boolean unreadMessage, Date lastMessageDate) {
        UserID = userID;
        this.unreadMessage = unreadMessage;
        this.lastMessageDate = lastMessageDate;
        this.totalMessages = 1;

    }


    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }


    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }

    public boolean isUnreadMessage() {
        return unreadMessage;
    }

    public void setUnreadMessage(boolean unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public void incrementTotalMessages() {this.totalMessages++; }
}
