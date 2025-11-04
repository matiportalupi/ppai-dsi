package com.example.modelo;

public class Rol {
    private String nombre;
    private String descripcionRol;

    public Rol(String nombre, String descripcionRol) {
        this.nombre = nombre;
        this.descripcionRol = descripcionRol;
    }

    public String getNombreRol() {
        return this.nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }
}
