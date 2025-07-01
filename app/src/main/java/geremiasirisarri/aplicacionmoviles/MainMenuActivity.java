package geremiasirisarri.aplicacionmoviles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Referencias a los botones del menú principal
        Button btnServicios    = findViewById(R.id.btnServicios);
        Button btnReservations = findViewById(R.id.btnReservations);
        Button btnContact      = findViewById(R.id.btnContact);

        // Navegación a cada pantalla
        btnServicios.setOnClickListener(v ->
                startActivity(new Intent(this, ServicesActivity.class))
        );
        btnReservations.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationActivity.class))
        );
        btnContact.setOnClickListener(v ->
                startActivity(new Intent(this, ContactActivity.class))
        );
    }
}

