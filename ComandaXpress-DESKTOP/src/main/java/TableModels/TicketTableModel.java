package TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import comandaxpress.comandaxpress.desktop.Modelo.Ticket;
import java.text.SimpleDateFormat;

public class TicketTableModel extends AbstractTableModel {
    private final List<Ticket> tickets;
    private final String[] columnNames = new String[] {"ID Ticket", "Numero de Mesa", "Fecha y Hora"};

    public TicketTableModel(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ticket ticket = tickets.get(rowIndex);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        switch (columnIndex) {
            case 0: return ticket.getTicketId();
            case 1: return ticket.getMesa().getNumero();
            case 2: return dateFormat.format(ticket.getFechaHora());
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        fireTableRowsInserted(tickets.size() - 1, tickets.size() - 1);
    }

    public void removeTicket(int rowIndex) {
        tickets.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}

