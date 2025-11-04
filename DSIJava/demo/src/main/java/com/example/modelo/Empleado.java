package com.example.modelo;

public class Empleado {
    private String nombre;
    private String apellido;
    private String mail;
    private String telefono;
    private Rol rol;

    public Empleado(String nombre, String apellido, String mail, String telefono, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.telefono = telefono;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public String obtenerMail() {
        return this.mail;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean esResponsableDeReparacion() {
        if (this.rol == null) {
            return false;
        }
        String nombreRol = this.rol.getNombreRol().toLowerCase();
        return nombreRol.contains("reparacion") || nombreRol.contains("técnico") || 
               nombreRol.contains("mantenimiento") || nombreRol.contains("supervisor");
    }

    public void mostrarInformacion() {
        System.out.println("Empleado: " + this.nombre + " " + this.apellido + 
                         ", Mail: " + this.mail + ", Teléfono: " + this.telefono +
                         ", Rol: " + (this.rol != null ? this.rol.getNombreRol() : "Sin rol"));
    }
}
