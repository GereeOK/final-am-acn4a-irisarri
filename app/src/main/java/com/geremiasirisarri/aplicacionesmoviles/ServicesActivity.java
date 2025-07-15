package com.geremiasirisarri.aplicacionesmoviles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        // Toolbar con flecha “Up”
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // SharedPreferences
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);

        // Referencias
        llServices        = findViewById(R.id.llServices);
        misReservasLayout = findViewById(R.id.misReservasLayout);

        // Datos de servicios
        List<Service> servicios = Arrays.asList(
                new Service("City Tour",       "Recorrido por lo mejor de Buenos Aires",      R.drawable.img_citytour),
                new Service("Show de Tango",   "Cena y espectáculo en una clásica tanguería",  R.drawable.img_tango_show),
                new Service("Delta del Tigre", "Navegación por el río y paseo en lancha",      R.drawable.img_tigre)
        );

        // Inflar tarjetas
        for (Service s : servicios) {
            View card = getLayoutInflater().inflate(R.layout.item_service, llServices, false);

            ImageView iv       = card.findViewById(R.id.ivService);
            TextView  tvName   = card.findViewById(R.id.tvName);
            TextView  tvDesc   = card.findViewById(R.id.tvDesc);
            Button    btnReserve = card.findViewById(R.id.btnReserve);

            iv.setImageResource(s.getImageResId());
            tvName.setText(s.getName());
            tvDesc.setText(s.getDescription());

            btnReserve.setOnClickListener(v -> {
                // 1) Guardar en SharedPreferences
                Set<String> actuales = prefs.getStringSet("reservas", new HashSet<>());
                Set<String> nuevas   = new HashSet<>(actuales);
                nuevas.add(s.getName());
                prefs.edit().putStringSet("reservas", nuevas).apply();

                // 2) Feedback al usuario
                Toast.makeText(this, "Reservaste: " + s.getName(), Toast.LENGTH_SHORT).show();
                if (misReservasLayout.getVisibility() == View.GONE) {
                    misReservasLayout.setVisibility(View.VISIBLE);
                }
                addReservationTextView(s.getName());
                invalidateOptionsMenu();

                // 3) Lanzar ReservationActivity con EXTRA
                Intent intent = new Intent(this, ReservationActivity.class);
                intent.putExtra("extra_service_name", s.getName());
                startActivity(intent);
            });

            llServices.addView(card);
        }

        // Cargar reservas previas
        loadExistingReservations();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_services, menu);
        MenuItem cartItem = menu.findItem(R.id.action_cart);
        View actionView   = cartItem.getActionView();
        TextView badge    = actionView.findViewById(R.id.tvCartBadge);

        updateCartBadge(badge);

        actionView.setOnClickListener(v -> onOptionsItemSelected(cartItem));
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem cartItem = menu.findItem(R.id.action_cart);
        View actionView   = cartItem.getActionView();
        TextView badge    = actionView.findViewById(R.id.tvCartBadge);

        updateCartBadge(badge);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, ReservationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addReservationTextView(String nombre) {
        TextView reserva = new TextView(this);
        reserva.setText(nombre);
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

    private void loadExistingReservations() {
        Set<String> guardadas = prefs.getStringSet("reservas", new HashSet<>());
        if (!guardadas.isEmpty()) {
            misReservasLayout.setVisibility(View.VISIBLE);
            for (String name : guardadas) {
                addReservationTextView(name);
            }
        }
    }

    private void updateCartBadge(TextView badge) {
        Set<String> reservas = prefs.getStringSet("reservas", new HashSet<>());
        int count = reservas.size();
        if (count > 0) {
            badge.setText(String.valueOf(count));
            badge.setVisibility(View.VISIBLE);
        } else {
            badge.setVisibility(View.GONE);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
