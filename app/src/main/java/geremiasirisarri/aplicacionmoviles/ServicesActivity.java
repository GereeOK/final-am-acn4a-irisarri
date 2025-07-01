package geremiasirisarri.aplicacionmoviles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicesActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private LinearLayout llServices;
    private LinearLayout misReservasLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // 0) Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // mostrar flecha back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // fin Activity al pulsar back
        toolbar.setNavigationOnClickListener(v -> finish());

        // 1) Inicializamos SharedPreferences
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);

        // 2) Referencias a layouts
        llServices        = findViewById(R.id.llServices);
        misReservasLayout = findViewById(R.id.misReservasLayout);

        // 3) Datos de servicios
        List<Service> servicios = Arrays.asList(
                new Service("City Tour",      "Recorrido por lo mejor de Buenos Aires",      R.drawable.img_citytour),
                new Service("Show de Tango",  "Cena y espectáculo en una clásica tanguería",  R.drawable.img_tango_show),
                new Service("Delta del Tigre","Navegación por el río y paseo en lancha",     R.drawable.img_tigre)
        );

        // 4) Inflamos cada card y asignamos listener
        for (Service s : servicios) {
            View card = getLayoutInflater().inflate(R.layout.item_service, llServices, false);

            ImageView iv        = card.findViewById(R.id.ivService);
            TextView  tvName    = card.findViewById(R.id.tvName);
            TextView  tvDesc    = card.findViewById(R.id.tvDesc);
            Button    btnReserve= card.findViewById(R.id.btnReserve);

            iv.setImageResource(s.getImageResId());
            tvName.setText(s.getName());
            tvDesc.setText(s.getDescription());

            btnReserve.setOnClickListener(v -> {
                // a) Toast
                Toast.makeText(this, "Reservaste: " + s.getName(), Toast.LENGTH_SHORT).show();
                // b) Mostrar sección Mis Reservas
                if (misReservasLayout.getVisibility() == View.GONE) {
                    misReservasLayout.setVisibility(View.VISIBLE);
                }
                // c) Persistir
                Set<String> actuales = prefs.getStringSet("reservas", new HashSet<>());
                Set<String> nuevas   = new HashSet<>(actuales);
                nuevas.add(s.getName());
                prefs.edit().putStringSet("reservas", nuevas).apply();
                // d) Añadir TextView
                TextView reserva = new TextView(this);
                reserva.setText(s.getName());
                reserva.setTextSize(16);
                reserva.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                int margin = dpToPx(6);
                lp.setMargins(0, margin, 0, margin);
                reserva.setLayoutParams(lp);
                misReservasLayout.addView(reserva);
            });

            llServices.addView(card);
        }

        // 5) (Opcional) cargar reservas previas
        loadExistingReservations();
    }

    private void loadExistingReservations() {
        Set<String> guardadas = prefs.getStringSet("reservas", null);
        if (guardadas != null && !guardadas.isEmpty()) {
            misReservasLayout.setVisibility(View.VISIBLE);
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
                misReservasLayout.addView(reserva);
            }
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}



