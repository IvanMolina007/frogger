package com.example.frogger.model;

public class Usuario {
    String nombre;
    int puntos;

    public Usuario() {
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
    }

    public Usuario(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
