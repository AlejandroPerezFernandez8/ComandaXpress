package ComandaXpress.Mesa.Repository;

import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.Mesa.Model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa,Long> {
}
