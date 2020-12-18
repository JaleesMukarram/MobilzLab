package com.learning.mobilzlab.Chat.Core;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.learning.mobilzlab.BuildConfig;
import com.learning.mobilzlab.Chat.Firebase.FirebaseGlobals;
import com.learning.mobilzlab.Chat.Interface.ActivityCommunication;
import com.learning.mobilzlab.Chat.Interface.FirebaseUploadListener;
import com.learning.mobilzlab.Chat.Modals.Chat;
import com.learning.mobilzlab.Chat.Modals.ImageChat;
import com.learning.mobilzlab.Chat.Modals.MessageChat;
import com.learning.mobilzlab.Interfaces.ActivityCustomHooks;
import com.learning.mobilzlab.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.learning.mobilzlab.Chat.Constants.GemeralConstants.CAMERA_AND_IMAGE_BOTH;
import static com.learning.mobilzlab.Chat.Constants.GemeralConstants.CAMERA_IMAGE_CAPTURE_ACTIVITY_RESULT;

public class ChatCore implements ActivityCustomHooks, ActivityCommunication {


    public static final String TAG = "SendChatCoreTAG";
    public ChatAdapter adapter;
    public Activity homeActivity;

    int SENDING_USER;
    int RECEIVING_USER;

    SoundPool player;
    int chatReceivedSound;
    List<Chat> chatList;
    Uri imageUri;
    FirebaseDatabase firebaseDatabase;
    boolean firstMessageSent;
    ImageView SendMessageIV;
    ImageView OpenCameraIV;
    ImageView BackButton;
    TextView TitleShowing;
    EditText EnterMessage;
    RecyclerView chatRecycler;
    private DatabaseReference databaseReferenceChat;


    public ChatCore(Activity homeActivity, int SENDING_USER, int RECEIVING_USER) {

        this.homeActivity = homeActivity;
        this.SENDING_USER = SENDING_USER;
        this.RECEIVING_USER = RECEIVING_USER;
    }

