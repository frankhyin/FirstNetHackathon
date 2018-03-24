package com.example.jennadeng.contextfirst;

import android.content.Context;
import android.content.SharedPreferences;

public class KeyValueDB {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    public KeyValueDB() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getLocation(Context context) {
        return getPrefs(context).getString("location_key", "default_location");
    }

    public static void setLocation(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("location_key", input);
        editor.commit();
    }
}