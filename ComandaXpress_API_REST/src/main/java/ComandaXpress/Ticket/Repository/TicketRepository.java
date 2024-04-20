package ComandaXpress.Ticket.Repository;

import ComandaXpress.Ticket.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
