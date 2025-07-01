package geremiasirisarri.aplicacionmoviles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class ReservationActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private LinearLayout llReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // ➤ Habilitar flecha “Up” en la ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 1) Inicializar SharedPreferences
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);

        // 2) Referencia al contenedor de reservas
        llReservations = findViewById(R.id.llReservations);

        // 3) Cargar y mostrar las reservas guardadas
        loadReservations();
    }

    /**
     * Recupera el Set de reservas de SharedPreferences
     * y las inyecta como TextViews en el LinearLayout.
     */
    private void loadReservations() {
        Set<String> guardadas = prefs.getStringSet("reservas", new HashSet<>());
        for (String name : guardadas) {
            TextView reserva = new TextView(this);
            reserva.setText(name);
            reserva.setTextSize(16);
            reserva.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            int margin = dpToPx(6);
            lp.setMargins(0, margin, 0, margin);
            reserva.setLayoutParams(lp);

            llReservations.addView(reserva);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Manejar clic en la flecha “Up”
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Convierte dp a pixels según la densidad de pantalla */
    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}

