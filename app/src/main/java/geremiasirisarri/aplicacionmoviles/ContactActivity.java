package geremiasirisarri.aplicacionmoviles;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
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

        // 1) Toolbar “Up”
        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 2) Referencias a campos
        etName    = findViewById(R.id.etName);
        etEmail   = findViewById(R.id.etEmail);
        etMessage = findViewById(R.id.etMessage);
        btnSend   = findViewById(R.id.btnSend);

        // 3) Click “Enviar”
        btnSend.setOnClickListener(v -> {
            String name    = etName.getText().toString().trim();
            String email   = etEmail.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            // Validaciones simples
            if (TextUtils.isEmpty(name)) {
                etName.setError(getString(R.string.error_name));
                return;
            }
            if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError(getString(R.string.error_email));
                return;
            }
            if (TextUtils.isEmpty(message)) {
                etMessage.setError(getString(R.string.error_message));
                return;
            }

            // Aquí iría el envío real (API, correo, etc). De momento:
            Toast.makeText(this,
                    getString(R.string.toast_sent, name),
                    Toast.LENGTH_LONG).show();

            // Opcional: limpiar formulario
            etName.setText("");
            etEmail.setText("");
            etMessage.setText("");
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Flecha “Up”
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}




