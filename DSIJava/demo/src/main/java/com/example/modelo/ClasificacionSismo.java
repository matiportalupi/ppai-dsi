
package com.example.modelo;
public class ClasificacionSismo {
    private String nombre;
    private float kmProfundidadDesde;
    private float kmProfundidadHasta;
    public ClasificacionSismo(String nombre, float desde, float hasta) {
        this.nombre = nombre;
        this.kmProfundidadDesde = desde;
        this.kmProfundidadHasta = hasta;
    }
    public String getNombre() {
        return nombre;
    }
    
    public boolean pertenece(float profundidadHipocentro) {
        return profundidadHipocentro >= kmProfundidadDesde && profundidadHipocentro < kmProfundidadHasta;
    }
    @Override
    public String toString() {
        return nombre;
    }
}
