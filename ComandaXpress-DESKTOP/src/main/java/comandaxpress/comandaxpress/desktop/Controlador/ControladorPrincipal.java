/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comandaxpress.comandaxpress.desktop.Controlador;

import comandaxpress.comandaxpress.desktop.Vista.PantallaPrincipal;

/**
 *
 * @author Juegos
 */
public class ControladorPrincipal {
    private static PantallaPrincipal ventana = new PantallaPrincipal();
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciarPantallaUsuarios() {
    }
}
