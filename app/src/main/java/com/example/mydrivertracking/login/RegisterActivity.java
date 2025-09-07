package com.example.mydrivertracking.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mydrivertracking.R;
import com.example.mydrivertracking.api.ApiClient;
import com.example.mydrivertracking.api.ApiService;
import com.example.mydrivertracking.models.AuthResponse;
import com.example.mydrivertracking.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // usa el layout creado

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> doRegister());
    }

    private void doRegister() {
        String nombre = etName.getText().toString().trim();
        String email  = etEmail.getText().toString().trim();
        String clave  = etPassword.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        User body = new User(nombre, email, clave);

        api.registro(body).enqueue(new Callback<AuthResponse>() {
            @Override public void onResponse(Call<AuthResponse> call, Response<AuthResponse> res) {
                if (res.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish(); // vuelve al Login
                } else {
                    String msg = "No se pudo registrar";
                    try { if (res.errorBody() != null) msg = res.errorBody().string(); } catch (Exception ignored) {}
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getClass().getSimpleName()+": "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
