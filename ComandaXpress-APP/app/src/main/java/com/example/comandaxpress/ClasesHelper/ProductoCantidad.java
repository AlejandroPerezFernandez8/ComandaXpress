package com.example.comandaxpress.ClasesHelper;

public class ProductoCantidad {
    String Producto;
    int cantidad;
    public ProductoCantidad(String producto, int cantidad) {
        Producto = producto;
        this.cantidad = cantidad;
    }
    public String getProducto() {return Producto;}
    public void setProducto(String producto) {Producto = producto;}
    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
}
