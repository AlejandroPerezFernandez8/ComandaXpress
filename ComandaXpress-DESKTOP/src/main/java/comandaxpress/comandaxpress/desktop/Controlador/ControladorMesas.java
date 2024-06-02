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
            
            if(ventana.getTxtIDMesa().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana,"Debes escribir un ID de mesa o seleccionar una en el combo");
                return;
            }
            
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
        if(ventana.getTxtIDMesa().getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(ventana, "Debes escribir un id de mesa o seleccionar una del combo");
            return;
        }
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
                if(mesa == null){JOptionPane.showMessageDialog(ventana, "La mesa no existe");return;}
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

    public static void actualizarDatos() {
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

   public static void activarMesaYCrearTicket() {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        String idMesaStr = ventana.getTxtIDMesa().getText().trim();
        if (idMesaStr.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debe proporcionar el ID de la mesa.");
            return;
        }

        Long idMesa = Long.parseLong(idMesaStr);
        Mesa mesa = session.get(Mesa.class, idMesa);
        if (mesa == null) {
            JOptionPane.showMessageDialog(ventana, "Mesa no encontrada.");
            return;
        }

        if (mesa.getActiva()) {
            int confirmInactivar = JOptionPane.showConfirmDialog(
                    ventana,
                    "La mesa ya está activa. ¿Desea desactivarla?",
                    "Confirmar desactivación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmInactivar == JOptionPane.YES_OPTION) {
                mesa.setActiva(false);
                session.update(mesa);
                transaction.commit();
                JOptionPane.showMessageDialog(ventana, "Mesa desactivada.");
            } else {
                JOptionPane.showMessageDialog(ventana, "Desactivación cancelada.");
            }
        } else {
            int confirmActivar = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Está seguro de que desea activar esta mesa y crear un nuevo ticket para ella?",
                    "Confirmar activación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmActivar == JOptionPane.YES_OPTION) {
                
                mesa.setActiva(true);
                session.update(mesa);
                transaction.commit();
                session.close(); 

                
                boolean ticketCreado = ControladorTickets.insertarTicket(mesa);
                if (ticketCreado) {
                    JOptionPane.showMessageDialog(ventana, "Mesa activada y ticket creado correctamente.");
                } else {
                   
                    try (Session revertSession = HibernateUtil.getSessionFactory().openSession()) {
                        Transaction revertTransaction = revertSession.beginTransaction();
                        mesa.setActiva(false);
                        revertSession.update(mesa);
                        revertTransaction.commit();
                        JOptionPane.showMessageDialog(ventana, "Error al crear el ticket, mesa revertida a inactiva.");
                    } catch (Exception revertException) {
                        JOptionPane.showMessageDialog(ventana, "Error al revertir la activación de la mesa: " + revertException.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(ventana, "Activación cancelada.");
            }
        }
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        JOptionPane.showMessageDialog(ventana, "Error al activar la mesa: " + e.getMessage());
    } finally {
        actualizarDatos();
    }
}




}
