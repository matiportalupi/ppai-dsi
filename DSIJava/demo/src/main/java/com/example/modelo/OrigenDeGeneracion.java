package com.example.modelo;
public class OrigenDeGeneracion {
    private String nombre;
    private String descripcion;
    public OrigenDeGeneracion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
public String toString() {
    
    return this.getNombre();
}
}
