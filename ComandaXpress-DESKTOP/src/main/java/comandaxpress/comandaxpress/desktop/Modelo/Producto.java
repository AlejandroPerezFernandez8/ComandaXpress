package comandaxpress.comandaxpress.desktop.Modelo;
import lombok.Data;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
@Data
@Entity
@Table(name = "Productos")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long productoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false, name="nombre")
    private String nombre;

    @Column(nullable = false, name ="precio")
    private BigDecimal precio;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TicketDetalle> ticketDetalles;

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
