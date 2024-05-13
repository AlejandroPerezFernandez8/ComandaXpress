package com.example.comandaxpress.API.Clases;

public class Usuario {
    private int usuario_id;
    private String nombre;
    private String apellido;
    private String email;
    private String usuario;
    private String contraseña;
    private String foto;

    public Usuario(){}
    public Usuario(String nombre, String apellido, String email, String usuario, String contraseña,String foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.foto = foto;
    }
    public int getUsuario_id() {return usuario_id;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public String getUsuario() {return usuario;}
    public String getContraseña() {return contraseña;}
    public String getFoto() {return foto;}
    public void setUsuario_id(int usuario_id) {this.usuario_id = usuario_id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setEmail(String email) {this.email = email;}
    public void setUsuario(String usuario) {this.usuario = usuario;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public void setFoto(String foto) {this.foto = foto;}
    @Override
    public String toString() {
        return "Usuario{" +
                "usuario_id=" + usuario_id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