    @Override
    public void callHooks() {

        initializeViews();
        initializeListeners();
        startProcessing();
    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void initializeViews() {

        SendMessageIV = homeActivity.findViewById(R.id.IVSendMessage);
        OpenCameraIV = homeActivity.findViewById(R.id.IVStartCamera);
        TitleShowing = homeActivity.findViewById(R.id.TVSelectQueryShowing);

        BackButton = homeActivity.findViewById(R.id.IVBackButton);

        EnterMessage = homeActivity.findViewById(R.id.TVEnteredMessage);

        chatRecycler = homeActivity.findViewById(R.id.RVAllChatsRecyclerShowing);


        initializeSoundPool();

        adapter = new ChatAdapter(this);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReferenceChat = firebaseDatabase.getReference(FirebaseGlobals.Database.QUERY_CHAT_REFERENCE);


    }

    @Override
    public void initializeListeners() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(homeActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);


        chatList = new ArrayList<>();

        chatRecycler.setLayoutManager(linearLayoutManager);
        chatRecycler.setAdapter(adapter);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });


        SendMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Send button clicked");
                handleNewTextSendMessage();

            }
        });

        OpenCameraIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Camera request clicked");
                requestCameraCall();
            }
        });


    }

    @Override
    public void startProcessing() {

        getChatsFromFirebase();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_AND_IMAGE_BOTH) {

            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                openCameraForImage();

            } else {

                Toast.makeText(homeActivity, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_IMAGE_CAPTURE_ACTIVITY_RESULT && resultCode == RESULT_OK) {

            ImageChat imageChat = new ImageChat(SENDING_USER, RECEIVING_USER, imageUri.toString());
            chatList.add(imageChat);
            adapter.notifyDataSetChanged();
            chatRecycler.smoothScrollToPosition(chatList.size());

            uploadImageChatToDatabase(imageChat);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void initializeSoundPool() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        player = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(1)
                .build();

        chatReceivedSound = player.load(homeActivity, R.raw.chat_sound, 1);

    }

    void playSound() {

        if (player != null) {

            player.play(chatReceivedSound, 0.5f, 0.5f, 1, 0, 1);
        }
    }

    // Sending Messages Chat
    void handleNewTextSendMessage() {

        Log.d(TAG, "Text handling");

        String message = EnterMessage.getText().toString().trim();

        if (message.length() == 0) {

            Toast.makeText(homeActivity, "Please enter some text", Toast.LENGTH_SHORT).show();

        } else {


            Log.d(TAG, "Text Received");

            firstMessageSent = true;

            MessageChat messageChat = new MessageChat(SENDING_USER, RECEIVING_USER, message);

            chatList.add(messageChat);

            uploadThisMessageChatToDatabase(messageChat);

            adapter.notifyDataSetChanged();
            chatRecycler.smoothScrollToPosition(chatList.size());

            EnterMessage.setText("");

        }
    }

    public void uploadThisMessageChatToDatabase(final MessageChat chat) {


        databaseReferenceChat.child(String.valueOf(chat.getChatID())).setValue(chat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        setThisChatAsSent(chat.getChatID(), chat.getTypedDate());

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "Issue: "+e);
                         setThisChatAsFailed(chat.getChatID());

                    }
                })

                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        setThisChatAsFailed(chat.getChatID());

                    }
                });

    }

    void setThisChatAsSent(int chatID, Date typedDate) {

        for (int i = 0; i < chatList.size(); i++) {

            if (chatList.get(i).getChatID() == chatID) {

                chatList.get(i).setSentDate(typedDate);
                Log.d(TAG, "Succeeded one found");
                break;

            }
        }

        adapter.notifyDataSetChanged();
    }

    void setThisChatAsFailed(int chatID) {

        for (int i = 0; i < chatList.size(); i++) {

            if (chatList.get(i).getChatID() == chatID) {

                chatList.get(i).setChatSent(false);
                Log.d(TAG, "Failed one found");
                break;

            }
        }

        adapter.notifyDataSetChanged();
    }

    // Sending Image Chat
    public void uploadImageChatToDatabase(final ImageChat imageChat) {

        firstMessageSent = true;

        try {

            ImageChat imageChaToSend = (ImageChat) imageChat.clone();

            imageChat.setProgress(0);

            UploadImageTaskClass imageTaskClass = new UploadImageTaskClass(imageChaToSend);
            imageTaskClass.setListener(new FirebaseUploadListener() {
                @Override
                public void onTaskUploadSuccess() {

                    setThisChatAsSent(imageChat.getChatID(), imageChat.getTypedDate());

                }

                @Override
                public void onTaskUploadFailed() {

                    setThisChatAsFailed(imageChat.getChatID());

                }

                @Override
                public void progress(int progress) {

                    adapter.notifyDataSetChanged();
                    imageChat.setProgress(progress);
                }
            });

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    // Receiving Messages
    void getChatsFromFirebase() {

        databaseReferenceChat
                .addChildEventListener(new ConstantChildEventListenerForChats(this));

    }

    void sortAllChats() {

        Collections.sort(chatList, new Comparator<Chat>() {
            @Override
            public int compare(Chat chat, Chat t1) {

                return chat.getTypedDate().compareTo(t1.getTypedDate());
            }
        });

        adapter.notifyDataSetChanged();


    }

    void thisAddedChatSnapshotIsForMe(DataSnapshot chat) {

        int chatType = 0;

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("chatType".equals(attribute.getKey())) {

                chatType = (int) ((long) attribute.getValue());
                break;
            }
        }

        if (firstMessageSent) {

            playSound();
        }

        if (chatType == Chat.CHAT_TYPE_MESSAGE) {

            addThisMessageChatFromDatabaseToLocal(chat);

        } else if (chatType == Chat.CHAT_TYPE_IMAGE) {

            addThisImageChatFromDatabaseToLocal(chat);

        }
    }

    void addThisMessageChatFromDatabaseToLocal(DataSnapshot chat) {

        MessageChat messageChat = chat.getValue(MessageChat.class);

        try {

            assert messageChat != null;
            messageChat.setSentDate(messageChat.getTypedDate());

            chatList.add(messageChat);
            chatRecycler.smoothScrollToPosition(chatList.size());
            setAllMessagesAsRead();


        } catch (Exception ignored) {

        }

        sortAllChats();

    }

    void addThisImageChatFromDatabaseToLocal(DataSnapshot chat) {

        ImageChat imageChat = chat.getValue(ImageChat.class);

        try {

            assert imageChat != null;
            imageChat.setSentDate(imageChat.getTypedDate());
            chatList.add(imageChat);
            chatRecycler.smoothScrollToPosition(chatList.size());

            setAllMessagesAsRead();


        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "Problem while");
        }

        sortAllChats();
    }

    //Deleted
    void thisDeletedSnapShotIsForMe(DataSnapshot chat) {

        Chat chat1 = chat.getValue(Chat.class);

        if (chat1 != null) {

            for (int i = 0; i < chatList.size(); i++) {

                if (chat1.getChatID() == chatList.get(i).getChatID()) {

                    chatList.remove(i);
                    Log.d(TAG, "LOCAL COPY REMOVED");
                    break;
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    // Status read
    void setAllMessagesAsRead() {

        for (Chat c : chatList) {

            if (c.getSendingUserID() == RECEIVING_USER) {

                if (c.getReadDate() == null) {

                    setThisChatSeenStatusAsTrue(c);
                }
            }
        }
    }

    void setThisChatSeenStatusAsTrue(final Chat c) {


        final Date readDate = new Date();
        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("readDate", readDate);
        databaseReferenceChat.child(String.valueOf(c.getChatID())).updateChildren(dateMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                if (databaseError == null) {

                    c.setReadDate(readDate);
                }
            }
        });


    }

    // Updated
    void thisUpdatedSnapshotIsForMe(DataSnapshot chat) {

        int chatType = 0;

        for (DataSnapshot attribute : chat.getChildren()) {

            if ("chatType".equals(attribute.getKey())) {

                chatType = (int) ((long) attribute.getValue());
                break;
            }
        }

        if (chatType == Chat.CHAT_TYPE_MESSAGE) {

            updateThisMessageChatFromDatabaseToLocal(chat);

        } else if (chatType == Chat.CHAT_TYPE_IMAGE) {

            updateThisImageChatFromDatabaseToLocal(chat);

        }
    }

    void updateThisMessageChatFromDatabaseToLocal(DataSnapshot chat) {

        MessageChat messageChat = chat.getValue(MessageChat.class);
        assert messageChat != null;
        messageChat.setSentDate(messageChat.getTypedDate());

        int chatID = messageChat.getChatID();

        for (int i = 0; i < chatList.size(); i++) {

            if (chatID == chatList.get(i).getChatID()) {

                chatList.set(i, messageChat);
                Log.d(TAG, messageChat.getMessage() + " is updated successfully");
                break;
            }
        }

        adapter.notifyDataSetChanged();
    }

    void updateThisImageChatFromDatabaseToLocal(DataSnapshot chat) {

        ImageChat imageChat = chat.getValue(ImageChat.class);
        assert imageChat != null;
        imageChat.setSentDate(imageChat.getTypedDate());

        int chatID = imageChat.getChatID();

        for (int i = 0; i < chatList.size(); i++) {

            if (chatID == chatList.get(i).getChatID()) {

                chatList.set(i, imageChat);
                Log.d(TAG, imageChat.getImageUri() + " is updated successfully");
                break;
            }
        }

        adapter.notifyDataSetChanged();
    }


    // Others
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

    void requestCameraCall() {

        // If device is on Marshmallow or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // If permission for using camera is not granted
            if (homeActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    homeActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(homeActivity, permissions, CAMERA_AND_IMAGE_BOTH);
//                homeActivity.requestPermissions(permissions, CAMERA_AND_IMAGE_BOTH);

            }
            // Permission already granted
            else {

                openCameraForImage();

            }
        }
        // System less than Marshmallow
        else {

            openCameraForImage();

        }

    }

    void openCameraForImage() {

        if (FirebaseGlobals.makeImageDirectory()) {

            File capturedImageFile = new File(FirebaseGlobals.Directory.LOCAL_IMAGE_DIRECTORY + "/" + System.currentTimeMillis() + ".jpg");

            imageUri = FileProvider.getUriForFile(homeActivity, BuildConfig.APPLICATION_ID + ".provider", capturedImageFile);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            homeActivity.startActivityForResult(cameraIntent, CAMERA_IMAGE_CAPTURE_ACTIVITY_RESULT);

        } else {

            Toast.makeText(homeActivity, "Error while making Image directory", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteThisChatFromDatabase(int chatID) {

        DatabaseReference reference = firebaseDatabase.getReference(FirebaseGlobals.Database.QUERY_CHAT_REFERENCE);
        reference.child(String.valueOf(chatID)).removeValue();

    }

    @SuppressLint("InflateParams")
    public void fullScreenImageShowingDialogue(String uri) {

        View view = homeActivity.getLayoutInflater().inflate(R.layout.view_full_screen_image_showing, null);
        ImageView imageView = view.findViewById(R.id.IVFullScreenImageShowing);

        Picasso.get().load(uri).into(imageView);

        Dialog fullImageDialogue = new Dialog(homeActivity, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        fullImageDialogue.setContentView(view);
        fullImageDialogue.show();

    }





}
