package com.example.chat;
public class UserProfile {
    public String nombre, mano_buena, nivel;
    public int partidos_jugados;

    public UserProfile() {} // Obligatorio para Firebase

    public UserProfile(String nombre, String mano_buena, String nivel, int partidos_jugados) {
        this.nombre = nombre;
        this.mano_buena = mano_buena;
        this.nivel = nivel;
        this.partidos_jugados = partidos_jugados;
    }
}
