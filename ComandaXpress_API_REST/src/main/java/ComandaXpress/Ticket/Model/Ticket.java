package ComandaXpress.Ticket.Model;

import ComandaXpress.Mesa.Model.Mesa;
import ComandaXpress.TicketDetalle.Model.TicketDetalle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Tickets")
public class Ticket {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long ticketId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "mesa_id", nullable = false)
        @JsonManagedReference
        @JsonIgnore
        private Mesa mesa;

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date fechaHora = new Date();

        @OneToMany(mappedBy = "ticket", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
        @JsonManagedReference
        @JsonIgnore
        private List<TicketDetalle> ticketDetalles;
}
