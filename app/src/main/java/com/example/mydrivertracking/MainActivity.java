package com.example.mydrivertracking;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mydrivertracking.dashboard.DashboardActivity;
import com.example.mydrivertracking.login.LoginActivity;
import com.example.mydrivertracking.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sm = new SessionManager(this);
        Intent next = (sm.getToken() != null && !sm.getToken().isEmpty())
                ? new Intent(this, DashboardActivity.class)
                : new Intent(this, LoginActivity.class);

        next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(next);
        finish();
    }
}
