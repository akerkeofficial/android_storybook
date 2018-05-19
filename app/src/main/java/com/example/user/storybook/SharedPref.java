package com.example.user.storybook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by USER on 29.04.2017.
 */

public class SharedPref {
    private static SharedPref sharePref = new SharedPref();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public SharedPref() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static SharedPref getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("Data", Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void saveData(String type,String placeObjStr) {
        editor.putString(type, placeObjStr);
        editor.commit();
    }

    public String getData(String type) {
        return sharedPreferences.getString(type, "");
    }

    public void removeData(String type) {
        editor.remove(type);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
