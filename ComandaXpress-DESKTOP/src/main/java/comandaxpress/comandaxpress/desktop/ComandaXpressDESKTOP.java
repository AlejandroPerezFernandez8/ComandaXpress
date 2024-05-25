package comandaxpress.comandaxpress.desktop;

import com.formdev.flatlaf.FlatDarkLaf;
import comandaxpress.comandaxpress.desktop.Controlador.ControladorPrincipal;
import javax.swing.UIDefaults;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ComandaXpressDESKTOP {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        try {
           UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
        }
        ControladorPrincipal.iniciar();
    }
}
