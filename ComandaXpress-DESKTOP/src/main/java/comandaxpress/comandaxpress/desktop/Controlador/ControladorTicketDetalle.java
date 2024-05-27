package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.TicketDetalleTableModel;
import TableModels.TicketTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Producto;
import comandaxpress.comandaxpress.desktop.Modelo.Ticket;
import comandaxpress.comandaxpress.desktop.Modelo.TicketDetalle;
import comandaxpress.comandaxpress.desktop.Vista.PantallaTicketDetalle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import org.hibernate.query.Query;

public class ControladorTicketDetalle {

    private static final PantallaTicketDetalle ventana = new PantallaTicketDetalle();
    private static TicketDetalleTableModel modeloTabla;
    private static DefaultComboBoxModel<Ticket> modeloComboTicket = new DefaultComboBoxModel<>();
    private static DefaultComboBoxModel<Producto> modeloComboProductos = new DefaultComboBoxModel<>();
    private static SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(0, 0, 99999, 1);

    public static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.getSpinnerCantidad().setModel(modeloSpinner);
        actualizarDatos();
        if (ventana.getComboTickets().getModel().getSize() > 0) {
            ventana.getComboTickets().setSelectedIndex(0);
        }
        if (ventana.getComboProductos().getModel().getSize() > 0) {
            ventana.getComboProductos().setSelectedIndex(0);
        }
    }

    public static List<TicketDetalle> getListaTicketDetalles() {
        List<TicketDetalle> ticketDetalles = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<TicketDetalle> query = session.createQuery("from TicketDetalle", TicketDetalle.class);
            ticketDetalles = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Error");
        }
        return ticketDetalles;
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

    public static List<Producto> getListaProductos() {
        List<Producto> productos = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Producto> query = session.createQuery("from Producto", Producto.class);
            productos = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Error");
        }
        return productos;
    }

    public static void actualizarDatos() {
        try {
            int productoSeleccted = ventana.getComboProductos().getSelectedIndex();
            int TicketSelected = ventana.getComboTickets().getSelectedIndex();
            modeloComboProductos.removeAllElements();
            modeloComboTicket.removeAllElements();
            modeloComboProductos.addAll(getListaProductos());
            modeloComboTicket.addAll(getListaTickets());
            modeloTabla = new TicketDetalleTableModel(getListaTicketDetalles());
            ventana.getComboProductos().setModel(modeloComboProductos);
            ventana.getComboTickets().setModel(modeloComboTicket);
            ventana.getSpinnerCantidad().setModel(modeloSpinner);
            ventana.getTablaTicketDetalle().setModel(modeloTabla);
            
            if (ventana.getComboTickets().getModel().getSize() > 0) {
            ventana.getComboTickets().setSelectedIndex(TicketSelected);
            }
            if (ventana.getComboProductos().getModel().getSize() > 0) {
                ventana.getComboProductos().setSelectedIndex(productoSeleccted);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertarTicketDetalle() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Ticket ticket = (Ticket) ventana.getComboTickets().getSelectedItem();
            if (ticket == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un ticket.");
                return;
            }

            Producto producto = (Producto) ventana.getComboProductos().getSelectedItem();
            if (producto == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un producto.");
                return;
            }

            Integer cantidad = (Integer) ventana.getSpinnerCantidad().getValue();
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(ventana, "La cantidad debe ser mayor a cero.");
                return;
            }

            Query<TicketDetalle> query = session.createQuery("from TicketDetalle where ticketId = :ticketId and productoId = :productoId", TicketDetalle.class);
            query.setParameter("ticketId", ticket.getTicketId());
            query.setParameter("productoId", producto.getProductoId());
            TicketDetalle existingDetalle = query.uniqueResult();
            if (existingDetalle != null) {
                JOptionPane.showMessageDialog(ventana, "El detalle del ticket ya existe.");
                return;
            }

            TicketDetalle ticketDetalle = new TicketDetalle();
            ticketDetalle.setTicketId(ticket.getTicketId());
            ticketDetalle.setProductoId(producto.getProductoId());
            ticketDetalle.setTicket(ticket);
            ticketDetalle.setProducto(producto);
            ticketDetalle.setCantidad(cantidad);

            session.save(ticketDetalle);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Detalle del ticket insertado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al insertar el detalle del ticket: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

    public static void eliminarTicketDetalle() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Ticket ticket = (Ticket) ventana.getComboTickets().getSelectedItem();
            if (ticket == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un ticket.");
                return;
            }

            Producto producto = (Producto) ventana.getComboProductos().getSelectedItem();
            if (producto == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un producto.");
                return;
            }

            Query<TicketDetalle> query = session.createQuery("from TicketDetalle where ticketId = :ticketId and productoId = :productoId", TicketDetalle.class);
            query.setParameter("ticketId", ticket.getTicketId());
            query.setParameter("productoId", producto.getProductoId());
            TicketDetalle existingDetalle = query.uniqueResult();
            if (existingDetalle == null) {
                JOptionPane.showMessageDialog(ventana, "El detalle del ticket no existe.");
                return;
            }

            session.delete(existingDetalle);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Detalle del ticket eliminado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al eliminar el detalle del ticket: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

    public static void modificarCantidadTicketDetalle() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Ticket ticket = (Ticket) ventana.getComboTickets().getSelectedItem();
            if (ticket == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un ticket.");
                return;
            }

            Producto producto = (Producto) ventana.getComboProductos().getSelectedItem();
            if (producto == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un producto.");
                return;
            }

            Integer cantidad = (Integer) ventana.getSpinnerCantidad().getValue();
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(ventana, "La cantidad debe ser mayor a cero.");
                return;
            }

            Query<TicketDetalle> query = session.createQuery("from TicketDetalle where ticketId = :ticketId and productoId = :productoId", TicketDetalle.class);
            query.setParameter("ticketId", ticket.getTicketId());
            query.setParameter("productoId", producto.getProductoId());
            TicketDetalle existingDetalle = query.uniqueResult();
            if (existingDetalle == null) {
                JOptionPane.showMessageDialog(ventana, "El detalle del ticket no existe.");
                return;
            }

            existingDetalle.setCantidad(cantidad);
            session.update(existingDetalle);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Cantidad del detalle del ticket modificada correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al modificar la cantidad del detalle del ticket: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

}
