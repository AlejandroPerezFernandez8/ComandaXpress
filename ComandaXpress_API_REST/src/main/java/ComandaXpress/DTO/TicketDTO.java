package ComandaXpress.DTO;

import ComandaXpress.Ticket.Model.Ticket;
import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import lombok.Data;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class TicketDTO {
    private Long ticketId;
    private Long mesaId;
    private Date fechaHora;
    private List<Long> ticketDetallesID;

    public static TicketDTO converter(Ticket source){
        TicketDTO target = new TicketDTO();
        target.setTicketId(source.getTicketId());
        target.setMesaId(source.getMesa().getMesaId());
        target.setFechaHora(source.getFechaHora());

        // Usar un Set para evitar duplicados
        Set<Long> uniqueTicketDetalleIds = source.getTicketDetalles().stream()
                .map(TicketDetalle::getTicket)
                .map(Ticket::getTicketId)
                .collect(Collectors.toSet());

        // Convertir el Set a List antes de asignarlo
        target.setTicketDetallesID(new ArrayList<>(uniqueTicketDetalleIds));

        return target;
    }
}
