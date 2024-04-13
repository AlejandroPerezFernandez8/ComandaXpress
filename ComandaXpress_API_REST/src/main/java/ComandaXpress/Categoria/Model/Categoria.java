package ComandaXpress.Categoria.Model;

import ComandaXpress.Producto.Model.Producto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaId;
    @Column(nullable = false)
    private String nombre;
    @Column
    private String descripcion;


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Producto> productos;
}
