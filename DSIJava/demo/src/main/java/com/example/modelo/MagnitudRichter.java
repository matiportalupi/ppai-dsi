package com.example.modelo;
public class MagnitudRichter {
    private String nombre;
    private float valor;
    public MagnitudRichter(String nombre, float valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    public float getValor() {
        return valor;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    @Override
    public String toString() {
        
        return String.format("%.1f (%s)", valor, nombre);
    }
}
