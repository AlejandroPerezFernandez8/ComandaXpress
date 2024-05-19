package com.example.comandaxpress.API.Clases;

public class TicketDetalleSimplificado {
    private long idTicket;

    private long idProducto;
    private int cantidad;

    public TicketDetalleSimplificado() {}
    public TicketDetalleSimplificado(long idTicket, long idProducto, int cantidad) {
        this.idTicket = idTicket;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }
    public long getIdTicket() {return idTicket;}
    public void setIdTicket(long idTicket) {this.idTicket = idTicket;}
    public long getIdProducto() {return idProducto;}
    public void setIdProducto(long idProducto) {this.idProducto = idProducto;}
    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
}
