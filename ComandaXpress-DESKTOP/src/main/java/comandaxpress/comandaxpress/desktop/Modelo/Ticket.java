package comandaxpress.comandaxpress.desktop.Modelo;
import javax.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Tickets")
public class Ticket {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ticket_id")
        private Long ticketId;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "mesa_id", nullable = false)
        private Mesa mesa;

        @Column(nullable = false , name="fecha_hora")
        @Temporal(TemporalType.TIMESTAMP)
        private Date fechaHora = new Date();

        @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
        private List<TicketDetalle> ticketDetalles;

    @Override
    public String toString() {
        return "Ticket: "+ticketId;
    }
        
        
}
