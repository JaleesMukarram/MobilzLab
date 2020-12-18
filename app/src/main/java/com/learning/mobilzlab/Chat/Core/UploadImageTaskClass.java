package com.learning.mobilzlab.Chat.Core;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.learning.mobilzlab.Chat.Firebase.FirebaseGlobals;
import com.learning.mobilzlab.Chat.Interface.FirebaseUploadListener;
import com.learning.mobilzlab.Chat.Interface.ObjectChatUploadInterface;
import com.learning.mobilzlab.Chat.Modals.ImageChat;


public class UploadImageTaskClass implements ObjectChatUploadInterface {

    public static final String TAG= "SendChatImageUploadTAG";

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseGlobals.Database.QUERY_CHAT_REFERENCE);
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference(FirebaseGlobals.Storage.IMAGE_STORAGE_REFERENCE);
    private FirebaseUploadListener listener;
    private ImageChat imageChat;


    public UploadImageTaskClass(ImageChat imageChat) {

        this.imageChat = imageChat;
        this.imageChat.setProgress(100);
    }

    public void setListener(FirebaseUploadListener listener) {
        this.listener = listener;
        uploadObjectToStorage();
    }

    @Override
    public void uploadObjectToStorage() {

        storageReference.child(imageChat.getChatID() + ".jpg")
                .putFile(Uri.parse(imageChat.getImageUri()))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        getObjectURI();
                        Log.d(TAG, "Image uploaded successfully");

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        onObjectUploadFailed();
                        Log.d(TAG, "Exception: " + e);

                    }
                })

                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        onObjectUploadFailed();
                        Log.d(TAG, "Canceled");

                    }
                });

    }

    @Override
    public void getObjectURI() {

        listener.progress(30);

        storageReference.child(imageChat.getChatID() + ".jpg")
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Log.d(TAG, "URI got " + uri);
                        imageChat.setImageUri(uri.toString());
                        onObjectUploadSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "URI failed");
                        onObjectUploadFailed();

                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        Log.d(TAG, "URI failed");
                        onObjectUploadFailed();

                    }
                });


    }

    @Override
    public void onObjectUploadSuccess() {

        uploadObjectChatToDatabase();
        listener.progress(60);

    }

    @Override
    public void onObjectUploadFailed() {

        listener.onTaskUploadFailed();

    }

    @Override
    public void uploadObjectChatToDatabase() {

        databaseReference.child(String.valueOf(imageChat.getChatID()))
                .setValue(imageChat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "Image Chat uploaded completely");
                        onObjectChatUploadSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "Image chat uploaded failed");
                        onObjectChatUploadFailed();

                    }
                })

                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        Log.d(TAG, "Image chat uploaded failed");
                        onObjectChatUploadFailed();

                    }
                });

    }

    @Override
    public void onObjectChatUploadSuccess() {

        listener.progress(100);
        listener.onTaskUploadSuccess();
    }

    @Override
    public void onObjectChatUploadFailed() {

        listener.onTaskUploadFailed();

    }
}