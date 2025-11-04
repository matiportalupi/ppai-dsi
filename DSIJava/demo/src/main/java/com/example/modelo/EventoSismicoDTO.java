package com.example.modelo;
import java.time.LocalDateTime;
public class EventoSismicoDTO {
    
    
    private final int id; 
    
    
    private final LocalDateTime fechaHoraOcurrencia;
    private final String localizacionGeografica;
    private final String magnitud;
    private final String estadoActual;
    private final String alcance;
    private final String origen;
    public EventoSismicoDTO(int id, LocalDateTime fecha, String loc, String mag, String estado, String alcance, String origen) {
        this.id = id;
        this.fechaHoraOcurrencia = fecha;
        this.localizacionGeografica = loc;
        this.magnitud = mag;
        this.estadoActual = estado;
        this.alcance = alcance;
        this.origen = origen;
    }
    
    public int getId() { return id; }
    public LocalDateTime getFechaHoraOcurrencia() { return fechaHoraOcurrencia; }
    public String getLocalizacionGeografica() { return localizacionGeografica; }
    public String getMagnitud() { return magnitud; }
    public String getEstadoActual() { return estadoActual; }
    public String getAlcance() { return alcance; }
    public String getOrigen() { return origen; }
    
    @Override
    public String toString() {
        return String.format("Fecha: %s - Loc: %s - Mag: %s - Estado: %s",
            fechaHoraOcurrencia.toLocalDate(),
            localizacionGeografica,
            magnitud,
            estadoActual);
    }
}
