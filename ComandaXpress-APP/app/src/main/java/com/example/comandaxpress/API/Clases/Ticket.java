package com.example.comandaxpress.API.Clases;

import java.util.Date;
import java.util.List;

public class Ticket {
    private Long ticketId;
    private Long mesaId;
    private Date fechaHora;
    private List<Long> ticketDetallesID;
    public Ticket() {}
    public Ticket(Long ticketId, Long mesaId, Date fechaHora, List<Long> ticketDetallesID) {
        this.ticketId = ticketId;
        this.mesaId = mesaId;
        this.fechaHora = fechaHora;
        this.ticketDetallesID = ticketDetallesID;
    }
    public Ticket(Long mesaId) {
        this.mesaId = mesaId;
    }
    public Long getTicketId() {return ticketId;}
    public void setTicketId(Long ticketId) {this.ticketId = ticketId;}
    public Long getMesaId() {return mesaId;}
    public void setMesaId(Long mesaId) {this.mesaId = mesaId;}
    public Date getFechaHora() {return fechaHora;}
    public void setFechaHora(Date fechaHora) {this.fechaHora = fechaHora;}
    public List<Long> getTicketDetallesID() {return ticketDetallesID;}
    public void setTicketDetallesID(List<Long> ticketDetallesID) {this.ticketDetallesID = ticketDetallesID;}
}
