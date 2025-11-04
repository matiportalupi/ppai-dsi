package com.example.modelo;

@Deprecated(forRemoval = true, since = "2025-10")
public class AnalistaEnSismos extends Usuario {
    private String mail;
    private String telefono;

    public AnalistaEnSismos(String nombreUsuario, String contrasenia, String mail, String telefono) {
        super(nombreUsuario, contrasenia);
        this.mail = mail;
        this.telefono = telefono;
    }

    public void mostrarInformacion() {
        System.out.println("Analista en Sismos: " + getNombreUsuario() + ", Mail: " + this.mail);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
