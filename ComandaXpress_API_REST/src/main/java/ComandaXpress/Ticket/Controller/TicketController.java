package ComandaXpress.Ticket.Controller;

import ComandaXpress.Api_Map;
import ComandaXpress.DTO.TicketDTO;
import ComandaXpress.Mesa.Repository.MesaRepository;
import ComandaXpress.Ticket.Model.Ticket;
import ComandaXpress.Ticket.Repository.TicketRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api_Map.TICKET_BASE_URL)
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MesaRepository mesaRepository;
    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream().map(TicketDTO::converter).collect(Collectors.toList());
    }
    @PostMapping
    public String guardarTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketDTO.getTicketId());
        ticket.setMesa(mesaRepository.findById(ticketDTO.getMesaId())
                .orElseThrow(()-> new RuntimeException("Mesa no existe!!")));

        ticketRepository.save(ticket);
        return "Ticket guardado con éxito!";
    }
    @PutMapping(Api_Map.TICKET_ID_URL)
    public String actualizarTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        Ticket ticketExistente = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado")); // Excepción
        ticketExistente.setMesa(ticket.getMesa());
        ticketExistente.setFechaHora(ticket.getFechaHora());
        ticketExistente.setTicketDetalles(ticket.getTicketDetalles());

        ticketRepository.save(ticketExistente);
        return "Ticket actualizado con éxito!";
    }
    @DeleteMapping(Api_Map.TICKET_ID_URL)
    public String eliminarTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado")); // Excepción
        ticketRepository.delete(ticket);
        return "Ticket eliminado con éxito!";
    }

    @GetMapping(Api_Map.TICKET_FILTROS_URL)
    public List<TicketDTO> filtrarTickets(@RequestParam(required = false) String fecha,
                                          @RequestParam(required = false) Integer numeroMesa) {
        Date fechaBusqueda = new Date();
        List<Ticket> tickets;
        if(fecha != null){
            fechaBusqueda = convertirFecha(fecha);
        }

        if (fecha != null && numeroMesa != null) {
            tickets = ticketRepository.findByFechaHoraAndMesa_Numero(fechaBusqueda, numeroMesa);
        } else if (fecha != null) {
            tickets = ticketRepository.findByFechaHora(fechaBusqueda);
        } else if (numeroMesa != null) {
            tickets = ticketRepository.findByMesa_Numero(numeroMesa);
        } else {
            tickets = ticketRepository.findAll();
        }

        return tickets.stream().map(TicketDTO::converter).collect(Collectors.toList());
    }


    public static Date convertirFecha(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
