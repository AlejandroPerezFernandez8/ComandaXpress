package ComandaXpress.TicketDetalle.Controller;


import ComandaXpress.Api_Map;
import ComandaXpress.DTO.TicketDetalleDTO;
import ComandaXpress.TicketDetalle.Repository.TicketDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(Api_Map.TICKET_DETALLE_BASE_URL)
public class TicketDetalleController {
    @Autowired
    TicketDetalleRepository ticketDetalleRepository;
    @GetMapping
    public List<TicketDetalleDTO> getAllDetalles(){
        return TicketDetalleDTO.agruparDetallesLista(ticketDetalleRepository.findAll());
    }
    @GetMapping(Api_Map.TICKET_DETALLE_ID_URL)
    public  List<TicketDetalleDTO> getOneTicketDetalle(@PathVariable Long idTicket){
        return TicketDetalleDTO.agruparDetallesLista(ticketDetalleRepository.findByTicket_ticketId(idTicket));
    }

    @PostMapping
    public ResponseEntity<String> guardarTicketDetalle(@RequestBody TicketDetalleDTO ticketDetalleDTO){
        ticketDetalleRepository.saveAll(TicketDetalleDTO.convertDTOToTicketDetalles(ticketDetalleDTO));
        return ResponseEntity.ok("Detalles Guardados!!!");
    }
}
