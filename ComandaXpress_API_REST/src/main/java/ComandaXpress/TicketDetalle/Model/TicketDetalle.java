package ComandaXpress.TicketDetalle.Model;

import ComandaXpress.Producto.Model.Producto;
import ComandaXpress.Ticket.Model.Ticket;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "TicketDetalle")
@IdClass(TicketDetalleID.class)
public class TicketDetalle {
        @Id
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ticket_id")
        private Ticket ticket;

        @Id
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "producto_id")
        private Producto producto;

        @Column(nullable = false)
        private Integer cantidad;
}

