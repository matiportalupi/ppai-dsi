package com.example.modelo;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GestorRegistroResultado {
    private List<EventoSismico> eventosSismicos;
    private List<Estado> estados;
    private List<AlcanceSismo> alcancesSismo;
    private List<OrigenDeGeneracion> origenesGeneracion;
    private Empleado usuarioLogueado;
    
    
    private List<ClasificacionSismo> clasificacionesSismo;
    private Map<String, Estado> estadosCache;

    public GestorRegistroResultado() {
        this.estadosCache = new HashMap<>();
        cargarDatosDePrueba();
    }
    
    
    public void obtenerYAsignarClasificacion(EventoSismico evento) {
        if (evento == null) return;
        float profundidad = evento.getLongitudHipocentro();
        for (ClasificacionSismo clasificacion : this.clasificacionesSismo) {
            if (clasificacion.pertenece(profundidad)) {
                evento.setClasificacionSismo(clasificacion);
                return; 
            }
        }
    }
    public List<EventoSismico> buscarEventosSismicosAutoDetectados() {
        return eventosSismicos.stream() 
            .filter(evento -> evento.obtenerEstadoActual().map(estado -> {
                boolean esPendiente = estado.esAutoDetectado();
                boolean esMioEnRevision = estado.esBloqueadoEnRevision() && this.usuarioLogueado.equals(evento.getEmpleadoResponsable());
                return esPendiente || esMioEnRevision;
            }).orElse(false))
            .sorted(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia))
            .collect(Collectors.toList());
    }
    public void bloquearEventoSismico(EventoSismico evento) {
        if (evento == null) return;
        if (!evento.obtenerEstadoActual().map(Estado::esAutoDetectado).orElse(false)) {
            throw new IllegalStateException("El evento ya fue tomado por otro analista o ya no está pendiente.");
        }
        buscarEstadoPorNombre(Estado.EN_REVISION).ifPresent(evento::setEstado);
        evento.setEmpleadoResponsable(this.usuarioLogueado);
        System.out.println("Evento bloqueado para revisión: " + evento.getLocalizacionGeografica());
    }
    public void actualizarDatosEventoSismico(EventoSismico evento, float nuevoValorMagnitud, AlcanceSismo nuevoAlcance, OrigenDeGeneracion nuevoOrigen) {
        if (evento == null) return;
        if (!evento.obtenerEstadoActual().map(Estado::esBloqueadoEnRevision).orElse(false)) {
            throw new IllegalStateException("La acción no es válida: El evento debe estar 'En revisión' para ser modificado.");
        }
        evento.setMagnitud(new MagnitudRichter(evento.getNombreMagnitud(), nuevoValorMagnitud));
        evento.setAlcanceSismo(nuevoAlcance);
        evento.setOrigenDeGeneracion(nuevoOrigen);
    }
    public void confirmarEvento(EventoSismico evento) { finalizarRevision(evento, Estado.CONFIRMADO); }
    public void rechazarEvento(EventoSismico evento) { finalizarRevision(evento, Estado.RECHAZADO); }
    public void solicitarRevisionExperto(EventoSismico evento) { finalizarRevision(evento, Estado.DERIVADO_EXPERTO); }
    private void finalizarRevision(EventoSismico evento, String nombreEstadoFinal) {
        if (evento == null) return;
        if (!evento.obtenerEstadoActual().map(Estado::esBloqueadoEnRevision).orElse(false)) {
            throw new IllegalStateException("La acción no es válida: El evento debe estar 'En revisión' para ser finalizado.");
        }
        buscarEstadoPorNombre(nombreEstadoFinal).ifPresent(estadoFinal -> {
            evento.setEstado(estadoFinal);
            evento.setEmpleadoResponsable(this.usuarioLogueado);
            System.out.println("--- Revisión Finalizada ---");
            System.out.println("Evento: " + evento.getLocalizacionGeografica());
            System.out.println("Acción: " + estadoFinal.getNombre());
            System.out.println("Usuario: " + usuarioLogueado.getNombre());
            System.out.println("Fecha: " + LocalDateTime.now());
            System.out.println("---------------------------");
        });
    }
    private Optional<Estado> buscarEstadoPorNombre(String nombre) {
        if (estadosCache.containsKey(nombre)) {
            return Optional.of(estadosCache.get(nombre));
        }
        Optional<Estado> estado = estados.stream()
            .filter(e -> e.esAmbitoEventoSismico() && nombre.equals(e.getNombre()))
            .findFirst();
        estado.ifPresent(s -> estadosCache.put(nombre, s));
        return estado;
    }
    
    public List<AlcanceSismo> obtenerAlcancesSismo() { 
        return new ArrayList<>(this.alcancesSismo); 
    }

    public List<OrigenDeGeneracion> obtenerOrigenesGeneracion() { 
        return new ArrayList<>(this.origenesGeneracion); 
    }

    public Sismograma generarSismograma(EventoSismico evento) {
        if (evento == null) {
            return null;
        }
        return evento.generarSismograma();
    }

    public List<EstacionSismologica> obtenerEstacionesInvolucradas(EventoSismico evento) {
        if (evento == null) {
            return new ArrayList<>();
        }
        return evento.getEstacionesInvolucradas();
    }

    public void verMapa(EventoSismico evento) {
        if (evento == null) {
            System.out.println("No hay evento seleccionado para visualizar el mapa.");
            return;
        }
        List<EstacionSismologica> estaciones = evento.getEstacionesInvolucradas();
        System.out.println("--- Visualizando Mapa ---");
        System.out.println("Evento en: " + evento.getLocalizacionGeografica());
        System.out.println("Estaciones involucradas: " + estaciones.size());
        for (EstacionSismologica estacion : estaciones) {
            System.out.println("  - " + estacion.getNombreEstacion() + 
                             " (" + estacion.getLatitud() + ", " + estacion.getLongitud() + ")");
        }
    }

    public void registrarRevisionManual() {
        System.out.println("=== Iniciando Registro de Revisión Manual ===");
        List<EventoSismico> eventosPendientes = buscarEventosSismicosAutoDetectados();
        if (eventosPendientes.isEmpty()) {
            System.out.println("No hay eventos sísmicos autodetectados sin revisar.");
        } else {
            System.out.println("Se encontraron " + eventosPendientes.size() + " eventos pendientes de revisión.");
        }
    }

    public List<MuestraSismica> obtenerMuestrasPorSerie(SerieTemporal serie) {
        if (serie == null || serie.getMuestras() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(serie.getMuestras());
    }

    public List<DetalleMuestraSismica> obtenerDetallesPorMuestra(MuestraSismica muestra) {
        if (muestra == null || muestra.getDetallesMuestraSismica() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(muestra.getDetallesMuestraSismica());
    }

    public List<MuestraSismica> obtenerMuestrasPorEstacion(EventoSismico evento, EstacionSismologica estacion) {
        List<MuestraSismica> resultado = new ArrayList<>();
        if (evento == null || evento.getSeriesTemporales() == null) {
            return resultado;
        }
        for (SerieTemporal serie : evento.getSeriesTemporales()) {
            if (serie.getSismografo().getEstacion().equals(estacion)) {
                resultado.addAll(obtenerMuestrasPorSerie(serie));
            }
        }
        return resultado;
    }

    public List<DetalleMuestraSismica> obtenerDetallesPorTipo(EventoSismico evento, TipoDato tipo) {
        List<DetalleMuestraSismica> resultado = new ArrayList<>();
        if (evento == null || evento.getSeriesTemporales() == null) {
            return resultado;
        }
        for (SerieTemporal serie : evento.getSeriesTemporales()) {
            for (MuestraSismica muestra : obtenerMuestrasPorSerie(serie)) {
                for (DetalleMuestraSismica detalle : obtenerDetallesPorMuestra(muestra)) {
                    if (detalle.getTipoDato().equals(tipo)) {
                        resultado.add(detalle);
                    }
                }
            }
        }
        return resultado;
    }
    
    public String obtenerInfoMuestrasPorEstacion(EventoSismico evento) {
        if (evento == null || evento.getSeriesTemporales() == null || evento.getSeriesTemporales().isEmpty()) {
            return "El evento no tiene series temporales registradas para mostrar.";
        }
        StringBuilder resultado = new StringBuilder();
        Map<EstacionSismologica, List<SerieTemporal>> seriesPorEstacion = evento.getSeriesTemporales().stream()
                .collect(Collectors.groupingBy(serie -> serie.getSismografo().getEstacion()));
        for (Map.Entry<EstacionSismologica, List<SerieTemporal>> entry : seriesPorEstacion.entrySet()) {
            EstacionSismologica estacion = entry.getKey();
            resultado.append("--- Estación Sismológica: ").append(estacion.getNombreEstacion()).append(" ---\n");
            int contadorMuestras = 0;
            for (SerieTemporal serie : entry.getValue()) {
                if (serie.getMuestras() == null) continue;
                for (MuestraSismica muestra : serie.getMuestras()) {
                    contadorMuestras++;
                    resultado.append("\tInstante de Tiempo #").append(contadorMuestras).append(":\n");
                    if (muestra.getDetallesMuestraSismica() == null) continue;
                    for (DetalleMuestraSismica detalle : muestra.getDetallesMuestraSismica()) {
                        resultado.append("\t\t- ")
                                 .append(detalle.getTipoDato().getDenominacion()).append(": ")
                                 .append(String.format(Locale.US, "%.2f", detalle.getValor())).append(" ")
                                 .append(detalle.getTipoDato().getValorUnidadMedida()).append("\n");
                    }
                }
            }
            resultado.append("\n");
        }
        return resultado.toString();
    }
    
    private void cargarDatosDePrueba() {
        Rol rolAnalista = new Rol("Analista en Sismos", "Responsable del análisis y revisión manual de eventos sísmicos");
        this.usuarioLogueado = new Empleado("Kevin", "Matos", "kevin@email.com", "1234567890", rolAnalista);
        this.estados = new ArrayList<>(Arrays.asList(
            new Estado("Revision", Estado.PENDIENTE_REVISION, "Evento detectado."),
            new Estado("Revision", Estado.EN_REVISION, "Evento siendo analizado."),
            new Estado("Revision", Estado.CONFIRMADO, "Evento validado."),
            new Estado("Revision", Estado.RECHAZADO, "Evento descartado."),
            new Estado("Revision", Estado.DERIVADO_EXPERTO, "Requiere análisis experto.")
        ));
        this.alcancesSismo = new ArrayList<>(Arrays.asList(
            new AlcanceSismo("Local", "Afecta un área pequeña."), 
            new AlcanceSismo("Regional", "Afecta una región extensa."),
            new AlcanceSismo("Global", "Afecta a todo el planeta.")
        ));
        
        this.origenesGeneracion = new ArrayList<>(Arrays.asList(
            new OrigenDeGeneracion("Tectónico", "Movimiento de placas."), 
            new OrigenDeGeneracion("Volcánico", "Actividad volcánica."),
            new OrigenDeGeneracion("Inducido", "Actividad humana."),
            new OrigenDeGeneracion("Intraplaca", "Deformación interna de placas.")
        ));
        this.clasificacionesSismo = new ArrayList<>(Arrays.asList(
            new ClasificacionSismo("Superficial", 0, 70),
            new ClasificacionSismo("Intermedio", 70, 300),
            new ClasificacionSismo("Profundo", 300, Float.MAX_VALUE)
        ));
        
        this.eventosSismicos = new ArrayList<>();
        Estado estadoInicial = buscarEstadoPorNombre(Estado.PENDIENTE_REVISION)
                                .orElseThrow(() -> new IllegalStateException("El estado 'Pendiente de revisión' no pudo ser encontrado."));
        
        EstacionSismologica estacionCba = new EstacionSismologica("Observatorio de Córdoba", "CBA-01", -31.4, -64.1);
        EstacionSismologica estacionMza = new EstacionSismologica("Observatorio de Mendoza", "MZA-01", -32.89, -68.85);
        EstacionSismologica estacionBsAs = new EstacionSismologica("Observatorio de Buenos Aires", "BAS-01", -34.60, -58.38);
        
        Sismografo sismografoCba = new Sismografo(1, "Sismógrafo Principal CBA", estacionCba);
        Sismografo sismografoMza = new Sismografo(2, "Sismógrafo Principal MZA", estacionMza);
        Sismografo sismografoBsAs = new Sismografo(3, "Sismógrafo Principal BAS", estacionBsAs);
        
        TipoDato tipoVelocidad = new TipoDato("Velocidad de onda", "km/s");
        TipoDato tipoFrecuencia = new TipoDato("Frecuencia de onda", "Hz");
        TipoDato tipoAmplitud = new TipoDato("Amplitud de onda", "mm");
        
        // EVENTO 1: Córdoba - Leve
        EventoSismico evento1 = new EventoSismico(estadoInicial);
        evento1.setFechaHoraOcurrencia(LocalDateTime.of(2025, 10, 14, 21, 30, 16));
        evento1.setLatitudEpicentro(-31.4135f);
        evento1.setLongitudEpicentro(-64.1810f);
        evento1.setLongitudHipocentro(35.0f); 
        evento1.setMagnitud(new MagnitudRichter("Leve", 3.5f));
        evento1.setAlcanceSismo(this.alcancesSismo.get(0));
        evento1.setOrigenDeGeneracion(this.origenesGeneracion.get(0));
        
        SerieTemporal serie1 = new SerieTemporal(sismografoCba, "Normal", evento1.getFechaHoraOcurrencia(), 100f);
        MuestraSismica muestra1 = new MuestraSismica();
        muestra1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(5.5f, tipoVelocidad));
        muestra1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(3.2f, tipoFrecuencia));
        serie1.agregarMuestra(muestra1);
        evento1.getSeriesTemporales().add(serie1);
        this.eventosSismicos.add(evento1);
        
        // EVENTO 2: Mendoza - Moderado
        EventoSismico evento2 = new EventoSismico(estadoInicial);
        evento2.setFechaHoraOcurrencia(LocalDateTime.of(2025, 10, 15, 0, 30, 16));
        evento2.setLatitudEpicentro(-32.8895f);
        evento2.setLongitudEpicentro(-68.8458f);
        evento2.setLongitudHipocentro(150.0f); 
        evento2.setMagnitud(new MagnitudRichter("Moderado", 4.8f));
        evento2.setAlcanceSismo(this.alcancesSismo.get(1));
        evento2.setOrigenDeGeneracion(this.origenesGeneracion.get(0));
        
        SerieTemporal serie2 = new SerieTemporal(sismografoMza, "Normal", evento2.getFechaHoraOcurrencia(), 150f);
        MuestraSismica muestra2_1 = new MuestraSismica();
        muestra2_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(8.3f, tipoVelocidad));
        muestra2_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(4.1f, tipoFrecuencia));
        muestra2_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(12.5f, tipoAmplitud));
        serie2.agregarMuestra(muestra2_1);
        
        MuestraSismica muestra2_2 = new MuestraSismica();
        muestra2_2.getDetallesMuestraSismica().add(new DetalleMuestraSismica(7.9f, tipoVelocidad));
        muestra2_2.getDetallesMuestraSismica().add(new DetalleMuestraSismica(3.8f, tipoFrecuencia));
        serie2.agregarMuestra(muestra2_2);
        
        evento2.getSeriesTemporales().add(serie2);
        this.eventosSismicos.add(evento2);
        
        // EVENTO 3: Buenos Aires - Leve
        EventoSismico evento3 = new EventoSismico(estadoInicial);
        evento3.setFechaHoraOcurrencia(LocalDateTime.of(2025, 10, 15, 8, 45, 22));
        evento3.setLatitudEpicentro(-34.6037f);
        evento3.setLongitudEpicentro(-58.3816f);
        evento3.setLongitudHipocentro(20.0f); 
        evento3.setMagnitud(new MagnitudRichter("Leve", 2.9f));
        evento3.setAlcanceSismo(this.alcancesSismo.get(0));
        evento3.setOrigenDeGeneracion(this.origenesGeneracion.get(3));
        
        SerieTemporal serie3 = new SerieTemporal(sismografoBsAs, "Normal", evento3.getFechaHoraOcurrencia(), 80f);
        MuestraSismica muestra3 = new MuestraSismica();
        muestra3.getDetallesMuestraSismica().add(new DetalleMuestraSismica(4.2f, tipoVelocidad));
        muestra3.getDetallesMuestraSismica().add(new DetalleMuestraSismica(2.8f, tipoFrecuencia));
        serie3.agregarMuestra(muestra3);
        evento3.getSeriesTemporales().add(serie3);
        this.eventosSismicos.add(evento3);
        
        // EVENTO 4: Mendoza - Fuerte (Volcánico)
        EventoSismico evento4 = new EventoSismico(estadoInicial);
        evento4.setFechaHoraOcurrencia(LocalDateTime.of(2025, 10, 16, 14, 15, 5));
        evento4.setLatitudEpicentro(-32.9505f);
        evento4.setLongitudEpicentro(-70.2708f);
        evento4.setLongitudHipocentro(8.0f); 
        evento4.setMagnitud(new MagnitudRichter("Fuerte", 5.6f));
        evento4.setAlcanceSismo(this.alcancesSismo.get(2));
        evento4.setOrigenDeGeneracion(this.origenesGeneracion.get(1));
        
        SerieTemporal serie4a = new SerieTemporal(sismografoMza, "Normal", evento4.getFechaHoraOcurrencia(), 200f);
        MuestraSismica muestra4a_1 = new MuestraSismica();
        muestra4a_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(12.7f, tipoVelocidad));
        muestra4a_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(5.3f, tipoFrecuencia));
        muestra4a_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(25.8f, tipoAmplitud));
        serie4a.agregarMuestra(muestra4a_1);
        
        MuestraSismica muestra4a_2 = new MuestraSismica();
        muestra4a_2.getDetallesMuestraSismica().add(new DetalleMuestraSismica(11.5f, tipoVelocidad));
        muestra4a_2.getDetallesMuestraSismica().add(new DetalleMuestraSismica(4.9f, tipoFrecuencia));
        serie4a.agregarMuestra(muestra4a_2);
        
        evento4.getSeriesTemporales().add(serie4a);
        this.eventosSismicos.add(evento4);
        
        // EVENTO 5: Córdoba - Moderado
        EventoSismico evento5 = new EventoSismico(estadoInicial);
        evento5.setFechaHoraOcurrencia(LocalDateTime.of(2025, 10, 17, 3, 20, 44));
        evento5.setLatitudEpicentro(-31.2667f);
        evento5.setLongitudEpicentro(-64.2667f);
        evento5.setLongitudHipocentro(85.0f); 
        evento5.setMagnitud(new MagnitudRichter("Moderado", 4.2f));
        evento5.setAlcanceSismo(this.alcancesSismo.get(1));
        evento5.setOrigenDeGeneracion(this.origenesGeneracion.get(0));
        
        SerieTemporal serie5 = new SerieTemporal(sismografoCba, "Normal", evento5.getFechaHoraOcurrencia(), 120f);
        MuestraSismica muestra5_1 = new MuestraSismica();
        muestra5_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(6.8f, tipoVelocidad));
        muestra5_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(3.5f, tipoFrecuencia));
        muestra5_1.getDetallesMuestraSismica().add(new DetalleMuestraSismica(8.9f, tipoAmplitud));
        serie5.agregarMuestra(muestra5_1);
        
        MuestraSismica muestra5_2 = new MuestraSismica();
        muestra5_2.getDetallesMuestraSismica().add(new DetalleMuestraSismica(6.3f, tipoVelocidad));
        muestra5_2.getDetallesMuestraSismica().add(new DetalleMuestraSismica(3.2f, tipoFrecuencia));
        serie5.agregarMuestra(muestra5_2);
        
        evento5.getSeriesTemporales().add(serie5);
        this.eventosSismicos.add(evento5);
        
        // EVENTO 6: Buenos Aires - Moderado Profundo
        EventoSismico evento6 = new EventoSismico(estadoInicial);
        evento6.setFechaHoraOcurrencia(LocalDateTime.of(2025, 10, 17, 18, 55, 30));
        evento6.setLatitudEpicentro(-33.8688f);
        evento6.setLongitudEpicentro(-59.5265f);
        evento6.setLongitudHipocentro(250.0f); 
        evento6.setMagnitud(new MagnitudRichter("Moderado", 4.5f));
        evento6.setAlcanceSismo(this.alcancesSismo.get(1));
        evento6.setOrigenDeGeneracion(this.origenesGeneracion.get(3));
        
        SerieTemporal serie6a = new SerieTemporal(sismografoBsAs, "Normal", evento6.getFechaHoraOcurrencia(), 140f);
        MuestraSismica muestra6a = new MuestraSismica();
        muestra6a.getDetallesMuestraSismica().add(new DetalleMuestraSismica(7.4f, tipoVelocidad));
        muestra6a.getDetallesMuestraSismica().add(new DetalleMuestraSismica(3.9f, tipoFrecuencia));
        muestra6a.getDetallesMuestraSismica().add(new DetalleMuestraSismica(11.2f, tipoAmplitud));
        serie6a.agregarMuestra(muestra6a);
        
        evento6.getSeriesTemporales().add(serie6a);
        this.eventosSismicos.add(evento6);
    }
}
