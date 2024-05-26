package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.TicketTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Mesa;
import comandaxpress.comandaxpress.desktop.Modelo.Ticket;
import comandaxpress.comandaxpress.desktop.Vista.PantallaTickets;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.query.Query;

public class ControladorTickets {
    private static final PantallaTickets ventana = new PantallaTickets();
    private static TicketTableModel modeloTabla;
    private static DefaultComboBoxModel<Ticket> modeloCombo;
    
    public static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        actualizarDatos();
    }

    public static List<Ticket> getListaTickets() {
        List<Ticket> tickets = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Ticket> query = session.createQuery("from Ticket", Ticket.class);
            tickets = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Error");
        }
        return tickets;
    }
    
    
    public static void actualizarDatos() {
        try{ 
            System.out.println(getListaTickets().toString());
            System.out.println("Se actualiza");
            modeloTabla = new TicketTableModel(getListaTickets());
            ventana.getTablaTickets().setModel(modeloTabla);
            modeloTabla.updateTable();
            modeloCombo = new DefaultComboBoxModel<>();
            modeloCombo.addAll(getListaTickets());
            ventana.getComboTickets().setModel(modeloCombo);
            if(ventana.getComboTickets().getModel().getSize() > 0){
                ventana.getComboTickets().setSelectedIndex(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

     public static void cambiarTicketTexto() {
        Ticket ticket = (Ticket) modeloCombo.getSelectedItem();
        if (ticket != null) {
            ventana.getTxtIDMesa().setText(ticket.getMesa().getNumero()+ "");
            ventana.getTxtIDTicket().setText(ticket.getTicketId() + "");
        }
    }
}
