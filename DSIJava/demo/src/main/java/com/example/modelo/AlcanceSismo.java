package com.example.modelo;
public class AlcanceSismo {
    private String nombre;
    private String descripcion;
    public AlcanceSismo(String nombre, String descripcion) {
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
