package com.example.chat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvNombre, tvManoBuena, tvNivel, tvPartidos;
    private TextView tvReservas, tvHistorialPartidos;
    private ImageView imagenPerfil;

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil); // Asegúrate que este XML esté bien

        // Referencias a vistas
        tvNombre = findViewById(R.id.tvNombre);
        tvManoBuena = findViewById(R.id.tvManoBuena);
        tvNivel = findViewById(R.id.tvNivel);
        tvPartidos = findViewById(R.id.tvPartidosJugados);
        tvReservas = findViewById(R.id.tvReservas);
        tvHistorialPartidos = findViewById(R.id.tvHistorialPartidos);
        imagenPerfil = findViewById(R.id.imgFotoPerfil);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid);

            cargarPerfil();
            cargarReservas();
            cargarHistorialPartidos();
        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void cargarPerfil() {
        userRef.child("Perfil").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile perfil = snapshot.getValue(UserProfile.class);
                if (perfil != null) {
                    tvNombre.setText(perfil.nombre != null ? perfil.nombre : "Sin nombre");
                    tvManoBuena.setText("Mano buena: " + (perfil.mano_buena != null ? perfil.mano_buena : "-"));
                    tvNivel.setText("Nivel: " + (perfil.nivel != null ? perfil.nivel : "-"));
                    tvPartidos.setText("Partidos jugados: " + perfil.partidos_jugados);

                    // Si luego agregas imagen de perfil (URL)
                    /*
                    if (perfil.foto_url != null && !perfil.foto_url.isEmpty()) {
                        Glide.with(ProfileActivity.this)
                             .load(perfil.foto_url)
                             .into(imagenPerfil);
                    }
                    */
                } else {
                    Toast.makeText(ProfileActivity.this, "No se encontró el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error al cargar perfil: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarReservas() {
        userRef.child("Reservas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder sb = new StringBuilder();
                for (DataSnapshot reservaSnap : snapshot.getChildren()) {
                    String club = reservaSnap.child("club").getValue(String.class);
                    String fecha = reservaSnap.child("fecha").getValue(String.class);

                    if (club != null && fecha != null) {
                        sb.append("- ").append(club).append(" (").append(fecha).append(")\n");
                    }
                }
                if (sb.length() > 0) {
                    tvReservas.setText(sb.toString());
                } else {
                    tvReservas.setText("Sin reservas programadas.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error al cargar reservas: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarHistorialPartidos() {
        userRef.child("HistorialPartidos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder sb = new StringBuilder();
                for (DataSnapshot partidoSnap : snapshot.getChildren()) {
                    String rival = partidoSnap.child("rival").getValue(String.class);
                    String resultado = partidoSnap.child("resultado").getValue(String.class);

                    if (rival != null && resultado != null) {
                        sb.append("- vs ").append(rival).append(" → ").append(resultado).append("\n");
                    }
                }
                if (sb.length() > 0) {
                    tvHistorialPartidos.setText(sb.toString());
                } else {
                    tvHistorialPartidos.setText("Sin historial disponible.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error al cargar historial: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
