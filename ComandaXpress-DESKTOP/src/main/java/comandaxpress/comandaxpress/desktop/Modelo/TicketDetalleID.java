package comandaxpress.comandaxpress.desktop.Modelo;
import javax.persistence.*;
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