package ComandaXpress.Producto.Repository;

import ComandaXpress.Producto.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
