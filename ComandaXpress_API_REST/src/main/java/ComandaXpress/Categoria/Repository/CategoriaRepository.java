package ComandaXpress.Categoria.Repository;

import ComandaXpress.Categoria.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
