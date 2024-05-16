package com.example.comandaxpress.API.Clases;

public class Categoria {
    private Long categoriaId;
    private String nombre;
    private String descripcion;
    public Categoria() {}
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public Categoria(Long categoriaId, String nombre, String descripcion) {
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    public Long getCategoriaId() {return categoriaId;}
    public String getDescripcion() {return descripcion;}
    public String getNombre() {return nombre;}
    public void setCategoriaId(Long categoriaId) {this.categoriaId = categoriaId;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}
