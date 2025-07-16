package com.geremiasirisarri.aplicacionesmoviles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashSet;
import java.util.Set;

public class ReservationActivity extends AppCompatActivity {

    private static final String KEY_HISTORIAL = "reservas_historial";
    private SharedPreferences prefs;
    private LinearLayout llReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        Toolbar toolbar = findViewById(R.id.toolbar_reservations);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);
        llReservations = findViewById(R.id.llReservations);
        showHistory();
    }

    private void showHistory() {
        llReservations.removeAllViews();
        Set<String> historial = prefs.getStringSet(KEY_HISTORIAL, new HashSet<>());

        if (historial.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No hay reservas completadas");
            empty.setTextSize(16);
            empty.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(0, dpToPx(16), 0, 0);
            empty.setLayoutParams(lp);
            llReservations.addView(empty);
            return;
        }

        for (String name : historial) {
            // infla la tarjeta del historial
            View card = getLayoutInflater().inflate(
                    R.layout.item_reservation_history,
                    llReservations,
                    false
            );

            TextView tvName = card.findViewById(R.id.tvHistServiceName);
            TextView tvCount= card.findViewById(R.id.tvHistPassengers);

            int pasajeros = prefs.getInt("count_" + name, 1);
            tvName.setText(name);
            tvCount.setText("Pasajeros: " + pasajeros);

            llReservations.addView(card);
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
