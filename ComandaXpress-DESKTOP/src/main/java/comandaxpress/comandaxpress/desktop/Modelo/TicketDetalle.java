package comandaxpress.comandaxpress.desktop.Modelo;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ticket_Detalle")
@IdClass(TicketDetalleID.class)
public class TicketDetalle {
        @Id
        @Column(name = "ticket_id")
        private Long ticketId;

        @Id
        @Column(name = "producto_id")
        private Long productoId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ticket_id", insertable = false, updatable = false)
        private Ticket ticket;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "producto_id", insertable = false, updatable = false)
        private Producto producto;

        @Column(name = "cantidad",nullable = false)
        private Integer cantidad;
}
