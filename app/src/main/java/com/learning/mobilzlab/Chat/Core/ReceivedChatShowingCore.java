package com.learning.mobilzlab.Chat.Core;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.learning.mobilzlab.Chat.Firebase.FirebaseGlobals;
import com.learning.mobilzlab.Chat.Modals.RepairUser;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ReceivedChatShowingCore implements UtilsHooks {

    public static final String TAG = "AdminAllChatsView";


    public List<RepairUser> repairUserList;
    RecyclerView recyclerView;
    UsersChatAdapter adapter;

    List<Integer> chatIDsList;
    public Activity homeScreen;
    private ImageView BackButton;
    private int RECEIVING_USER;
    private int QUERY_ID;
    private String name;
    private String email;

    public ReceivedChatShowingCore(Activity homeScreen) {
        this.homeScreen = homeScreen;

        this.repairUserList = new ArrayList<>();
        this.chatIDsList = new ArrayList<>();
    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();

    }

    @Override
    public void initializeViews() {

        BackButton = homeScreen.findViewById(R.id.IVAdminAllChatShowingBackButton);
        recyclerView = homeScreen.findViewById(R.id.RVAdminAllChatShowingAllChats);

        adapter = new UsersChatAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(homeScreen);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        repairUserList = new ArrayList<>();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void initializeListeners() {
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen.onBackPressed();
            }
        });

    }

    @Override
    public void startProcessing() {

        getAllChats();


    }

    private void getAllChats() {

        Log.d(TAG, "Getting firebase queryid: " + QUERY_ID);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseGlobals.Database.QUERY_CHAT_REFERENCE);

        reference.addValueEventListener(new ConstantRepairUsersListener(this));

    }

    void sortAllChats() {

        Collections.sort(repairUserList, new Comparator<RepairUser>() {
            @Override
            public int compare(RepairUser repairUser, RepairUser t1) {

                return t1.getLastMessageDate().compareTo(repairUser.getLastMessageDate());
            }
        });

        adapter.notifyDataSetChanged();


    }

    public String getRelativeDate(Date date) {

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;

        Date currentDate = new Date();


        long difference = currentDate.getTime() - date.getTime();

        if (difference > day) {

            return ((int) (Math.floor(1f * difference / day))) + "d";

        } else if (difference > hour) {

            return ((int) (Math.floor(1f * difference / hour))) + "h";

        } else if (difference > minute) {

            return ((int) (Math.floor(1f * difference / minute))) + "m";
        } else {

            int diff = ((int) (Math.floor(1f * difference / second)));

            if (diff > 0) {

                return diff + "s";

            } else {

                return "now";
            }
        }

    }


}
