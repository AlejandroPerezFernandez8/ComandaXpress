package com.example.comandaxpress.API;

import com.example.comandaxpress.API.Clases.FiltroTicket;

public class ApiMap {
    private static String IP = "";
    private static final String PORT = ":8080/";
    public static String getBaseUrl() {
        return "http://" + getIP() + PORT;
    }

    //----------------------------------USUARIOS--------------------------------------------------------------
    public static String getUrlUsuarioLogin() {
        return getBaseUrl() + "usuarios/login";
    }
    public static String getUrlUsuarioRegistro() {return getBaseUrl() + "usuarios/saveUsuarios";}
    public static String getUrlUsuarioModificar(long idUsuario) {return getBaseUrl() + "usuarios/modificarUsuario/"+idUsuario;}
    //----------------------------------PRODUCTOS------------------------------------------------------------
    public static String getUrlProductosCategoria(long idCategoria) {return getBaseUrl() + "productos/categoria/"+idCategoria;}
    //-----------------------------------MESAS--------------------------------------------------------------
    public static String getUrlMesa() {return getBaseUrl() + "mesa";}
    public static String getUrlMesaModificar(Long idMesa) {return getBaseUrl() + "mesa/" + idMesa;}
    //----------------------------------CATEGORIAS--------------------------------------------------
    public static String getUrlCategoria() {return getBaseUrl() + "categoria";}
    //-----------------------------------TICKETS----------------------------------------------------------
    public static String getUrlTicket() {return getBaseUrl() + "ticket";}

    public static String getUrlTicketFiltros(FiltroTicket filtro) {
        if(filtro.getIdMesa()!= null && filtro.getFecha()!= null){
            return getBaseUrl() + "ticket/filtros?numeroMesa="+ filtro.getIdMesa()+"&fecha="+filtro.getFecha();
        }else if(filtro.getIdMesa() != null){
            return getBaseUrl() + "ticket/filtros?numeroMesa="+ filtro.getIdMesa();
        } else if (filtro.getFecha()!= null) {
            return getBaseUrl() + "ticket/filtros?fecha="+filtro.getFecha();
        }else {
            return getBaseUrl() + "ticket/filtros";
        }
    }
    //-----------------------------------DETALLES DE LOS TICKETS----------------------------------------------------------
    public static String getUrlTicketDetalle(long idTicket) {return getBaseUrl() + "ticketDetalle/"+idTicket;}
    public static String getUrlTicketDetalleGuardar() {return getBaseUrl() + "ticketDetalle/guardar";}
    public static String getUrlTicketDetalleEliminar() {return getBaseUrl() + "ticketDetalle/eliminar";}

    //--------------------------------CONFIGURACION DE IP--------------------------------------------------
    public static void setIP(String ip) {
        IP = ip;
    }
    public static String getIP() {
        return IP;
    }
}
