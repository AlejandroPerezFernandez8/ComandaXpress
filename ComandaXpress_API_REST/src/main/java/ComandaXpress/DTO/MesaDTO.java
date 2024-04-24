package ComandaXpress.DTO;

import ComandaXpress.Mesa.Model.Mesa;
import ComandaXpress.Ticket.Model.Ticket;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MesaDTO {
    private Long mesaId;
    private Integer numero;
    private Integer capacidad;
    private Boolean activa;
    private List<Long> Tickets;

    public static MesaDTO converter(Mesa source){
        MesaDTO target = new MesaDTO();
        target.setMesaId(source.getMesaId());
        target.setNumero(source.getNumero());
        target.setCapacidad(source.getCapacidad());
        target.setActiva(source.getActiva());
        target.setTickets(source.getTickets().stream().map(Ticket::getTicketId).collect(Collectors.toList()));
        return target;
    }
}
