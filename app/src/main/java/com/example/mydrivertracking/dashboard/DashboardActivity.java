package com.example.mydrivertracking.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mydrivertracking.R;
import com.example.mydrivertracking.login.LoginActivity;
import com.example.mydrivertracking.utils.SessionManager;

public class DashboardActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnLogout = findViewById(R.id.btnLogout);
        SessionManager sessionManager = new SessionManager(this);

        btnLogout.setOnClickListener(v -> {
            sessionManager.clearSession();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}
