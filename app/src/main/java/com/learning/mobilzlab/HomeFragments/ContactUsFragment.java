package com.learning.mobilzlab.HomeFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.learning.mobilzlab.Activities.ClientForm;
import com.learning.mobilzlab.Activities.Gallery;
import com.learning.mobilzlab.Activities.SendChat;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.learning.mobilzlab.Utils.DataSharedPrefs;

import java.util.Objects;

import static com.learning.mobilzlab.Utils.DataSharedPrefs.RECEIVER_INTENT_KEY;
import static com.learning.mobilzlab.Utils.DataSharedPrefs.SENDER_INTENT_KEY;

public class ContactUsFragment extends Fragment implements ActivityCustomHooks {

    public static final int CALL_PERMISSION_REQUEST = 1;
    public static final String NUMBER = "+9200-5338880";

    private View view;
    private Button callBTN;
    private Button liveChatBTN;
    private Button repairRequestBTN;
    private Button youtubeBTN;
    private Button galleryBTN;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact_us_layout, container);
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

        callBTN = view.findViewById(R.id.BTNContactWaysCallOwner);
        liveChatBTN = view.findViewById(R.id.BTNContactWaysLiveChat);
        repairRequestBTN = view.findViewById(R.id.BTNContactWaysScheduleRepair);

        youtubeBTN = view.findViewById(R.id.BTNContactWaysYoutubeChannel);
        galleryBTN = view.findViewById(R.id.BTNContactWaysGalleryeChannel);

    }

    @Override
    public void initializeListeners() {

        callBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkIfCallPermissionIsGranted()) {

                    makeCall();

                } else {

                    getCallPermission();

                }


            }
        });

        liveChatBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToChat();
            }
        });


        repairRequestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), ClientForm.class));
            }
        });

        youtubeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToYoutubeIntent();

            }
        });

        galleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGallery();
            }
        });


    }

    private void goToGallery() {

        Intent intent = new Intent(getActivity(), Gallery.class);
        startActivity(intent);
    }


    @Override
    public void startProcessing() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CALL_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            makeCall();

        } else {

            Toast.makeText(getContext(), "Call Permission denied", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkIfCallPermissionIsGranted() {

        // If device is on Marshmallow or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // If permission for using camera is not granted
            return Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        }

        return true;
    }

    private void getCallPermission() {

        // If device is on Marshmallow or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] permissions = {Manifest.permission.CALL_PHONE};
            Objects.requireNonNull(getActivity()).requestPermissions(permissions, CALL_PERMISSION_REQUEST);


        }
    }

    private void makeCall() {

        String uri = "tel: " + NUMBER;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);

    }

    private void goToChat() {

        DataSharedPrefs sharedPrefs = new DataSharedPrefs(Objects.requireNonNull(getActivity()));

        int userID = sharedPrefs.getUserID();
        int adminID = DataSharedPrefs.ADMIN_ID;

        Intent intent = new Intent(getActivity(), SendChat.class);

        intent.putExtra(SENDER_INTENT_KEY, userID);
        intent.putExtra(RECEIVER_INTENT_KEY, adminID);

        startActivity(intent);
    }

    private void goToYoutubeIntent() {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCE7-35aiztMwgKcqocbEXiw"));
        startActivity(intent);
    }

}
