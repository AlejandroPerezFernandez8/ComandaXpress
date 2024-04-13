package ComandaXpress.Producto.Model;
import ComandaXpress.Categoria.Model.Categoria;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;

    // Este método proporciona el ID de la categoría para la serialización JSON
    @JsonProperty("categoriaId")
    @JsonInclude(JsonInclude.Include.NON_NULL) // Incluye el ID de la categoría solo si no es null
    public Long getCategoriaId() {
        return categoria != null ? categoria.getCategoriaId() : null;
    }
}
