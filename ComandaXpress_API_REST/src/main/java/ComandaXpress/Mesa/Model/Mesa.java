package ComandaXpress.Mesa.Model;

import ComandaXpress.Ticket.Model.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "mesa")
public class Mesa {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long mesaId;

        @Column(nullable = false, unique = true)
        private Integer numero;

        @Column(nullable = false)
        private Integer capacidad;

        @Column(nullable = false)
        private Boolean activa;

        @OneToMany(mappedBy = "mesa", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
        @JsonManagedReference
        @JsonIgnore
        private List<Ticket> tickets;
}
