package ComandaXpress.TicketDetalle.Controller;


import ComandaXpress.Api_Map;
import ComandaXpress.DTO.TicketDetalleDTO;
import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import ComandaXpress.TicketDetalle.Model.TicketDetalleID;
import ComandaXpress.TicketDetalle.Repository.TicketDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api_Map.TICKET_DETALLE_BASE_URL)
public class TicketDetalleController {
    @Autowired
    TicketDetalleRepository ticketDetalleRepository;

    @GetMapping
    public List<TicketDetalleDTO> getAllDetalles(){
        return TicketDetalleDTO.agruparDetallesLista(ticketDetalleRepository.findAll());
    }
}
