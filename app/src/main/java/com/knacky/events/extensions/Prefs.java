package com.knacky.events.extensions;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;

/**
 * Created by knacky on 24.05.2018.
 */
public class Prefs {

    public static void setUser(String user, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("user", user).apply();
    }

    public static void deleteUser(String user, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().apply();
    }

    public static String getUser(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("user", "");
    }

}
