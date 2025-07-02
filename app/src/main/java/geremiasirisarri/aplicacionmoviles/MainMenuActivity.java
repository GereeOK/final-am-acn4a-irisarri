package geremiasirisarri.aplicacionmoviles;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import android.widget.ImageView;

public class MainMenuActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private MaterialButton btnServicios;
    private MaterialButton btnReservations;
    private MaterialButton btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Sólo existe imgLogo, ya no hay banner separado
        imgLogo         = findViewById(R.id.imgLogo);
        btnServicios    = findViewById(R.id.btnServicios);
        btnReservations = findViewById(R.id.btnReservations);
        btnContact      = findViewById(R.id.btnContact);

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
