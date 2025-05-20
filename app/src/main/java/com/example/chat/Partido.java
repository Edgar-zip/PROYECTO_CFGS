package com.example.chat;

public class Partido {
    private String rivales;
    private String resultado;
    private String fecha;

    public Partido() {
    }

    public Partido(String rivales, String resultado, String fecha) {
        this.rivales = rivales;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public String getRivales() {
        return rivales;
    }

    public String getResultado() {
        return resultado;
    }

    public String getFecha() {
        return fecha;
    }
}
