package ComandaXpress.TicketDetalle.Repository;

import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import ComandaXpress.TicketDetalle.Model.TicketDetalleID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketDetalleRepository extends JpaRepository<TicketDetalle, TicketDetalle> {
}
