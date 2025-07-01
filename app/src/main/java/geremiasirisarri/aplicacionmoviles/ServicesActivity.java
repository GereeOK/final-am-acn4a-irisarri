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

        // Inicializamos SharedPreferences
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);

        // Referencias a layouts
        llServices        = findViewById(R.id.llServices);
        misReservasLayout = findViewById(R.id.misReservasLayout);

        // Datos de servicios
        List<Service> servicios = Arrays.asList(
                new Service("City Tour",      "Recorrido por lo mejor de Buenos Aires",      R.drawable.img_citytour),
                new Service("Show de Tango",  "Cena y espectáculo en una clásica tanguería",  R.drawable.img_tango_show),
                new Service("Delta del Tigre","Navegación por el río y paseo en lancha",     R.drawable.img_tigre)
        );

        // Inflamos cada card y asignamos listener
        for (Service s : servicios) {
            View card = getLayoutInflater().inflate(R.layout.item_service, llServices, false);

            ImageView iv      = card.findViewById(R.id.ivService);
            TextView  tvName  = card.findViewById(R.id.tvName);
            TextView  tvDesc  = card.findViewById(R.id.tvDesc);
            Button    btnReserve = card.findViewById(R.id.btnReserve);

            iv.setImageResource(s.getImageResId());
            tvName.setText(s.getName());
            tvDesc.setText(s.getDescription());

            btnReserve.setOnClickListener(v -> {
                // 1) Toast de confirmación
                Toast.makeText(this, "Reservaste: " + s.getName(), Toast.LENGTH_SHORT).show();

                // 2) Mostrar sección Mis Reservas si estaba oculta
                if (misReservasLayout.getVisibility() == View.GONE) {
                    misReservasLayout.setVisibility(View.VISIBLE);
                }

                // 3) Persistir en SharedPreferences
                Set<String> actuales = prefs.getStringSet("reservas", new HashSet<>());
                Set<String> nuevas   = new HashSet<>(actuales);
                nuevas.add(s.getName());
                prefs.edit()
                        .putStringSet("reservas", nuevas)
                        .apply();

                // 4) Añadir dinámicamente un TextView con la reserva
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

        // 5) (Opcional) Cargar al inicio las reservas ya guardadas
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
