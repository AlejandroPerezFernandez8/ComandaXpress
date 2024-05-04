package com.example.comandaxpress.API.Clases;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String usuario;
    private String contraseña;
    private String rol;

    public Usuario(){}
    public Usuario(String nombre, String apellido, String email, String usuario, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = "admin";
    }

    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public String getUsuario() {return usuario;}
    public String getContraseña() {return contraseña;}
    public String getRol() {return rol;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setEmail(String email) {this.email = email;}
    public void setUsuario(String usuario) {this.usuario = usuario;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public void setRol(String rol) {this.rol = rol;}
}
