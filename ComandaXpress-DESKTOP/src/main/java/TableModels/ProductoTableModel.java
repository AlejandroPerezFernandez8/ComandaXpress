package TableModels;

import comandaxpress.comandaxpress.desktop.Modelo.Producto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProductoTableModel extends AbstractTableModel {
    private final List<Producto> productos;
    private final String[] columnNames = new String[] {"ID", "Nombre", "Categoría", "Precio"};

    public ProductoTableModel(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto producto = productos.get(rowIndex);
        switch (columnIndex) {
            case 0: return producto.getProductoId();
            case 1: return producto.getNombre();
            case 2: return producto.getCategoria().getNombre();
            case 3: return producto.getPrecio();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addProducto(Producto producto) {
        productos.add(producto);
        fireTableRowsInserted(productos.size() - 1, productos.size() - 1);
    }

    public void removeProducto(int rowIndex) {
        productos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}
