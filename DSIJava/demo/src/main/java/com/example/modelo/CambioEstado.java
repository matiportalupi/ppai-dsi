package com.example.modelo;
import java.time.LocalDateTime;
public class CambioEstado {
    private LocalDateTime fechaHoraInicio;
    
    
    
    private Estado estado;
    public CambioEstado(Estado estado, LocalDateTime fechaHoraInicio) {
        this.estado = estado;
        this.fechaHoraInicio = fechaHoraInicio;
    }
    
    
    
    
    
    
    
    
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}
