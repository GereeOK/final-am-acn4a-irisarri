package com.geremiasirisarri.aplicacionesmoviles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicesActivity extends AppCompatActivity {

    private static final String KEY_ACTIVAS   = "reservas";
    private static final String KEY_HISTORIAL = "reservas_historial";

    private SharedPreferences prefs;
    private LinearLayout llServices;
    private LinearLayout misReservasLayout;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // Toolbar con flecha Up
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Inicializar Firebase y Firestore
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // SharedPreferences
        prefs = getSharedPreferences("baires_prefs", MODE_PRIVATE);

        // Referencias a vistas
        llServices        = findViewById(R.id.llServices);
        misReservasLayout = findViewById(R.id.misReservasLayout);

        // Cargar la lista de servicios y las reservas previas
        loadServicesFromFirestore();
        loadExistingReservations();
    }

    private void loadServicesFromFirestore() {
        llServices.removeAllViews();

        db.collection("servicios")
                .get()
                .addOnSuccessListener(this::handleServicesSnapshot)
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar servicios", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    private void handleServicesSnapshot(QuerySnapshot snapshots) {
        List<Service> servicios = new ArrayList<>();
        for (QueryDocumentSnapshot doc : snapshots) {
            servicios.add(doc.toObject(Service.class));
        }

        for (Service s : servicios) {
            View card = getLayoutInflater().inflate(R.layout.item_service, llServices, false);

            ImageView iv        = card.findViewById(R.id.ivService);
            TextView  tvName    = card.findViewById(R.id.tvName);
            TextView  tvDesc    = card.findViewById(R.id.tvDesc);
            Button    btnReserve= card.findViewById(R.id.btnReserve);

            // Resuelve el drawable por nombre
            int resId = getResources().getIdentifier(
                    s.getImageName(), "drawable", getPackageName());
            if (resId != 0) iv.setImageResource(resId);
            else            iv.setImageResource(android.R.drawable.ic_menu_report_image);

            tvName.setText(s.getName());
            tvDesc.setText(s.getDescription());
            btnReserve.setText("Reservar");

            btnReserve.setOnClickListener(v -> showFinalizeDialog(s));

            llServices.addView(card);
        }
    }

    private void showFinalizeDialog(Service service) {
        View form = LayoutInflater.from(this).inflate(R.layout.dialog_finalize, null);
        EditText etName  = form.findViewById(R.id.etName);
        EditText etCount = form.findViewById(R.id.etPassengerCount);
        EditText etPhone = form.findViewById(R.id.etPhone);

        new AlertDialog.Builder(this)
                .setTitle("Completa tu reserva")
                .setView(form)
                .setPositiveButton("Enviar", (dialog, which) -> {
                    String cliente = etName.getText().toString().trim();
                    String countStr= etCount.getText().toString().trim();
                    String phone   = etPhone.getText().toString().trim();

                    if (cliente.isEmpty() || countStr.isEmpty() || phone.isEmpty()) {
                        Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int pasajeros;
                    try {
                        pasajeros = Integer.parseInt(countStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Cantidad de pasajeros inválida", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 1) Guardar en RESERVAS activas
                    Set<String> actuales = prefs.getStringSet(KEY_ACTIVAS, new HashSet<>());
                    Set<String> nuevas   = new HashSet<>(actuales);
                    nuevas.add(service.getName());
                    prefs.edit().putStringSet(KEY_ACTIVAS, nuevas).apply();

                    // 2) Guardar también en HISTORIAL
                    Set<String> historial = prefs.getStringSet(KEY_HISTORIAL, new HashSet<>());
                    historial.add(service.getName());
                    prefs.edit().putStringSet(KEY_HISTORIAL, historial).apply();

                    // 3) Guardar la cantidad de pasajeros con clave única
                    prefs.edit()
                            .putInt("count_" + service.getName(), pasajeros)
                            .apply();

                    // Feedback y refrescar badge
                    Toast.makeText(this, "Su reserva se ha registrado", Toast.LENGTH_LONG).show();
                    invalidateOptionsMenu();

                    // Ir a Mis Reservas
                    startActivity(new Intent(this, ReservationActivity.class));
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_services, menu);
        // Aquí actualiza tu badge si lo tienes
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, ReservationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadExistingReservations() {
        Set<String> guardadas = prefs.getStringSet(KEY_ACTIVAS, new HashSet<>());
        if (!guardadas.isEmpty()) {
            misReservasLayout.setVisibility(View.VISIBLE);
            for (String name : guardadas) {
                TextView tv = new TextView(this);
                tv.setText(name);
                misReservasLayout.addView(tv);
            }
        }
    }
}
