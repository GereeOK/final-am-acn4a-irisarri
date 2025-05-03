package geremiasirisarri.aplicacionmoviles;

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
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        LinearLayout llServices = findViewById(R.id.llServices);
        LinearLayout misReservasLayout = findViewById(R.id.misReservasLayout);
        List<Service> servicios = Arrays.asList(
                new Service("City Tour",     "Recorrido por lo mejor de Buenos Aires",  R.drawable.img_citytour),
                new Service("Show de Tango", "Cena y espectáculo en una clásica tanguería", R.drawable.img_tango_show),
                new Service("Delta del Tigre","Navegación por el río y paseo en lancha",    R.drawable.img_tigre)
        );

        for (Service s : servicios) {
            View card = getLayoutInflater().inflate(R.layout.item_service, llServices, false);

            ImageView iv        = card.findViewById(R.id.ivService);
            TextView tvName     = card.findViewById(R.id.tvName);
            TextView tvDesc     = card.findViewById(R.id.tvDesc);
            Button btnReserve   = card.findViewById(R.id.btnReserve);

            iv.setImageResource(s.getImageResId());
            tvName.setText(s.getName());
            tvDesc.setText(s.getDescription());

            btnReserve.setOnClickListener(v -> {
                Toast.makeText(this, "Reservaste: " + s.getName(), Toast.LENGTH_SHORT).show();

                if (misReservasLayout.getVisibility() == View.GONE) {
                    misReservasLayout.setVisibility(View.VISIBLE);
                }

                TextView reserva = new TextView(this);
                reserva.setText(s.getName());
                reserva.setTextSize(16);
                reserva.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));

                //  Asignar LayoutParams con márgenes
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                int margin = dpToPx(6);
                lp.setMargins(0, margin, 0, margin);
                reserva.setLayoutParams(lp);

                // 5) Añadir la reserva al contenedor
                misReservasLayout.addView(reserva);
            });
            llServices.addView(card);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}


