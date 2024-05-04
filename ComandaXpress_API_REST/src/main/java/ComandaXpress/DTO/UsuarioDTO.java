package ComandaXpress.DTO;


import ComandaXpress.Usuarios.Model.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private long usuario_id;
    private String nombre;
    private String apellido;

    private String email;

    private String usuario;

    private String contraseña;

    private String rol;

    public static UsuarioDTO converter(Usuario source){
        UsuarioDTO target = new UsuarioDTO();
        target.setUsuario_id(source.getUsuario_id());
        target.setNombre(source.getNombre());
        target.setApellido(source.getApellido());
        target.setEmail(source.getEmail());
        target.setUsuario(source.getUsuario());
        target.setContraseña(source.getContraseña());
        target.setRol(source.getRol());
        return target;
    }
    public static Usuario fromEntity(UsuarioDTO source){
        Usuario target = new Usuario();
        target.setNombre(source.getNombre());
        target.setApellido(source.getApellido());
        target.setEmail(source.getEmail());
        target.setUsuario(source.getUsuario());
        target.setContraseña(source.getContraseña());
        target.setRol(source.getRol());
        return target;
    }

}
