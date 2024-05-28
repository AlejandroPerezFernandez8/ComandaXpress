package comandaxpress.comandaxpress.desktop.Controlador;

import comandaxpress.comandaxpress.desktop.Vista.PantallaPrincipal;

public class ControladorPrincipal {
    private static final PantallaPrincipal ventana = new PantallaPrincipal();
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciarPantallaUsuarios() {
          ControladorUsuarios.iniciar();
    }

    public static void iniciarPantallaMesas() {
        ControladorMesas.iniciar();
    }
    
    public static void iniciarPantallaTickets() {
        ControladorTickets.iniciar();
    }

    public static void inciarPantallaTicketDetalle() {
        ControladorTicketDetalle.iniciar();
    }

    public static void iniciarPantallaProductos() {
        ControladorProductos.iniciar();
    }

    public static void iniciarPantallaCategorias() {
        ControladorCategorias.iniciar();
    }
    
    
}
