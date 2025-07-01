package geremiasirisarri.aplicacionmoviles;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilita edge-to-edge antes de inflar el layout:
        EdgeToEdge.enable(this);

        // Carga el XML con tu toolbar y scrollView:
        setContentView(R.layout.activity_services);

        // Aplica los insets (status bar / navbar) al Toolbar que ahora tiene id @+id/toolbar
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.toolbar),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );
    }
}





