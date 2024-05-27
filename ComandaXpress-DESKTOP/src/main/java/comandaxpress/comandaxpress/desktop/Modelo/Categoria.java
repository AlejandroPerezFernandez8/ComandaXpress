package comandaxpress.comandaxpress.desktop.Modelo;
import lombok.Data;

import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name = "Categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Long categoriaId;
    @Column(nullable = false)
    private String nombre;
    @Column
    private String descripcion;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    private List<Producto> productos;
}

