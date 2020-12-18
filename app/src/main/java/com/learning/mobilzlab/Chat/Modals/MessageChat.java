package com.learning.mobilzlab.Chat.Modals;

import java.util.Date;

public class MessageChat extends Chat {

    private String message;

    public MessageChat() {
    }

    public MessageChat(  int sendingUserID, int receivingUserID, String message) {
        super( sendingUserID, receivingUserID, CHAT_TYPE_MESSAGE);
        this.message = message;
    }

    public MessageChat(  int sendingUserID, int receivingUserID, Date sentDate, Date receivedDate, Date readDate, String message) {
        super( sendingUserID, receivingUserID, sentDate, readDate);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
