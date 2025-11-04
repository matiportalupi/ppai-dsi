package com.example.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sismograma {
    private EventoSismico evento;
    private LocalDateTime fechaGeneracion;
    private String datos;
    private List<Float> amplitudes;
    private List<Float> frecuencias;

    public Sismograma(EventoSismico evento) {
        this.evento = evento;
        this.amplitudes = new ArrayList<>();
        this.frecuencias = new ArrayList<>();
        this.fechaGeneracion = LocalDateTime.now();
    }

    public void generar() {
        if (evento == null || evento.getSeriesTemporales() == null || evento.getSeriesTemporales().isEmpty()) {
            this.datos = "No hay datos de series temporales para generar sismograma.";
            return;
        }

        StringBuilder sismogramaData = new StringBuilder();
        sismogramaData.append("=== SISMOGRAMA GENERADO ===\n");
        sismogramaData.append("Fecha Generación: ").append(fechaGeneracion).append("\n");
        sismogramaData.append("Evento: ").append(evento.getLocalizacionGeografica()).append("\n");
        sismogramaData.append("Magnitud: ").append(evento.getValorMagnitud()).append("\n\n");

        int contador = 0;
        for (SerieTemporal serie : evento.getSeriesTemporales()) {
            sismogramaData.append("Serie Temporal #").append(++contador).append("\n");
            
            if (serie.getMuestras() != null) {
                for (MuestraSismica muestra : serie.getMuestras()) {
                    if (muestra.getDetallesMuestraSismica() != null) {
                        for (DetalleMuestraSismica detalle : muestra.getDetallesMuestraSismica()) {
                            float valor = detalle.getValor();
                            amplitudes.add(valor);
                            
                            if ("Frecuencia de onda".equals(detalle.getTipoDato().getDenominacion())) {
                                frecuencias.add(valor);
                            }
                        }
                    }
                }
            }
        }

        sismogramaData.append("Total de amplitudes registradas: ").append(amplitudes.size()).append("\n");
        sismogramaData.append("Frecuencias detectadas: ").append(frecuencias.size()).append("\n");
        this.datos = sismogramaData.toString();
    }

    public String obtenerDatos() {
        return this.datos;
    }

    public List<Float> getAmplitudes() {
        return new ArrayList<>(amplitudes);
    }

    public List<Float> getFrecuencias() {
        return new ArrayList<>(frecuencias);
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public EventoSismico getEvento() {
        return evento;
    }
}
