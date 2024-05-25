package ComandaXpress;

public class Api_Map {
    public static final String BASE_URL = "http://localhost:8080";
    /**-------------------------CATEGORIA--------------------------------*/
    public static final String CATEGORIA_BASE_URL = "/categoria";
    public static final String CATEGORIA_ID_URL = "/{categoriaId}";
    /**---------------------------MESA----------------------------------*/
    public static final String MESA_BASE_URL = "/mesa";
    public static final String MESA_ID_URL = "/{mesaId}";
    /**-------------------------PRODUCTO--------------------------------*/
    public static final String PRODUCTO_BASE_URL = "/productos";
    public static final String PRODUCTO_ID_URL = "/{productoId}";
    public static final String PRODUCTO_CATEGORIAID_URL = "/categoria/{categoriaId}";
    public static final String PRODUCTO_SUMA_TOTAL = "/suma";
    /**-------------------------TICKETS--------------------------------*/
    public static final String TICKET_BASE_URL = "/ticket";
    public static final String TICKET_ID_URL = "/{ticketId}";
    /**-------------------------TICKETDETALLE--------------------------------*/
    public static final String TICKET_DETALLE_BASE_URL = "/ticketDetalle";
    public static final String TICKET_DETALLE_ID_URL = "/{idTicket}";
    public static final String TICKET_DETALLE_GUARDAR = "/guardar";
    /**-------------------------USUARIOS--------------------------------*/
    public static final String USUARIO_BASE_URL = "/usuarios";
    public static final String USUARIO_LOGIN_URL = "/login";
    public static final String USUARIO_GUARDAR_URL = "/saveUsuarios";
    public static final String USUARIO_MODIFICAR_URL = "/modificarUsuario/{usuario_id}";
    public static final String USUARIO_ELIMINAR_URL = "/borrarUsuario/{usuario_id}";

}
