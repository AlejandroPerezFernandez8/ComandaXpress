package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.ProductoTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Producto;
import comandaxpress.comandaxpress.desktop.Modelo.Categoria;
import comandaxpress.comandaxpress.desktop.Modelo.TicketDetalle;
import comandaxpress.comandaxpress.desktop.Vista.PantallaProductos;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.util.List;

public class ControladorProductos {

    private static final PantallaProductos ventana = new PantallaProductos();
    private static ProductoTableModel modeloTabla;
    private static DefaultComboBoxModel<Producto> modeloComboProducto = new DefaultComboBoxModel<>();
    private static DefaultComboBoxModel<Categoria> modeloComboCategoria = new DefaultComboBoxModel<>();

    public static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        actualizarDatos();
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
            int productoSelected = ventana.getComboProductos().getSelectedIndex();
            int categoriaSelected = ventana.getComboCategorias().getSelectedIndex();
            modeloComboProducto.removeAllElements();
            modeloComboCategoria.removeAllElements();
            modeloComboProducto.addAll(getListaProductos());
            modeloComboCategoria.addAll(getListaCategorias());
            modeloTabla = new ProductoTableModel(getListaProductos());
            ventana.getComboProductos().setModel(modeloComboProducto);
            ventana.getComboCategorias().setModel(modeloComboCategoria);
            ventana.getTablaProductos().setModel(modeloTabla);
            modeloTabla.updateTable();

            if (ventana.getComboProductos().getModel().getSize() > 0) {
                ventana.getComboProductos().setSelectedIndex(productoSelected);
            }
            if (ventana.getComboCategorias().getModel().getSize() > 0) {
                ventana.getComboCategorias().setSelectedIndex(categoriaSelected);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertarProducto() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String nombre = ventana.getTxtNombre().getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El nombre del producto es obligatorio.");
                return;
            }

            Categoria categoria = (Categoria) ventana.getComboCategorias().getSelectedItem();
            if (categoria == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar una categoría.");
                return;
            }

            BigDecimal precio = new BigDecimal(ventana.getSpinnerPrecio().getValue().toString());
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(ventana, "El precio debe ser mayor a cero.");
                return;
            }

            Query<Producto> query = session.createQuery("from Producto where nombre = :nombre", Producto.class);
            query.setParameter("nombre", nombre);
            Producto existingProducto = query.uniqueResult();

            if (existingProducto != null) {
                JOptionPane.showMessageDialog(ventana, "El producto ya existe.");
                return;
            }

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);

            session.save(producto);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Producto insertado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al insertar el producto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

    public static void eliminarProducto() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Producto producto = (Producto) ventana.getComboProductos().getSelectedItem();
            if (producto == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un producto.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Está seguro de que desea eliminar este producto y todos sus detalles de ticket asociados?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminar el Producto (los detalles se eliminarán en cascada)
                session.delete(producto);
                transaction.commit();
                JOptionPane.showMessageDialog(ventana, "Producto y detalles asociados eliminados correctamente.");
            } else {
                JOptionPane.showMessageDialog(ventana, "Eliminación cancelada.");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al eliminar el producto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            actualizarDatos();
        }
    }

    public static void modificarProducto() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Producto existingProducto = (Producto) ventana.getComboProductos().getSelectedItem();
            if (existingProducto == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar un producto.");
                return;
            }

            String nombre = ventana.getTxtNombre().getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El nombre del producto es obligatorio.");
                return;
            }

            Categoria nuevaCategoria = (Categoria) ventana.getComboCategorias().getSelectedItem();
            if (nuevaCategoria == null) {
                JOptionPane.showMessageDialog(ventana, "Debe seleccionar una categoría.");
                return;
            }

            BigDecimal nuevoPrecio = new BigDecimal(ventana.getSpinnerPrecio().getValue().toString());
            if (nuevoPrecio.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(ventana, "El precio debe ser mayor a cero.");
                return;
            }

            existingProducto.setNombre(nombre);
            existingProducto.setCategoria(nuevaCategoria);
            existingProducto.setPrecio(nuevoPrecio);

            session.update(existingProducto);
            transaction.commit();
            JOptionPane.showMessageDialog(ventana, "Producto modificado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana, "Error al modificar el producto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            actualizarDatos();
        }
    }

    public static void actualizarCamposProducto() {
        try {
            Producto producto = (Producto) ventana.getComboProductos().getSelectedItem();
            if (producto != null) {
                ventana.getTxtNombre().setText(producto.getNombre());
                ventana.getSpinnerPrecio().setValue(producto.getPrecio());
                // Actualizar el combo de categorías
                Categoria categoria = producto.getCategoria();
                DefaultComboBoxModel<Categoria> modeloCategoria = (DefaultComboBoxModel<Categoria>) ventana.getComboCategorias().getModel();
                modeloCategoria.setSelectedItem(categoria);
            }
        } catch (Exception e) {
        }
    }

}
