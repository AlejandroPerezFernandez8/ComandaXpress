package TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import comandaxpress.comandaxpress.desktop.Modelo.Usuario;

public class UsuarioTableModel extends AbstractTableModel {
    private final List<Usuario> usuarios;
    private final String[] columnNames = new String[] {"ID", "Nombre", "Apellido", "Email", "Usuario", "Contraseña"};

    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0: return usuario.getUsuario_id();
            case 1: return usuario.getNombre();
            case 2: return usuario.getApellido();
            case 3: return usuario.getEmail();
            case 4: return usuario.getUsuario();
            case 5: return usuario.getContraseña(); // Asumiendo que existe un método getContraseña() en la clase Usuario
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        fireTableRowsInserted(usuarios.size() - 1, usuarios.size() - 1);
    }

    public void removeUsuario(int rowIndex) {
        usuarios.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}
