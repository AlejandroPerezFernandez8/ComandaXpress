package ComandaXpress.TicketDetalle.Model;

import lombok.Data;

@Data
public class TicketDetalleSimplificadoDTO {
    private long idTicket;
    private long idProducto;
    private int cantidad;
}