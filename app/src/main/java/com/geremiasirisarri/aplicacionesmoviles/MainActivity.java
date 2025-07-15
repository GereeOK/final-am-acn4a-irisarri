package com.geremiasirisarri.aplicacionesmoviles;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1) Hacer que la ventana dibuje bajo statusBar/navBar
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // 2) Carga el layout que contiene tu Toolbar
        setContentView(R.layout.activity_services);

        // 3) Ajustar padding en el toolbar según los insets del sistema
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
