package com.geremiasirisarri.aplicacionesmoviles;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegEmail, etRegPassword;
    private Button btnRegister;
    private ProgressBar progressBarReg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializa Firebase
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etRegEmail     = findViewById(R.id.etRegEmail);
        etRegPassword  = findViewById(R.id.etRegPassword);
        btnRegister    = findViewById(R.id.btnRegister);
        progressBarReg = findViewById(R.id.progressBarReg);

        btnRegister.setOnClickListener(v -> {
            String email    = etRegEmail.getText().toString().trim();
            String password = etRegPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etRegEmail.setError(getString(R.string.error_email));
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etRegPassword.setError(getString(R.string.error_password));
                return;
            }
            if (password.length() < 6) {
                etRegPassword.setError(getString(R.string.error_password_length));
                return;
            }

            progressBarReg.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressBarReg.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(
                                    RegisterActivity.this,
                                    getString(R.string.toast_registered, user.getEmail()),
                                    Toast.LENGTH_SHORT
                            ).show();
                            // Cierra pantalla de registro y vuelve al login
                            finish();
                        } else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    getString(R.string.error_register, task.getException().getMessage()),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });
    }
}