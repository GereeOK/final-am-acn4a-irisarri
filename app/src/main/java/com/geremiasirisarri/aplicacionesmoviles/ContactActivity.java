package com.geremiasirisarri.aplicacionesmoviles;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContactActivity extends AppCompatActivity {
    private EditText etName, etEmail, etMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // 1️⃣ Toolbar “Up”
        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 2️⃣ Referencias a campos del formulario
        etName    = findViewById(R.id.etName);
        etEmail   = findViewById(R.id.etEmail);
        etMessage = findViewById(R.id.etMessage);
        btnSend   = findViewById(R.id.btnSend);

        // 3️⃣ Lógica del botón “Enviar”
        btnSend.setOnClickListener(v -> {
            String name    = etName.getText().toString().trim();
            String email   = etEmail.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aquí podrías enviar el mensaje a tu backend o a Firestore
            Toast.makeText(this, "¡Mensaje enviado!", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Esto maneja la flecha “Up” del toolbar
        finish();
        return true;
    }
}
