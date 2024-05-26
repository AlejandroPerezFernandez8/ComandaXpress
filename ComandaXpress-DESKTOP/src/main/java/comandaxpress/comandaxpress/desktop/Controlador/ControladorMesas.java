package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.MesaTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Mesa;
import comandaxpress.comandaxpress.desktop.Vista.PantallaMesas;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

public class ControladorMesas {

    private static final PantallaMesas ventana = new PantallaMesas();
    private static DefaultComboBoxModel<Mesa> modeloCombo = new DefaultComboBoxModel<>();
    private static MesaTableModel modeloTabla;

    static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        actualizarDatos();
    }

    public static List<Mesa> getListaMesas() {
        List<Mesa> mesas = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Mesa> query = session.createQuery("from Mesa", Mesa.class);
            mesas = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return mesas;
    }

    public static void insertarMesa() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Integer numero = Integer.parseInt(ventana.getTxtNumeroMesa().getText().trim());
            Integer capacidad = Integer.parseInt(ventana.getTxtNumeroComensales().getText().trim());

            if (numero == null || capacidad == null) {
                JOptionPane.showMessageDialog(ventana, "Completa los campos numero y capacidad");
                return;
            }

            Mesa mesa = new Mesa();
            mesa.setNumero(numero);
            mesa.setCapacidad(capacidad);

            session.save(mesa);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Mesa insertada correctamente.");
        } catch (ConstraintViolationException e) {
            JOptionPane.showMessageDialog(ventana, "Error: La mesa ya existe");
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(ventana, "Error al insertar mesa: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            actualizarDatos();
        }
    }

    public static void modificarMesa() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Long mesaId = Long.parseLong(ventana.getTxtIDMesa().getText().trim());
            Integer numero = Integer.parseInt(ventana.getTxtNumeroMesa().getText().trim());
            Integer capacidad = Integer.parseInt(ventana.getTxtNumeroComensales().getText().trim());

            Mesa mesa = session.get(Mesa.class, mesaId);
            if (mesa == null) {
                JOptionPane.showMessageDialog(ventana, "Mesa no encontrada.");
                return;
            }

            mesa.setNumero(numero);
            mesa.setCapacidad(capacidad);

            session.update(mesa);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Mesa modificada correctamente.");
        } catch (ConstraintViolationException e) {
            JOptionPane.showMessageDialog(ventana, "Error: La mesa ya existe");
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                JOptionPane.showMessageDialog(ventana, "Error: La mesa ya existe");
            } else {
                JOptionPane.showMessageDialog(ventana, "Error al modificar la mesa: " + e.getMessage());
            }
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            actualizarDatos();
        }
    }

    public static void eliminarMesa() {
        Long mesaId = Long.parseLong(ventana.getTxtIDMesa().getText().trim());
        int confirm = JOptionPane.showConfirmDialog(
                ventana,
                "¿Estás seguro de que deseas eliminar esta mesa y todos sus tickets asociados?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                Mesa mesa = session.get(Mesa.class, mesaId);
                session.delete(mesa);  // Elimina la mesa y sus tickets asociados debido al cascade
                transaction.commit();
                JOptionPane.showMessageDialog(ventana, "Mesa eliminada.");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                JOptionPane.showMessageDialog(ventana, "Error al eliminar mesa: " + e.getMessage());
            } finally {
                actualizarDatos();
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Eliminación cancelada.");
        }
    }

    private static void actualizarDatos() {
        modeloCombo.removeAllElements();
        modeloCombo.addAll(getListaMesas());
        ventana.getComboMesas().setModel(modeloCombo);

        modeloTabla = new MesaTableModel(getListaMesas());
        ventana.getTablaMesas().setModel(modeloTabla);
        modeloTabla.updateTable();

        if (ventana.getComboMesas().getModel().getSize() > 0) {
            ventana.getComboMesas().setSelectedIndex(0);
        }
    }

    public static void cambiarMesaTexto() {
        Mesa mesa = (Mesa) modeloCombo.getSelectedItem();
        if (mesa != null) {
            ventana.getTxtIDMesa().setText(mesa.getMesaId() + "");
            ventana.getTxtNumeroComensales().setText(mesa.getCapacidad() + "");
            ventana.getTxtNumeroMesa().setText(mesa.getNumero() + "");
        }
    }
}
