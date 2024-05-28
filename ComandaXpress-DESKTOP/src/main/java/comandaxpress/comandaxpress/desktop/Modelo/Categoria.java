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
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE , fetch = FetchType.EAGER)
    private List<Producto> productos;

    @Override
    public String toString() {return nombre;}
}

