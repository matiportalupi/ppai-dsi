package com.example.modelo;
public class Estado {
    
    public static final String PENDIENTE_REVISION = "Pendiente de revision";
    public static final String EN_REVISION = "En revisión";
    public static final String CONFIRMADO = "Confirmado";
    public static final String RECHAZADO = "Rechazado";
    public static final String DERIVADO_EXPERTO = "Derivado a experto";
    private String ambito;
    private String nombre;
    private String descripcion;
    public Estado(String ambito, String nombre, String descripcion) {
        this.ambito = ambito;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    
    
    public boolean pendienteDeRevision() {
        return PENDIENTE_REVISION.equalsIgnoreCase(this.nombre);
    }

    public boolean esAutoDetectado() {
        return pendienteDeRevision();
    }

    public boolean esBloqueadoEnRevision() {
        return EN_REVISION.equalsIgnoreCase(this.nombre);
    }
    public boolean esAmbitoEventoSismico() {
        return "Revision".equalsIgnoreCase(this.ambito);
    }
    
    public String getAmbito() { return ambito; }
    public void setAmbito(String ambito) { this.ambito = ambito; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
