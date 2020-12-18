package com.learning.mobilzlab.AdminRepairReuqest;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.Activities.AdminProblemDetails;
import com.learning.mobilzlab.Interfaces.UtilsHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.ScheduleBook.RepairRequest;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

import java.util.ArrayList;
import java.util.List;

import static com.learning.mobilzlab.Utils.ScheduleBookUtils.FORM_REFERENCE;

public class RepairRequestCore implements UtilsHooks {

    List<RepairRequest> requestRepairList;
    Activity homeScreen;
    private RecyclerView mRequestRepairRecycler;
    private RepairRequestAdapter adapter;

    public RepairRequestCore(Activity homeScreen) {
        this.homeScreen = homeScreen;
    }


    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();

    }

    @Override
    public void initializeViews() {

        mRequestRepairRecycler = homeScreen.findViewById(R.id.RVAllScheduleRepairRecyclers);

        adapter = new RepairRequestAdapter(this);


    }

    @Override
    public void initializeListeners() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(homeScreen);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);


        requestRepairList = new ArrayList<>();

        mRequestRepairRecycler.setLayoutManager(linearLayoutManager);
        mRequestRepairRecycler.setAdapter(adapter);


    }

    @Override
    public void startProcessing() {

        getAllRequestedRepairs();
     }

    void getAllRequestedRepairs() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FORM_REFERENCE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot request : snapshot.getChildren()) {


                        RepairRequest requestRepair = request.getValue(RepairRequest.class);
                        requestRepairList.add(requestRepair);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(homeScreen, "Error while fetching repair requests", Toast.LENGTH_SHORT).show();

            }
        });
    }


    void moveToDetails(int position) {

        Intent intent = new Intent(homeScreen, AdminProblemDetails.class);
        intent.putExtra(DataSharedPrefs.REPAIR_INTENT_KEY, requestRepairList.get(position));
        homeScreen.startActivity(intent);
    }


}
