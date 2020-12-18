package com.learning.mobilzlab.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class DataSharedPrefs {

    public static final int ADMIN_ID = 420420;

    public static final String SENDER_INTENT_KEY = "sender";
    public static final String RECEIVER_INTENT_KEY = "receiver";


    private static final String USER_DEFAULT_PREFERENCES = "user_default";
    private static final String USER_KEY = "user";

    public static final String REPAIR_INTENT_KEY = "repair";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public DataSharedPrefs(Context context) {
        this.context = context;

        this.sharedPreferences = context.getSharedPreferences(USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

    }

    public int getUserID() {

        int id = sharedPreferences.getInt(USER_KEY, 1);

        if (id == 1) {

            setNewUser();
            id = sharedPreferences.getInt(USER_KEY, 1);
        }

        return id;
    }

    public void setNewUser() {

        Random random = new Random();
        int id = 100000 + random.nextInt(899999);
        editor.putInt(USER_KEY, id);
        editor.commit();

    }


}
