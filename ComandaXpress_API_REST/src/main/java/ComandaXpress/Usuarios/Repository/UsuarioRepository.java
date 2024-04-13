package ComandaXpress.Usuarios.Repository;

import ComandaXpress.Usuarios.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
