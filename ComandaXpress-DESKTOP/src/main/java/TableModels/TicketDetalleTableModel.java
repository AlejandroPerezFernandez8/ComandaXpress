package TableModels;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import comandaxpress.comandaxpress.desktop.Modelo.TicketDetalle;

public class TicketDetalleTableModel extends AbstractTableModel {
    private final List<TicketDetalle> ticketDetalles;
    private final String[] columnNames = new String[] {"Ticket ID", "Producto","Cantidad"};

    public TicketDetalleTableModel(List<TicketDetalle> ticketDetalles) {
        this.ticketDetalles = ticketDetalles;
    }

    @Override
    public int getRowCount() {
        return ticketDetalles.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TicketDetalle ticketDetalle = ticketDetalles.get(rowIndex);
        switch (columnIndex) {
            case 0: return ticketDetalle.getTicketId();
            case 1: return ticketDetalle.getProducto();
            case 2: return ticketDetalle.getCantidad();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addTicketDetalle(TicketDetalle ticketDetalle) {
        ticketDetalles.add(ticketDetalle);
        fireTableRowsInserted(ticketDetalles.size() - 1, ticketDetalles.size() - 1);
    }

    public void removeTicketDetalle(int rowIndex) {
        ticketDetalles.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}

