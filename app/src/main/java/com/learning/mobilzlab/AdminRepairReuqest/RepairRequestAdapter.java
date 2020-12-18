package com.learning.mobilzlab.AdminRepairReuqest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.R;


public class RepairRequestAdapter extends RecyclerView.Adapter<RepairRequestView> {

    RepairRequestCore repairRequestCore;

    public RepairRequestAdapter(RepairRequestCore repairRequestCore) {
        this.repairRequestCore = repairRequestCore;
    }

    @NonNull
    @Override
    public RepairRequestView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_repair_request_view, parent, false);

        return new RepairRequestView(view, repairRequestCore);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairRequestView holder, int position) {

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return repairRequestCore.requestRepairList.size();
    }
}


