package ComandaXpress.DTO;

import ComandaXpress.Ticket.Model.Ticket;
import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import lombok.Data;


import java.util.Date;
import java.util.List;
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
        target.setTicketDetallesID(source.getTicketDetalles().stream().map(
                TicketDetalle::getTicket).collect(Collectors.toList()).stream().map(Ticket::getTicketId).collect(Collectors.toList())
        );
        return target;
    }
}
