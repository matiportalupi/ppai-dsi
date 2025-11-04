package com.example.modelo;

public class Usuario {
    private String nombreUsuario;
    private String contrasenia;
    private Empleado empleado;

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombreUsuario, String contrasenia, Empleado empleado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.empleado = empleado;
    }
    
    public String getNombreUsuario() { 
        return nombreUsuario; 
    }
    
    public void setNombreUsuario(String nombreUsuario) { 
        this.nombreUsuario = nombreUsuario; 
    }
    
    public String getContrasenia() { 
        return contrasenia; 
    }
    
    public void setContrasenia(String contrasenia) { 
        this.contrasenia = contrasenia; 
    }
    
    public Empleado getEmpleado() { 
        return empleado; 
    }
    
    public void setEmpleado(Empleado empleado) { 
        this.empleado = empleado; 
    }
    
    public Empleado getRILogueado() {
        return this.empleado;
    }
}
