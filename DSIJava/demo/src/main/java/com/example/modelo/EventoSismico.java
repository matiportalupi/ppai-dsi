package com.example.modelo;
import java.time.LocalDateTime;
import java.util.*;
public class EventoSismico {
    private LocalDateTime fechaHoraOcurrencia;
    private float latitudEpicentro;
    private float longitudEpicentro;
    private float longitudHipocentro;
    private MagnitudRichter magnitud;
    private OrigenDeGeneracion origenDeGeneracion;
    private ClasificacionSismo clasificacionSismo;
    private AlcanceSismo alcanceSismo;
    private List<SerieTemporal> seriesTemporales;
    private List<CambioEstado> historialDeEstados;
    private Estado estadoActualCache;
    private Empleado empleadoResponsable;

    public EventoSismico(Estado estadoInicial) {
        this.historialDeEstados = new ArrayList<>();
        this.seriesTemporales = new ArrayList<>();
        this.estadoActualCache = estadoInicial;
        crearNuevoCambioEstado(estadoInicial, LocalDateTime.now());
    }
    
    
    public Map<String, Object> getDatosEventoSismico() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("fechaHoraOcurrencia", this.getFechaHoraOcurrencia());
        datos.put("latitudEpicentro", this.getLatitudEpicentro());
        datos.put("longitudEpicentro", this.getLongitudEpicentro());
        datos.put("valorMagnitud", this.getValorMagnitud());
        datos.put("alcanceSismo", this.getAlcanceSismo());
        datos.put("origenDeGeneracion", this.getOrigenDeGeneracion());
        return datos;
    }
    public Optional<Estado> obtenerEstadoActual() {
        return Optional.ofNullable(this.estadoActualCache);
    }
    public void setEstado(Estado nuevoEstado) {
        this.estadoActualCache = nuevoEstado;
        crearNuevoCambioEstado(nuevoEstado, LocalDateTime.now());
    }

    public List<CambioEstado> obtenerHistorialEstados() {
        return new ArrayList<>(this.historialDeEstados);
    }
    private void crearNuevoCambioEstado(Estado estado, LocalDateTime fechaHoraInicio) {
        this.historialDeEstados.add(new CambioEstado(estado, fechaHoraInicio));
    }
    
    public float getValorMagnitud() {
        return this.magnitud != null ? this.magnitud.getValor() : 0.0f;
    }
    
    public LocalDateTime getFechaHoraOcurrencia() { return fechaHoraOcurrencia; }
    public void setFechaHoraOcurrencia(LocalDateTime fechaHoraOcurrencia) { this.fechaHoraOcurrencia = fechaHoraOcurrencia; }
    public float getLatitudEpicentro() { return latitudEpicentro; }
    public void setLatitudEpicentro(float latitudEpicentro) { this.latitudEpicentro = latitudEpicentro; }
    public float getLongitudEpicentro() { return longitudEpicentro; }
    public void setLongitudEpicentro(float longitudEpicentro) { this.longitudEpicentro = longitudEpicentro; }
    public float getLongitudHipocentro() { return longitudHipocentro; }
    public void setLongitudHipocentro(float longitudHipocentro) { this.longitudHipocentro = longitudHipocentro; }
    public MagnitudRichter getMagnitud() { return magnitud; }
    public void setMagnitud(MagnitudRichter magnitud) { this.magnitud = magnitud; }
    public OrigenDeGeneracion getOrigenDeGeneracion() { return origenDeGeneracion; }
    public void setOrigenDeGeneracion(OrigenDeGeneracion origenDeGeneracion) { this.origenDeGeneracion = origenDeGeneracion; }
    public ClasificacionSismo getClasificacionSismo() { return clasificacionSismo; }
    public void setClasificacionSismo(ClasificacionSismo clasificacionSismo) { this.clasificacionSismo = clasificacionSismo; }
    public AlcanceSismo getAlcanceSismo() { return alcanceSismo; }
    public void setAlcanceSismo(AlcanceSismo alcanceSismo) { this.alcanceSismo = alcanceSismo; }
    public List<SerieTemporal> getSeriesTemporales() { return seriesTemporales; }
    public void setSeriesTemporales(List<SerieTemporal> seriesTemporales) { this.seriesTemporales = seriesTemporales; }
    public String getLocalizacionGeografica() { return String.format("%.4f, %.4f", this.latitudEpicentro, this.longitudEpicentro); }
    
    public Sismograma generarSismograma() {
        Sismograma sismograma = new Sismograma(this);
        sismograma.generar();
        return sismograma;
    }

    public List<EstacionSismologica> getEstacionesInvolucradas() {
        List<EstacionSismologica> estaciones = new ArrayList<>();
        if (this.seriesTemporales != null) {
            for (SerieTemporal serie : this.seriesTemporales) {
                if (serie.getSismografo() != null && serie.getSismografo().getEstacion() != null) {
                    EstacionSismologica estacion = serie.getSismografo().getEstacion();
                    if (!estaciones.contains(estacion)) {
                        estaciones.add(estacion);
                    }
                }
            }
        }
        return estaciones;
    }

    public Empleado getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void setEmpleadoResponsable(Empleado empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }
}
