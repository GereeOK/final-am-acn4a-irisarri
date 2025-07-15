package com.geremiasirisarri.aplicacionesmoviles;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa Firebase aquí una sola vez:
        FirebaseApp.initializeApp(this);
    }
}

