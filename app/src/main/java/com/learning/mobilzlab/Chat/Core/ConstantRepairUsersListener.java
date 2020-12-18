package com.learning.mobilzlab.Chat.Core;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.Chat.Modals.Chat;
import com.learning.mobilzlab.Chat.Modals.ImageChat;
import com.learning.mobilzlab.Chat.Modals.MessageChat;
import com.learning.mobilzlab.Chat.Modals.RepairUser;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

import java.util.Date;

class ConstantRepairUsersListener implements ValueEventListener {

    private static final String TAG = "AdminAllChatsView";

    private ReceivedChatShowingCore receivedChatShowingCore;

    public ConstantRepairUsersListener(ReceivedChatShowingCore receivedChatShowingCore) {
        this.receivedChatShowingCore = receivedChatShowingCore;
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.exists()) {

            for (DataSnapshot chat : dataSnapshot.getChildren()) {

                Log.d(TAG, "CHAT SNAPSHOT ADDED: " + chat);

                int receivingUserID = getReceivingUserID(chat);
                int chatType = getChatType(chat);


                if (receivingUserID == DataSharedPrefs.ADMIN_ID) {

                    ThisChatSnapshotIsForMe(chat, chatType);

                }
            }
        }
    }


    private void ThisChatSnapshotIsForMe(DataSnapshot snapshot, int chatType) {

        if (chatType == Chat.CHAT_TYPE_MESSAGE) {

            MessageChat messageChat = snapshot.getValue(MessageChat.class);

            assert messageChat != null;

            if (checkIfChatAvailableInList(messageChat.getChatID())) {
                return;
            }

            receivedChatShowingCore.chatIDsList.add(messageChat.getChatID());

            int index = getIndexOfThisUserInList(messageChat.getSendingUserID());

            if (index != -1) {

                thisUserChatIsAdditional(messageChat, index);

            } else {

                thisUserChatIsNew(messageChat);

            }


        } else if (chatType == Chat.CHAT_TYPE_IMAGE) {

            ImageChat imageChat = snapshot.getValue(ImageChat.class);
            assert imageChat != null;

            if (checkIfChatAvailableInList(imageChat.getChatID())) {
                return;
            }

            receivedChatShowingCore.chatIDsList.add(imageChat.getChatID());


            int index = getIndexOfThisUserInList(imageChat.getSendingUserID());
            if (index != -1) {
                thisUserChatIsAdditional(imageChat, index);
            } else {
                thisUserChatIsNew(imageChat);
            }

        }


        receivedChatShowingCore.sortAllChats();

    }

    private void thisUserChatIsAdditional(Chat chat, int index) {

        RepairUser repairUser = receivedChatShowingCore.repairUserList.get(index);
        repairUser.incrementTotalMessages();

        if (repairUser.getLastMessageDate().before(chat.getTypedDate())) {

            repairUser.setLastMessageDate(chat.getTypedDate());
        }

        if (checkIfChatIsUnread(chat)) {

            repairUser.setUnreadMessage(true);
        }
        receivedChatShowingCore.sortAllChats();
     }

    // If the news user message is new
    private void thisUserChatIsNew(Chat chat) {

        RepairUser repairUser = new RepairUser(chat.getSendingUserID(), checkIfChatIsUnread(chat), chat.getTypedDate());
        receivedChatShowingCore.repairUserList.add(repairUser);


        receivedChatShowingCore.sortAllChats();


    }

    // checking if this user message is in list
    private int getIndexOfThisUserInList(int sendingUserID) {

        for (int i = 0; i < receivedChatShowingCore.repairUserList.size(); i++) {

            if (receivedChatShowingCore.repairUserList.get(i).getUserID() == sendingUserID) {

                return i;
            }
        }

        return -1;
    }

    private boolean checkIfChatIsUnread(Chat chat) {

        Date readDate = chat.getReadDate();
        return readDate == null;

    }

    private boolean checkIfChatAvailableInList(int chatID) {

        for (int i : receivedChatShowingCore.chatIDsList) {

            if (i == chatID) {
                return true;
            }
        }


        return false;
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


    // Helping Methods
    private int getChatType(DataSnapshot chat) {

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("chatType".equals(attribute.getKey())) {

                return (int) ((long) attribute.getValue());

            }
        }

        return 0;

    }

    private int getReceivingUserID(DataSnapshot chat) {

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("receivingUserID".equals(attribute.getKey())) {

                return (int) ((long) attribute.getValue());
            }
        }

        return 0;
    }

}
