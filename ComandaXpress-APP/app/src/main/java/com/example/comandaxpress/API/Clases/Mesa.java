package com.example.comandaxpress.API.Clases;

import java.util.List;

public class Mesa {
    private Long mesaId;
    private Integer numero;
    private Integer capacidad;
    private Boolean activa;
    private List<Long> tickets;

    public Long getMesaId() {return mesaId;}
    public void setMesaId(Long mesaId) {this.mesaId = mesaId;}
    public Integer getNumero() {return numero;}
    public void setNumero(Integer numero) {this.numero = numero;}
    public Integer getCapacidad() {return capacidad;}
    public void setCapacidad(Integer capacidad) {this.capacidad = capacidad;}
    public Boolean getActiva() {return activa;}
    public void setActiva(Boolean activa) {this.activa = activa;}
    public List<Long> getTickets() {return tickets;}
    public void setTickets(List<Long> tickets) {tickets = tickets;}

    @Override
    public String toString() {
        return "Mesa{" +
                "mesaId=" + mesaId +
                ", numero=" + numero +
                ", capacidad=" + capacidad +
                ", activa=" + activa +
                ", Tickets=" + tickets +
                '}';
    }
}
