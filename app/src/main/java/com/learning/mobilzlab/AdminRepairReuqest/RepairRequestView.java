package com.learning.mobilzlab.AdminRepairReuqest;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Activities.AdminProblemDetails;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.ScheduleBook.RepairRequest;

import java.util.Date;

public class RepairRequestView extends RecyclerView.ViewHolder {

    private TextView nameTV;
    private TextView dateTV;
    private TextView mobileTV;

    private View repairView;

    private RepairRequestCore repairRequestCore;

    public RepairRequestView(@NonNull View itemView, RepairRequestCore repairRequestCore) {
        super(itemView);

        this.repairRequestCore = repairRequestCore;

        nameTV = itemView.findViewById(R.id.TVSingleRepairRequestNameShowing);
        dateTV = itemView.findViewById(R.id.TVSingleRepairRequestTimeShowing);
        mobileTV = itemView.findViewById(R.id.TVSingleRepairRequestMobileShowing);

        repairView = itemView.findViewById(R.id.CVSingleRepairRequestRequestContainer);

    }

    void setData(int position){

        RepairRequest repairRequest = repairRequestCore.requestRepairList.get(position);

        nameTV.setText(repairRequest.getmUserName());
        dateTV.setText(getRelativeDate(repairRequest.getSendDate()));
        mobileTV.setText(repairRequest.getmUserMobile());

        setListener(position);

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

    public void setListener(final int position){

        repairView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repairRequestCore.moveToDetails(position);

            }
        });
    }

}
