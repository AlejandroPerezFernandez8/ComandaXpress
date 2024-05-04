package ComandaXpress.TicketDetalle.Model;

import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class TicketDetalleID implements Serializable {
    private Long ticketId;
    private Long productoId;
}