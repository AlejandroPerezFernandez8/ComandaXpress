package comandaxpress.comandaxpress.desktop.Modelo;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long usuario_id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String email;
    @Column
    private String usuario;
    @Column
    private String contraseña;
    @Column
    private String foto;
}
