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
        setContentView(R.layout.activity_perfil); // Tu layout XML

        // Inicializar vistas
        tvNombre = findViewById(R.id.tvNombre);
        tvManoBuena = findViewById(R.id.tvManoBuena);
        tvNivel = findViewById(R.id.tvNivel);
        tvPartidos = findViewById(R.id.tvPartidosJugados);
        tvReservas = findViewById(R.id.tvReservas);
        tvHistorialPartidos = findViewById(R.id.tvHistorialPartidos);
        imagenPerfil = findViewById(R.id.imgFotoPerfil); // Puedes dejar un drawable por defecto

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
                    tvNombre.setText(perfil.nombre);
                    tvManoBuena.setText("Mano buena: " + perfil.mano_buena);
                    tvNivel.setText("Nivel: " + perfil.nivel);
                    tvPartidos.setText("Partidos jugados: " + perfil.partidos_jugados);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error al cargar perfil", Toast.LENGTH_SHORT).show();
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
                    sb.append("- ").append(club).append(" (").append(fecha).append(")\n");
                }
                tvReservas.setText(sb.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error al cargar reservas", Toast.LENGTH_SHORT).show();
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
                    sb.append("- vs ").append(rival).append(" → ").append(resultado).append("\n");
                }
                tvHistorialPartidos.setText(sb.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error al cargar historial", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
