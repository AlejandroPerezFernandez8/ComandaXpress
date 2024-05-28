package TableModels;

import comandaxpress.comandaxpress.desktop.Modelo.Categoria;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CategoriaTableModel extends AbstractTableModel {
    private final List<Categoria> categorias;
    private final String[] columnNames = {"ID", "Nombre", "Descripción"};

    public CategoriaTableModel(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public int getRowCount() {
        return categorias.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria categoria = categorias.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return categoria.getCategoriaId();
            case 1:
                return categoria.getNombre();
            case 2:
                return categoria.getDescripcion();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addCategoria(Categoria categoria) {
        categorias.add(categoria);
        fireTableRowsInserted(categorias.size() - 1, categorias.size() - 1);
    }

    public void removeCategoria(int rowIndex) {
        categorias.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}
