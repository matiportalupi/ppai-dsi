package com.example.modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class SerieTemporal {
    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestras;
    private LocalDateTime fechaHoraRegistro;
    private float frecuenciaMuestreo;
    private List<MuestraSismica> muestras;
    private Sismografo sismografo; 
    public SerieTemporal(Sismografo sismografo, String condicionAlarma, LocalDateTime fechaHoraInicioRegistroMuestras, float frecuenciaMuestreo) {
        this.sismografo = sismografo; 
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraInicioRegistroMuestras = fechaHoraInicioRegistroMuestras;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.fechaHoraRegistro = LocalDateTime.now();
        this.muestras = new ArrayList<>();
    }
    
    public List<MuestraSismica> getMuestras() {
        return this.muestras;
    }
    
    public void agregarMuestra(MuestraSismica muestra) {
        this.muestras.add(muestra);
    }
    
    public Sismografo getSismografo() { 
        return sismografo;
    }
    public void setSismografo(Sismografo sismografo) {
        this.sismografo = sismografo;
    }
    public String getCondicionAlarma() {
        return condicionAlarma;
    }
    public void setCondicionAlarma(String condicionAlarma) {
        this.condicionAlarma = condicionAlarma;
    }
    public LocalDateTime getFechaHoraInicioRegistroMuestras() {
        return fechaHoraInicioRegistroMuestras;
    }
    public void setFechaHoraInicioRegistroMuestras(LocalDateTime fechaHoraInicioRegistroMuestras) {
        this.fechaHoraInicioRegistroMuestras = fechaHoraInicioRegistroMuestras;
    }
    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
    public float getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }
    public void setFrecuenciaMuestreo(float frecuenciaMuestreo) {
        this.frecuenciaMuestreo = frecuenciaMuestreo;
    }
    
    public void setMuestras(List<MuestraSismica> muestras) {
        this.muestras = muestras;
    }
}
