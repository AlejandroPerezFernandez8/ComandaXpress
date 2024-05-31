package ComandaXpress.TicketDetalle.Controller;

import ComandaXpress.Api_Map;
import ComandaXpress.DTO.TicketDetalleDTO;
import ComandaXpress.Producto.Model.Producto;
import ComandaXpress.Producto.Repository.ProductoRepository;
import ComandaXpress.Ticket.Model.Ticket;
import ComandaXpress.Ticket.Repository.TicketRepository;
import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import ComandaXpress.TicketDetalle.Model.TicketDetalleID;
import ComandaXpress.TicketDetalle.Model.TicketDetalleSimplificadoDTO;
import ComandaXpress.TicketDetalle.Repository.TicketDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Api_Map.TICKET_DETALLE_BASE_URL)
public class TicketDetalleController {
    @Autowired
    TicketDetalleRepository ticketDetalleRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ProductoRepository productoRepository;

    @GetMapping
    public List<TicketDetalleDTO> getAllDetalles() {
        return TicketDetalleDTO.agruparDetallesLista(ticketDetalleRepository.findAll());
    }

    @GetMapping(Api_Map.TICKET_DETALLE_ID_URL)
    public List<TicketDetalleDTO> getOneTicketDetalle(@PathVariable Long idTicket) {
        return TicketDetalleDTO.agruparDetallesLista(ticketDetalleRepository.findByTicket_ticketId(idTicket));
    }

    @PostMapping(Api_Map.TICKET_DETALLE_GUARDAR)
    public ResponseEntity<String> guardarTicketDetalle(@RequestBody TicketDetalleSimplificadoDTO ticketDetalleDTO) {
        Ticket ticket = ticketRepository.findById(ticketDetalleDTO.getIdTicket())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con id: " + ticketDetalleDTO.getIdTicket()));

        Producto producto = productoRepository.findById(ticketDetalleDTO.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + ticketDetalleDTO.getIdProducto()));

        Optional<TicketDetalle> detalleExistenteOpt = ticketDetalleRepository.findById(new TicketDetalleID(ticketDetalleDTO.getIdTicket(), ticketDetalleDTO.getIdProducto()));

        TicketDetalle ticketDetalle;
        if (detalleExistenteOpt.isPresent()) {
            ticketDetalle = detalleExistenteOpt.get();
            ticketDetalle.setCantidad(ticketDetalle.getCantidad() + ticketDetalleDTO.getCantidad());
        } else {
            ticketDetalle = new TicketDetalle();
            ticketDetalle.setTicket(ticket);
            ticketDetalle.setTicketId(ticket.getTicketId());
            ticketDetalle.setProducto(producto);
            ticketDetalle.setProductoId(producto.getProductoId());
            ticketDetalle.setCantidad(ticketDetalleDTO.getCantidad());
        }

        ticketDetalleRepository.save(ticketDetalle);

        return ResponseEntity.ok("Detalle Guardado!!!");
    }


    @PostMapping(Api_Map.TICKET_DETALLE_ELIMINAR)
    public ResponseEntity<String> eliminarTicketDetalle(@RequestBody TicketDetalleSimplificadoDTO ticketDetalleDTO) {
        System.out.println("detalles a eliminar: " + ticketDetalleDTO.getIdTicket() + " " + ticketDetalleDTO.getIdProducto() + " " + ticketDetalleDTO.getCantidad());
        Optional<TicketDetalle> detalleExistenteOpt = ticketDetalleRepository.findById(new TicketDetalleID(ticketDetalleDTO.getIdTicket(), ticketDetalleDTO.getIdProducto()));

        if (!detalleExistenteOpt.isPresent()) {
            return ResponseEntity.status(404).body("El detalle del ticket no existe.");
        }

        ticketDetalleRepository.delete(detalleExistenteOpt.get());
        return ResponseEntity.ok("Detalle del ticket eliminado correctamente.");
    }

}
