package com.example.mydrivertracking.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREFS = "session_prefs";
    private static final String KEY_TOKEN = "token";

    private final SharedPreferences sp;

    public SessionManager(Context ctx) {
        sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        sp.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return sp.getString(KEY_TOKEN, null);
    }

    public void clearSession() {
        sp.edit().clear().apply();
    }
}
