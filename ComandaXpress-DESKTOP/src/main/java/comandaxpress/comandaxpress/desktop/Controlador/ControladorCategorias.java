package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.CategoriaTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Categoria;
import comandaxpress.comandaxpress.desktop.Vista.PantallaCategoria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.util.List;
import org.hibernate.query.Query;

public class ControladorCategorias {

    private static final PantallaCategoria ventana = new PantallaCategoria();
    private static CategoriaTableModel modeloTabla;
    private static DefaultComboBoxModel<Categoria> modeloCombo = new DefaultComboBoxModel<>();

    public static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        actualizarDatos();
        if (ventana.getComboCategorias().getModel().getSize() > 0) {
            ventana.getComboCategorias().setSelectedIndex(0);
        }
    }

    public static List<Categoria> getListaCategorias() {
        List<Categoria> categorias = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Categoria> query = session.createQuery("from Categoria", Categoria.class);
            categorias = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Error");
        }
        return categorias;
    }

    public static void actualizarDatos() {
        try {
            int categoriaSeleccionada = ventana.getComboCategorias().getSelectedIndex();
            modeloCombo.removeAllElements();
            modeloCombo.addAll(getListaCategorias());
            modeloTabla = new CategoriaTableModel(getListaCategorias());
            ventana.getComboCategorias().setModel(modeloCombo);
            ventana.getTablaCategorias().setModel(modeloTabla);

            if (ventana.getComboCategorias().getModel().getSize() > 0) {
                ventana.getComboCategorias().setSelectedIndex(categoriaSeleccionada);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertarCategoria() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String nombre = ventana.getTxtNombre().getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El nombre de la categoría es obligatorio.");
                return;
            }

            String descripcion = ventana.getTxtDescripcion().getText().trim();

            Query<Categoria> query = session.createQuery("from Categoria where nombre = :nombre", Categoria.class);
            query.setParameter("nombre", nombre);
            Categoria existingCategoria = query.uniqueResult();

            if (existingCategoria != null) {
                JOptionPane.showMessageDialog(ventana, "La categoría ya existe.");
                return;
            }

            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);

            session.save(categoria);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Categoría insertada correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al insertar la categoría: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

    public static void eliminarCategoria() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Categoria categoria = (Categoria) ventana.getComboCategorias().getSelectedItem();
            if (categoria == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar una categoría.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Está seguro de que desea eliminar esta categoría y todos sus productos asociados?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                session.delete(categoria);
                transaction.commit();
                JOptionPane.showMessageDialog(ventana, "Categoría y productos asociados eliminados correctamente.");
            } else {
                JOptionPane.showMessageDialog(ventana, "Eliminación cancelada.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al eliminar la categoría: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

    public static void modificarCategoria() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Categoria categoria = (Categoria) ventana.getComboCategorias().getSelectedItem();
            if (categoria == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar una categoría.");
                return;
            }

            String nuevoNombre = ventana.getTxtNombre().getText().trim();
            if (nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El nombre de la categoría es obligatorio.");
                return;
            }

            String nuevaDescripcion = ventana.getTxtDescripcion().getText().trim();

            categoria.setNombre(nuevoNombre);
            categoria.setDescripcion(nuevaDescripcion);

            session.update(categoria);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Categoría modificada correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al modificar la categoría: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }
    
    public static void actualizarCamposCategoria() {
    Categoria categoria = (Categoria) ventana.getComboCategorias().getSelectedItem();
    if (categoria != null) {
        ventana.getTxtNombre().setText(categoria.getNombre());
        ventana.getTxtDescripcion().setText(categoria.getDescripcion());
    } else {
        ventana.getTxtNombre().setText("");
        ventana.getTxtDescripcion().setText("");
    }
}

    
    
}
