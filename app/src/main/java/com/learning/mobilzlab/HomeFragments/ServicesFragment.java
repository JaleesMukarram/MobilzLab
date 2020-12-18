package com.learning.mobilzlab.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Feedback.FeedbackCore;
import com.learning.mobilzlab.Feedback.FeedbackDialogue;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.BrandRecycler;
import com.learning.mobilzlab.Utils.HideSlideUtils;

public class ServicesFragment extends Fragment implements ActivityCustomHooks {

    private View view;

    private RecyclerView mBrandRecycler;

    private Button sendFeedbackBTN;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_services_layout, container);

        callHooks();
        return view;
    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();

    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void initializeViews() {

        handleEvents();

        sendFeedbackBTN = view.findViewById(R.id.BTNFragmentServicesProvideFeedback);

        FeedbackCore feedbackCore = new FeedbackCore(getActivity(), view);
        feedbackCore.callHooks();

    }

    @Override
    public void initializeListeners() {

        RecyclerView mBrandRecycler = view.findViewById(R.id.RVServicesFragmentAllBrandsRecycler);
        BrandRecycler recycler = new BrandRecycler(mBrandRecycler, getActivity());
        recycler.startShowing();

        sendFeedbackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FeedbackDialogue feedbackDialogue = new FeedbackDialogue(getActivity());
                feedbackDialogue.callHooks();
            }
        });

    }

    @Override
    public void startProcessing() {

    }

    private void handleEvents(){

        View toHide = view.findViewById(R.id.RLFragmentServicesIPhoneDetailsShowing);
        View toClick = view.findViewById(R.id.RLFragmentServicesHideContainerIphone);
        ImageView icon = view.findViewById(R.id.IVFragmentServicesIPhoneButton);

        HideSlideUtils hideSlideUtils1 = new HideSlideUtils(toHide, icon, toClick, getActivity(), true);


        toHide = view.findViewById(R.id.RLFragmentServicesAndroidDetailsShowing);
        toClick = view.findViewById(R.id.RLFragmentServicesHideContainerAndroid);
        icon = view.findViewById(R.id.IVFragmentServicesAndroidButton);

        HideSlideUtils hideSlideUtils2 = new HideSlideUtils(toHide, icon, toClick, getActivity(), true);

        toHide = view.findViewById(R.id.RLFragmentServicesICloudDetailsShowing);
        toClick = view.findViewById(R.id.RLFragmentServicesHideContainerICloud);
        icon = view.findViewById(R.id.IVFragmentServicesICloudButton);

        HideSlideUtils hideSlideUtils3 = new HideSlideUtils(toHide, icon, toClick, getActivity(), true);


        toHide = view.findViewById(R.id.RLFragmentServicesFeedbackDetailsShowing);
        toClick = view.findViewById(R.id.RLFragmentServicesHideContainerFeedback);
        icon = view.findViewById(R.id.IVFragmentServicesFeedbackButton);

        HideSlideUtils hideSlideUtils4 = new HideSlideUtils(toHide, icon, toClick, getActivity(), true);

    }
}
