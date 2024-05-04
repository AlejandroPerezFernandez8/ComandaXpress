package ComandaXpress.TicketDetalle.Model;

import ComandaXpress.Producto.Model.Producto;
import ComandaXpress.Ticket.Model.Ticket;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TicketDetalle")
@IdClass(TicketDetalleID.class)
public class TicketDetalle {
        @Id
        @Column(name = "ticketId")
        private Long ticketId;

        @Id
        @Column(name = "productoId")
        private Long productoId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ticketId")
        private Ticket ticket;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "productoId")
        private Producto producto;

        @Column(nullable = false)
        private Integer cantidad;
}

