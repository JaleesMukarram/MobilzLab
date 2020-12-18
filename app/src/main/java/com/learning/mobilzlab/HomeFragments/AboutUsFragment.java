package com.learning.mobilzlab.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.DialogUtils;

public class AboutUsFragment extends Fragment implements ActivityCustomHooks {

    private View view;
    private ImageView logo;
    private int clickCounter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_about_us_layout, container);

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

        logo = view.findViewById(R.id.TVFragmentAboutUsAboutUsImage);


    }

    @Override
    public void initializeListeners() {

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickCounter++;

                if (clickCounter == 5) {

                    clickCounter = 0;

                    goToAdminMode();
                }


            }
        });

    }

    private void goToAdminMode() {

        DialogUtils utils = new DialogUtils(getActivity(), DialogUtils.MODE_DIAL_PASSWORD);
        utils.callHooks();

    }


    @Override
    public void startProcessing() {

    }
}
