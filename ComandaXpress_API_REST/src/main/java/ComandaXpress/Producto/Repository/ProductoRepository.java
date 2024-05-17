package ComandaXpress.Producto.Repository;

import ComandaXpress.Producto.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoriaCategoriaId(Long categoriaId);
}
