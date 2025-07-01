package geremiasirisarri.aplicacionmoviles;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // 1) Inicializamos el Toolbar y lo configuramos como ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar_reservations);
        setSupportActionBar(toolbar);

        // 2) Activamos la flecha “Up”
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Aquí cargarías dinámicamente las reservas si lo deseas…
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 3) Cuando pulse la flecha Up, simplemente cerramos Activity
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


