package com.geremiasirisarri.aplicacionesmoviles;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvGoRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1️⃣ Inicializar Firebase
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        // 2️⃣ Instancia de Auth y vistas
        mAuth         = FirebaseAuth.getInstance();
        etEmail       = findViewById(R.id.etEmail);
        etPassword    = findViewById(R.id.etPassword);
        btnLogin      = findViewById(R.id.btnLogin);
        tvGoRegister  = findViewById(R.id.tvGoRegister);
        progressBar   = findViewById(R.id.progressBar);

        // 3️⃣ Click en “Ingresar”
        btnLogin.setOnClickListener(v -> attemptLogin());

        // 4️⃣ Click en “¿No tienes cuenta? Regístrate”
        tvGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void attemptLogin() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validaciones básicas
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Ingresa tu correo");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Ingresa tu contraseña");
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Intento de login
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login OK
                        onAuthSuccess(mAuth.getCurrentUser());
                    } else {
                        Exception ex = task.getException();
                        // Si el usuario no existe, lo enviamos al registro
                        if (ex instanceof FirebaseAuthInvalidUserException) {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this, RegisterActivity.class)
                                    .putExtra("email", email));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Error de autenticación: " + ex.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Navegar al menú principal
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
}