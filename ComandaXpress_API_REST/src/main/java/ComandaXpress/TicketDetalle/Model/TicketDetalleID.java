package ComandaXpress.TicketDetalle.Model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetalleID implements Serializable {
    private Long ticketId;
    private Long productoId;
}