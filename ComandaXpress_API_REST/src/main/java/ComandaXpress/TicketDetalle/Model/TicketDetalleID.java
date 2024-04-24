package ComandaXpress.TicketDetalle.Model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TicketDetalleID implements Serializable {
    private Long ticket;
    private Long producto;
}