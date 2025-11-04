package com.example.modelo;
import java.time.LocalDateTime;
public class Sesion {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Usuario usuario;
    public Sesion(Usuario usuario) {
        this.usuario = usuario;
        this.fechaHoraInicio = LocalDateTime.now();
        this.fechaHoraFin = null; 
    }
    
    public void finalizarSesion() {
        this.fechaHoraFin = LocalDateTime.now();
    }
    
    
    public Usuario getUsuarioLogueado() {
        return this.usuario;
    }
    
    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }
    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
