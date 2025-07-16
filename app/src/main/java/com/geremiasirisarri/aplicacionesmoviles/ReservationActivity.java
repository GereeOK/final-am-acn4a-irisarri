package com.geremiasirisarri.aplicacionesmoviles;

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

    // Ahora el fallback local usa imageName (no imageResId)
    private final List<Service> servicios = Arrays.asList(
            new Service("City Tour",       "Recorrido por lo mejor de Buenos Aires",      "img_citytour"),
            new Service("Show de Tango",   "Cena y espectáculo en una clásica tanguería", "img_tango_show"),
            new Service("Delta del Tigre", "Navegación por el río y paseo en lancha",     "img_tigre")
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

        // Preferences y contenedor
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);
        llReservations = findViewById(R.id.llReservations);

        // Primera carga
        loadAndShowReservations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAndShowReservations();
    }

    private void loadAndShowReservations() {
        // 0) limpiar todas las vistas previas
        llReservations.removeAllViews();

        // 1) obtenemos set de nombres
        Set<String> guardadas = prefs.getStringSet("reservas", new HashSet<>());

        if (guardadas.isEmpty()) {
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
                Service s = servicios.stream()
                        .filter(x -> x.getName().equals(name))
                        .findFirst()
                        .orElse(null);
                if (s == null) continue;

                View card = getLayoutInflater().inflate(
                        R.layout.item_service, llReservations, false);

                ImageView iv     = card.findViewById(R.id.ivService);
                TextView  tvName = card.findViewById(R.id.tvName);
                TextView  tvDesc = card.findViewById(R.id.tvDesc);
                Button    btnAct = card.findViewById(R.id.btnReserve);

                // if you want to show the image here, resolve drawable by name:
                int resId = getResources().getIdentifier(
                        s.getImageName(),
                        "drawable",
                        getPackageName()
                );
                if (resId != 0) {
                    iv.setImageResource(resId);
                } else {
                    iv.setImageResource(android.R.drawable.ic_menu_report_image);
                }

                tvName.setText(s.getName());
                tvDesc.setText(s.getDescription());

                btnAct.setText("Finalizar Reserva");
                btnAct.setOnClickListener(v -> {
                    Set<String> actuales = prefs.getStringSet("reservas", new HashSet<>());
                    Set<String> nuevas = new HashSet<>(actuales);
                    nuevas.remove(s.getName());
                    prefs.edit().putStringSet("reservas", nuevas).apply();

                    // remueve la tarjeta
                    llReservations.removeView(card);

                    // si quedó vacío, muestra mensaje
                    if (nuevas.isEmpty()) {
                        loadAndShowReservations();
                    }
                });

                llReservations.addView(card);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
