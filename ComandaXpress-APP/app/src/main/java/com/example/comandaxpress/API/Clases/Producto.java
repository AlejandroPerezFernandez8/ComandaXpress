package com.example.comandaxpress.API.Clases;

import java.math.BigDecimal;

public class Prodcuto {
    private Long producto_id;
    private String nombre;
    private BigDecimal precio;
    private Long categoriaId;
    public Prodcuto() {}
    public Prodcuto(Long producto_id, String nombre, BigDecimal precio, Long categoriaId) {
        this.producto_id = producto_id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoriaId = categoriaId;
    }
    public Long getProducto_id() {return producto_id;}
    public void setProducto_id(Long producto_id) {this.producto_id = producto_id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public BigDecimal getPrecio() {return precio;}
    public void setPrecio(BigDecimal precio) {this.precio = precio;}
    public Long getCategoriaId() {return categoriaId;}
    public void setCategoriaId(Long categoriaId) {this.categoriaId = categoriaId;}
}
