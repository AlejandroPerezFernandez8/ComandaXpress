package comandaxpress.comandaxpress.desktop.Modelo;
import lombok.Data;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name = "mesa")
public class Mesa {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "mesa_id")
        private Long mesaId;

        @Column(nullable = false, unique = true)
        private Integer numero;

        @Column(nullable = false)
        private Integer capacidad;

        @Column
        private Boolean activa = false;

        @OneToMany(mappedBy = "mesa", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
        private List<Ticket> tickets;

    @Override
    public String toString() {
        return numero.toString();
    }     
}
