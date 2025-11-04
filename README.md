# Sismógrafo App - Aplicación de Registro de Eventos Sísmicos

## 📋 Descripción
Aplicación JavaFX para registrar, revisar y gestionar eventos sísmicos con interfaz gráfica y gestión de bases de datos.

---

## ⚡ Instrucciones de Ejecución

### IMPORTANTE: Cambiar a la carpeta demo
```powershell
cd C:\Users\Space\Desktop\DSI\DSI\DSIJava\demo
```

### Requisitos previos
- **JDK 11 o superior** instalado y en PATH
- **Maven** instalado y en PATH

### Verificar instalación
```powershell
java -version
mvn -v
```

### Ejecutar la aplicación

#### Opción A: Ejecutar con Maven (recomendado)
```powershell
mvn clean javafx:run
```

#### Opción B: Compilar y ejecutar por separado
```powershell
mvn clean package
mvn javafx:run
```

#### Opción C: Desde VS Code (si necesitas debug)
- Usa la configuración de lanzamiento (Launch MainApp) en `.vscode/launch.json`
- Ajusta los paths de JavaFX SDK si es necesario

---

## 📁 Estructura del Proyecto

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── MainApp.java                          # Punto de entrada de la aplicación
│   │   │       ├── controladores/
│   │   │       │   └── PantallaRegistrarResultadoController.java
│   │   │       └── modelo/
│   │   │           ├── AlcanceSismo.java
│   │   │           ├── CambioEstado.java
│   │   │           ├── ClasificacionSismo.java
│   │   │           ├── DetalleMuestraSismica.java
│   │   │           ├── Empleado.java
│   │   │           ├── EstacionSismologica.java
│   │   │           ├── Estado.java
│   │   │           ├── EventoSismico.java
│   │   │           ├── EventoSismicoDTO.java
│   │   │           ├── GestorRegistroResultado.java
│   │   │           ├── MagnitudRichter.java
│   │   │           ├── MuestraSismica.java
│   │   │           ├── OrigenDeGeneracion.java
│   │   │           ├── SerieTemporal.java
│   │   │           ├── Sesion.java
│   │   │           ├── Sismografo.java
│   │   │           ├── TipoDato.java
│   │   │           └── Usuario.java
│   │   └── resources/
│   │       └── com/example/
│   │           └── InterfazGrafica/
│   │               └── PantallaRegistrarResultado.fxml
│   └── test/
├── target/                                              # Carpeta generada por Maven (compilados)
├── pom.xml                                              # Configuración de Maven
├── README.md                                            # Este archivo
└── .vscode/                                             # Configuración de VS Code
    └── launch.json                                      # Configuración de debug
```

### Descripción de carpetas principales:
- **src/main/java/** - Código fuente Java
  - **controladores/** - Controladores de la interfaz gráfica
  - **modelo/** - Clases de modelo de datos
- **src/main/resources/** - Archivos de recursos (FXML, imágenes, etc.)
- **target/** - Archivos compilados (generado automáticamente por Maven)
- **pom.xml** - Configuración del proyecto Maven con dependencias

---

## 🛠️ Solución de Problemas

### Error: "El comando mvn no se reconoce"
- Verifica que Maven esté instalado: `mvn -v`
- Añade Maven a la variable PATH del sistema

### Error: "Java no está disponible"
- Verifica que JDK esté instalado: `java -version`
- Asegúrate de tener JDK 11+ (no solo JRE)

### La aplicación no inicia
- Asegúrate de estar en la carpeta `demo` antes de ejecutar
- Intenta: `mvn clean` y luego `mvn javafx:run`

---

## 📝 Notas
- La aplicación utiliza JavaFX 17.0.2
- El compilador Java es versión 1.8+ (compatible con Java moderno)
- La interfaz se define en archivos FXML
