package geremiasirisarri.aplicacionmoviles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private LinearLayout llReservations;
    // misma lista de servicios que en ServicesActivity para resolver imágenes/descripciones
    private final List<Service> servicios = Arrays.asList(
            new Service("City Tour",      "Recorrido por lo mejor de Buenos Aires",      R.drawable.img_citytour),
            new Service("Show de Tango",  "Cena y espectáculo en una clásica tanguería", R.drawable.img_tango_show),
            new Service("Delta del Tigre","Navegación por el río y paseo en lancha",     R.drawable.img_tigre)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // Toolbar + Up
        Toolbar toolbar = findViewById(R.id.toolbar_reservations);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // prefs y contenedor
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);
        llReservations = findViewById(R.id.llReservations);

        // inflar cada reserva como tarjeta
        loadAndShowReservations();
    }

    private void loadAndShowReservations() {
        // obtenemos set de nombres
        Set<String> guardadas = prefs.getStringSet("reservas", new HashSet<>());
        if (guardadas.isEmpty()) {
            // mensaje si no hay
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
            for (String name : guardadas) {
                // buscamos el Service correspondiente
                Service s = servicios.stream()
                        .filter(x -> x.getName().equals(name))
                        .findFirst()
                        .orElse(null);
                if (s == null) continue;

                // inflamos item_service.xml
                View card = getLayoutInflater().inflate(
                        R.layout.item_service, llReservations, false);

                ImageView iv      = card.findViewById(R.id.ivService);
                TextView  tvName  = card.findViewById(R.id.tvName);
                TextView  tvDesc  = card.findViewById(R.id.tvDesc);
                Button    btnAct  = card.findViewById(R.id.btnReserve);

                iv.setImageResource(s.getImageResId());
                tvName.setText(s.getName());
                tvDesc.setText(s.getDescription());

                // adaptamos el botón
                btnAct.setText("Finalizar Reserva");
                btnAct.setOnClickListener(v -> {
                    // 1) quitar de prefs
                    Set<String> actuales = prefs.getStringSet("reservas", new HashSet<>());
                    Set<String> nuevas = new HashSet<>(actuales);
                    nuevas.remove(s.getName());
                    prefs.edit().putStringSet("reservas", nuevas).apply();
                    // 2) remover tarjeta de la UI
                    llReservations.removeView(card);
                    // 3) si quedó vacío, mostrar mensaje
                    if (nuevas.isEmpty()) {
                        llReservations.removeAllViews();
                        loadAndShowReservations(); // recursivo para poner el texto “no hay…”
                    }
                });

                llReservations.addView(card);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Up → cerrar
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
