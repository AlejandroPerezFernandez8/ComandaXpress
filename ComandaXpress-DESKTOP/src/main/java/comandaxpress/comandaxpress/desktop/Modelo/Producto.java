package comandaxpress.comandaxpress.desktop.Modelo;
import lombok.Data;
import lombok.Getter;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "Productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precio;
}
