package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.TicketTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Mesa;
import comandaxpress.comandaxpress.desktop.Modelo.Ticket;
import comandaxpress.comandaxpress.desktop.Vista.PantallaTickets;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.query.Query;

public class ControladorTickets {

    private static final PantallaTickets ventana = new PantallaTickets();
    private static TicketTableModel modeloTabla;
    private static DefaultComboBoxModel<Ticket> modeloCombo;

    public static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.getComboFechaHora().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        actualizarDatos();
        if (ventana.getComboTickets().getModel().getSize() > 0) {
            ventana.getComboTickets().setSelectedIndex(0);
        }
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
        try {
            System.out.println(getListaTickets().toString());
            System.out.println("Se actualiza");
            modeloTabla = new TicketTableModel(getListaTickets());
            ventana.getTablaTickets().setModel(modeloTabla);
            modeloTabla.updateTable();
            modeloCombo = new DefaultComboBoxModel<>();
            modeloCombo.addAll(getListaTickets());
            ventana.getComboTickets().setModel(modeloCombo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void cambiarTicketTexto() {
        Ticket ticket = (Ticket) modeloCombo.getSelectedItem();
        if (ticket != null) {
            ventana.getTxtIDMesa().setText(ticket.getMesa().getNumero() + "");
            ventana.getTxtIDTicket().setText(ticket.getTicketId() + "");
            ventana.getComboFechaHora().setDate(ticket.getFechaHora());
        }
    }

    public static void insertarTicket() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String numeroMesaStr = ventana.getTxtIDMesa().getText().trim();
            if (numeroMesaStr.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El numero de mesa es obligatorio");
                return;
            }

            Integer numeroMesa = Integer.parseInt(numeroMesaStr);
            Query<Mesa> query = session.createQuery("from Mesa where numero = :numeroMesa", Mesa.class);
            query.setParameter("numeroMesa", numeroMesa);
            Mesa mesa = query.uniqueResult();
            if (mesa == null) {
                JOptionPane.showMessageDialog(ventana, "Mesa no encontrada.");
                return;
            }

            Ticket ticket = new Ticket();
            ticket.setMesa(mesa);

            Date fechaHora = ventana.getComboFechaHora().getDate();
            if (fechaHora == null) {
                fechaHora = new Date();
            }
            ticket.setFechaHora(fechaHora);

            String idTicketStr = ventana.getTxtIDTicket().getText().trim();
            if (!idTicketStr.isEmpty()) {
                Long idTicket = Long.parseLong(idTicketStr);
                ticket.setTicketId(idTicket);
            }

            session.save(ticket);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Ticket insertado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al insertar ticket: " + e.getMessage());
        } finally {
            actualizarDatos();
        }
    }

    public static void modificarTicket() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Obtener el ID del ticket desde la interfaz
            String idTicketStr = ventana.getTxtIDTicket().getText().trim();
            if (idTicketStr.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El Id del ticket es obligatorio");
                return;
            }

            Long idTicket = Long.parseLong(idTicketStr);
            Ticket ticket = session.get(Ticket.class, idTicket);
            if (ticket == null) {
                JOptionPane.showMessageDialog(ventana, "Ticket no encontrado.");
                return;
            }

            // Obtener el número de la mesa desde la interfaz
            String numeroMesaStr = ventana.getTxtIDMesa().getText().trim();
            if (!numeroMesaStr.isEmpty()) {
                Integer numeroMesa = Integer.parseInt(numeroMesaStr);
                Query<Mesa> query = session.createQuery("from Mesa where numero = :numeroMesa", Mesa.class);
                query.setParameter("numeroMesa", numeroMesa);
                Mesa mesa = query.uniqueResult();
                if (mesa == null) {
                    JOptionPane.showMessageDialog(ventana, "Mesa no encontrada.");
                    return;
                }
                ticket.setMesa(mesa);
            }

            Date fechaHora = ventana.getComboFechaHora().getDate();
            if (fechaHora != null) {
                ticket.setFechaHora(fechaHora);
            }

            session.update(ticket);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Ticket modificado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al modificar ticket: ");
        } finally {
            actualizarDatos();
        }
    }

  public static void eliminarTicket() {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        // Obtener el ID del ticket desde la interfaz
        String idTicketStr = ventana.getTxtIDTicket().getText().trim();
        if (idTicketStr.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Id de ticket obligatorio.");
            return;
        }

        Long idTicket = Long.parseLong(idTicketStr);
        Ticket ticket = session.get(Ticket.class, idTicket);
        if (ticket == null) {
            JOptionPane.showMessageDialog(ventana, "Ticket no encontrado.");
            return;
        }

        // Obtener la mesa asociada al ticket
        Mesa mesa = ticket.getMesa();

        // Verificar si el ticket a eliminar es el último en la lista de tickets de la mesa
        List<Ticket> ticketsDeMesa = mesa.getTickets();
        Long ultimoTicketId = ticketsDeMesa.isEmpty() ? null : ticketsDeMesa.get(ticketsDeMesa.size() - 1).getTicketId();
        boolean esUltimoTicket = ultimoTicketId != null && ultimoTicketId.equals(idTicket);

        // Eliminar el ticket
        session.delete(ticket);
        transaction.commit();

        // Verificar si la mesa asociada está activa y si este era su último ticket
        if (mesa.getActiva()&& esUltimoTicket) {
            transaction = session.beginTransaction();
            mesa.setActiva(false);
            session.update(mesa);
            transaction.commit();
        }

        JOptionPane.showMessageDialog(ventana, "Ticket eliminado correctamente.");
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        JOptionPane.showMessageDialog(ventana, "Error al eliminar ticket: " + e.getMessage());
    } finally {
        actualizarDatos();
    }
}





    public static boolean insertarTicket(Mesa mesa) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (mesa == null) {
                return false;
            }

            // Verificar si la mesa existe en la base de datos
            Query<Mesa> query = session.createQuery("from Mesa where mesaId = :mesaId", Mesa.class);
            query.setParameter("mesaId", mesa.getMesaId());
            Mesa mesaExistente = query.uniqueResult();
            if (mesaExistente == null) {
                return false;
            }

            Ticket ticket = new Ticket();
            ticket.setMesa(mesaExistente);

            // Establecer la fecha y hora actuales
            Date fechaHora = new Date();
            ticket.setFechaHora(fechaHora);

            // Verificar si el ID del ticket es opcional
            String idTicketStr = ventana.getTxtIDTicket().getText().trim();
            if (!idTicketStr.isEmpty()) {
                Long idTicket = Long.parseLong(idTicketStr);
                ticket.setTicketId(idTicket);
            }

            session.save(ticket);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            actualizarDatos();
        }
    }

}
