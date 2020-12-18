package com.learning.mobilzlab.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.BrandRecycler;
import com.learning.mobilzlab.Utils.SliderUtils;

public class HomeFragment extends Fragment implements ActivityCustomHooks {

    private View view;
    private SliderUtils mSliderUtils;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_layout, container, false);
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

        mSliderUtils = new SliderUtils(getActivity(), view);
        mSliderUtils.initializeComponents();

        RecyclerView mBrandRecycler = view.findViewById(R.id.RVHomeFragmentAllBrandsRecycler);
        BrandRecycler recycler = new BrandRecycler(mBrandRecycler, getActivity());
        recycler.startShowing();


    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void startProcessing() {

    }


}
