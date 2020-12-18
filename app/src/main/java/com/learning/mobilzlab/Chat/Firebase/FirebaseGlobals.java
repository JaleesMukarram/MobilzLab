package com.learning.mobilzlab.Chat.Firebase;

import android.os.Environment;

import java.io.File;

public class FirebaseGlobals {

    private static final String CHAT_REFERENCE = "CHAT_REFERENCE/";

    public static boolean makeImageDirectory() {

        if (new File(Directory.LOCAL_IMAGE_DIRECTORY).exists()) {
            return true;
        }

        File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + Directory.APP_FOLDER_NAME + "/" + Directory.IMAGE_FOLDER);
        return file.mkdirs();

    }

    private static boolean makeAppFolder() {

        return new File(Environment.getExternalStorageDirectory().toString() + "/" + Directory.APP_FOLDER_NAME).mkdir();

    }

    public static class Database {

        public static final String QUERY_CHAT_REFERENCE = CHAT_REFERENCE + "MESSAGE_CHAT_REFERENCE";

    }

    public static class Storage {

        public static final String IMAGE_STORAGE_REFERENCE = CHAT_REFERENCE + "IMAGE_CHAT_STORAGE_REFERENCE";

    }

    public static class Directory {

        public static final String APP_FOLDER_NAME = "Mobilz Lab";
        public static final String IMAGE_FOLDER = "Images";

        public static final String LOCAL_IMAGE_DIRECTORY = Environment.getExternalStorageDirectory().toString() + "/" + APP_FOLDER_NAME + "/" + IMAGE_FOLDER;

    }

}
