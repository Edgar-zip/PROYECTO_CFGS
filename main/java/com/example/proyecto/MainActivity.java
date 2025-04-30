package com.example.proyecto;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.PROYECTO.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this); // Asegúrate de inicializar Firebase

        db = FirebaseFirestore.getInstance();

        // Crear un documento de prueba
        Map<String, Object> prueba = new HashMap<>();
        prueba.put("mensaje", "Hola Firebase!");
        prueba.put("estado", "funcionando");

        db.collection("prueba").document("demo")
                .set(prueba)
                .addOnSuccessListener(unused -> Log.d("Firestore", "Documento creado"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error al crear documento", e));
    }
}
