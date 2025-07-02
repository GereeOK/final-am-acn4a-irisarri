package geremiasirisarri.aplicacionmoviles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashSet;
import java.util.Set;

public class ReservationActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private LinearLayout llReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // 1) Configuramos el Toolbar y la flecha Up
        Toolbar toolbar = findViewById(R.id.toolbar_reservations);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 2) Inicializamos SharedPreferences y la lista
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);
        llReservations = findViewById(R.id.llReservations);

        // 3) Cargamos y mostramos las reservas
        loadReservations();
    }

    private void loadReservations() {
        Set<String> guardadas = prefs.getStringSet("reservas", new HashSet<>());
        if (guardadas.isEmpty()) {
            // No hay reservas: mostramos un mensaje
            TextView empty = new TextView(this);
            empty.setText(R.string.no_reservations);
            empty.setTextSize(16);
            empty.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(0, dpToPx(16), 0, 0);
            empty.setLayoutParams(lp);
            llReservations.addView(empty);
        } else {
            // Hay reservas: creamos un TextView por cada una
            for (String name : guardadas) {
                TextView reserva = new TextView(this);
                reserva.setText(name);
                reserva.setTextSize(16);
                reserva.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.setMargins(0, dpToPx(6), 0, dpToPx(6));
                reserva.setLayoutParams(lp);
                llReservations.addView(reserva);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Flecha Up → cerramos la Activity
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}



