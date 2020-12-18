package com.learning.mobilzlab.Chat.Interface;

public interface FirebaseUploadListener {

    void onTaskUploadSuccess();

    void onTaskUploadFailed();

    void progress(int progress);
}
