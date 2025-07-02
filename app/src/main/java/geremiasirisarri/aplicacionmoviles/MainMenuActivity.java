package geremiasirisarri.aplicacionmoviles;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainMenuActivity extends AppCompatActivity {

    private MaterialButton btnServicios, btnReservations, btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Sólo inicializamos los botones que ya existen en el layout:
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
