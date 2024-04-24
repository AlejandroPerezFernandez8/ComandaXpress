package ComandaXpress.Ticket.Controller;

import ComandaXpress.DTO.TicketDTO;
import ComandaXpress.Ticket.Model.Ticket;
import ComandaXpress.Ticket.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream().map(TicketDTO::converter).collect(Collectors.toList());
    }
    @PostMapping
    public String guardarTicket(@RequestBody Ticket ticket) {
        ticketRepository.save(ticket);
        return "Ticket guardado con éxito!";
    }

    @PutMapping("/{ticketId}")
    public String actualizarTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        Ticket ticketExistente = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado")); // Excepción
        ticketExistente.setMesa(ticket.getMesa());
        ticketExistente.setFechaHora(ticket.getFechaHora());
        ticketExistente.setTicketDetalles(ticket.getTicketDetalles());

        ticketRepository.save(ticketExistente);
        return "Ticket actualizado con éxito!";
    }

    @DeleteMapping("/{ticketId}")
    public String eliminarTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado")); // Excepción
        ticketRepository.delete(ticket);
        return "Ticket eliminado con éxito!";
    }
}
