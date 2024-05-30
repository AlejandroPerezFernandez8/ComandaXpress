package com.example.comandaxpress.API.Clases;

public class FiltroTicket {
    String idMesa = null;
    String fecha = null;

    public FiltroTicket(){}

    public FiltroTicket(String idMesa, String fecha) {
        this.idMesa = idMesa;
        this.fecha = fecha;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
