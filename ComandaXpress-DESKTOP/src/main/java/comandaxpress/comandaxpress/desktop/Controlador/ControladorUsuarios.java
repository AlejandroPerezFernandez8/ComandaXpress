package comandaxpress.comandaxpress.desktop.Controlador;

import TableModels.UsuarioTableModel;
import comandaxpress.comandaxpress.desktop.HibernateUtils.HibernateUtil;
import comandaxpress.comandaxpress.desktop.Modelo.Usuario;
import comandaxpress.comandaxpress.desktop.Vista.PantallaUsuarios;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

public class ControladorUsuarios {
    private static final PantallaUsuarios ventana = new PantallaUsuarios();
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel<>();
    private static UsuarioTableModel modeloTabla;
    static void iniciar() {
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        actualizarDatos();
    }
   public static List<Usuario> getListaUsuarios() {
        List<Usuario> usuarios = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Usuario> query = session.createQuery("from Usuario", Usuario.class);
            usuarios = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {transaction.rollback();}
            e.printStackTrace();
        }
        return usuarios;
    }
   public static void EliminarUsuario() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Usuario usuario = (Usuario) modelo.getSelectedItem();
            session.delete(usuario);
            transaction.commit();
            new JOptionPane().showMessageDialog(ventana, "Usuario Eliminado");
        } catch (ConstraintViolationException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana,"Error, el usuario ya existe");
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            // Manejo de errores de Hibernate general
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana,"Error inesperado");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(ventana,"Error inesperado");
            System.out.println(e.getMessage());
        } finally {
            actualizarDatos();
        }
    }
   
     
public static void InsertarUsuario() {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        String id = ventana.getTxtIDusuario().getText().trim();
        String nombre = ventana.getTxtNombre().getText().trim();
        String apellidos = ventana.getTxtApellidos().getText().trim();
        String email = ventana.getTxtEmail().getText().trim();
        String nombreUsuario = ventana.getTxtNombreUsuario().getText().trim();
        String contraseña = ventana.getTxtContraseña().getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || nombreUsuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debes cubrir todos los campos.");
            return;
        }
        transaction = session.beginTransaction();
        Usuario usuario = new Usuario(nombre, apellidos, email, nombreUsuario, contraseña);
        if (!id.isEmpty()) {
            usuario.setUsuario_id(Long.parseLong(id));
        }
        
        session.save(usuario);
        transaction.commit();
        JOptionPane.showMessageDialog(ventana, "Usuario insertado correctamente.");
    } catch (ConstraintViolationException e) {
        JOptionPane.showMessageDialog(ventana, "Error: El usuario ya existe en la base de datos.");
        if (transaction != null) {
            transaction.rollback();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(ventana, "Error al insertar usuario: " + e.getMessage());
        if (transaction != null) {
            transaction.rollback();
        }
    } finally {
        actualizarDatos();
    }
}
     
     public static void ModificarUsuario() {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            
            String id = ventana.getTxtIDusuario().getText().trim();
            String nombre = ventana.getTxtNombre().getText().trim();
            String apellidos = ventana.getTxtApellidos().getText().trim();
            String email = ventana.getTxtEmail().getText().trim();
            String nombreUsuario = ventana.getTxtNombreUsuario().getText().trim();
            String contraseña = ventana.getTxtContraseña().getText().trim();
            
            Usuario usuario = session.get(Usuario.class, Long.parseLong(id));
            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
                return;
            }
            usuario.setNombre(nombre);
            usuario.setApellido(apellidos);
            usuario.setEmail(email);
            usuario.setUsuario(nombreUsuario);
            usuario.setContraseña(contraseña);
            
            session.update(usuario);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Usuario modificado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el usuario: " + e.getMessage());
             if (transaction != null) {
                transaction.rollback();
            }
        } finally {
          actualizarDatos();
        }
    } 
     
     
     
     
   
   private static void actualizarDatos(){
        modelo.removeAllElements();
        modelo.addAll(getListaUsuarios());
        ventana.getComboUsuarios().setModel(modelo);
        
        modeloTabla = new UsuarioTableModel(getListaUsuarios());
        ventana.getTablaUsuarios().setModel(modeloTabla);
        modeloTabla.updateTable();
        
        if(ventana.getComboUsuarios().getModel().getSize() > 0){
            ventana.getComboUsuarios().setSelectedIndex(0);
        }
   }
   public static void cambiarUsuarioTexto() {
        Usuario usuario = (Usuario)modelo.getSelectedItem();
        if(usuario != null){
            ventana.getTxtIDusuario().setText(usuario.getUsuario_id()+"");
            ventana.getTxtNombre().setText(usuario.getNombre());
            ventana.getTxtApellidos().setText(usuario.getApellido());
            ventana.getTxtEmail().setText(usuario.getApellido());
            ventana.getTxtNombreUsuario().setText(usuario.getUsuario());
            ventana.getTxtContraseña().setText(usuario.getContraseña());
        }
    }   
}
