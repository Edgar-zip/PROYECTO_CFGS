package com.example.chat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreenActivity extends AppCompatActivity {

    ImageView cardPistas, cardRopa, cardReservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        cardPistas = findViewById(R.id.card_pistas);
        cardRopa = findViewById(R.id.card_ropa);
        cardReservas = findViewById(R.id.card_reservas);

        // Navegación con clics en tarjetas
        cardPistas.setOnClickListener(view -> startActivity(new Intent(this, PistasActivity.class)));
        cardRopa.setOnClickListener(view -> startActivity(new Intent(this, RopaActivity.class)));
        cardReservas.setOnClickListener(view -> startActivity(new Intent(this, ReservasActivity.class)));

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home); // actual
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    return true;
                case R.id.nav_fav:
                    startActivity(new Intent(getApplicationContext(), FavoritosActivity.class));
                    return true;
                case R.id.nav_cart:
                    startActivity(new Intent(getApplicationContext(), CarritoActivity.class));
                    return true;
                case R.id.nav_menu:
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    return true;
            }
            return false;
        });
    }
}
