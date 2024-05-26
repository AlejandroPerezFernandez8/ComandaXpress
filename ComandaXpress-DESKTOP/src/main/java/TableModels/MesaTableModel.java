package TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import comandaxpress.comandaxpress.desktop.Modelo.Mesa;

public class MesaTableModel extends AbstractTableModel {
    private final List<Mesa> mesas;
    private final String[] columnNames = new String[] {"ID Mesa", "Número", "Capacidad", "Activa"};

    public MesaTableModel(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    @Override
    public int getRowCount() {
        return mesas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mesa mesa = mesas.get(rowIndex);
        switch (columnIndex) {
            case 0: return mesa.getMesaId();
            case 1: return mesa.getNumero();
            case 2: return mesa.getCapacidad();
            case 3: return mesa.getActiva() ? "Sí" : "No"; // Manejo booleano para mostrar como "Sí" o "No"
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addMesa(Mesa mesa) {
        mesas.add(mesa);
        fireTableRowsInserted(mesas.size() - 1, mesas.size() - 1);
    }

    public void removeMesa(int rowIndex) {
        mesas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}
