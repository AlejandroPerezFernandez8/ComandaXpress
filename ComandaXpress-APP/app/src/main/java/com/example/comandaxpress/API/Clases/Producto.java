package com.example.comandaxpress.API.Clases;

import java.math.BigDecimal;

public class Producto {
    private Long productoId;
    private String nombre;
    private BigDecimal precio;
    private Long categoriaId;
    public Producto() {}
    public Producto(Long producto_id, String nombre, BigDecimal precio, Long categoriaId) {
        this.productoId = producto_id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoriaId = categoriaId;
    }
    public Long getProducto_id() {return productoId;}
    public void setProducto_id(Long producto_id) {this.productoId = producto_id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public BigDecimal getPrecio() {return precio;}
    public void setPrecio(BigDecimal precio) {this.precio = precio;}
    public Long getCategoriaId() {return categoriaId;}
    public void setCategoriaId(Long categoriaId) {this.categoriaId = categoriaId;}

    @Override
    public String toString() {
        return "Producto{" +
                "producto_id=" + productoId +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
