package com.example.comandaxpress.ClasesHelper;

import com.example.comandaxpress.API.Clases.Producto;

public class ProductoCantidad {
    Producto Producto;
    int cantidad;
    public ProductoCantidad(Producto producto, int cantidad) {
        Producto = producto;
        this.cantidad = cantidad;
    }
    public Producto getProducto() {return Producto;}
    public void setProducto(Producto producto) {Producto = producto;}
    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
}
