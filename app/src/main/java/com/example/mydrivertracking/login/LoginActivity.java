package com.example.mydrivertracking.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mydrivertracking.R;
import com.example.mydrivertracking.api.ApiClient;
import com.example.mydrivertracking.api.ApiService;
import com.example.mydrivertracking.dashboard.DashboardActivity;
import com.example.mydrivertracking.models.AuthResponse;
import com.example.mydrivertracking.models.User;
import com.example.mydrivertracking.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister; // asegúrate de tener este id en el XML
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister); // o cambia por tvRegisterLink si ese es tu id

        sessionManager = new SessionManager(this);

        btnLogin.setOnClickListener(v -> doLogin());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }

    private void doLogin() {
        String email = etEmail.getText().toString().trim();
        String clave  = etPassword.getText().toString().trim();

        if (email.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        User body = new User("", email, clave);
        ApiService api = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        api.login(body).enqueue(new Callback<AuthResponse>() {
            @Override public void onResponse(Call<AuthResponse> call, Response<AuthResponse> res) {
                if (res.isSuccessful() && res.body() != null && res.body().getToken() != null) {
                    sessionManager.saveToken(res.body().getToken());
                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    String msg = "Credenciales inválidas";
                    try {
                        if (res.errorBody() != null) msg = res.errorBody().string();
                    } catch (Exception ignored) {}
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,
                        t.getClass().getSimpleName() + ": " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
