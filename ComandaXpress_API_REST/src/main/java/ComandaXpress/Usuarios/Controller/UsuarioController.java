package ComandaXpress.Usuarios.Controller;

import ComandaXpress.Usuarios.Model.Usuario;
import ComandaXpress.Usuarios.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/usuarios")
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    @PostMapping(value = "/saveUsuario")
    public String saveusuario(@RequestBody Usuario usuario){
        usuarioRepository.save(usuario);
        return "Usuario Registrado!!!";
    }

    @PutMapping(value = "/modificarUsuario/{usuario_id}")
    public String modificarUsuario(@PathVariable long usuario_id ,@RequestBody Usuario usuario){
        Usuario usuarioModificado = usuarioRepository.findById(usuario_id).get();
        //Modificamos los varoles del usuario
        usuarioModificado.setNombre(usuario.getNombre());
        usuarioModificado.setApellido(usuario.getApellido());
        usuarioModificado.setUsuario(usuario.getUsuario());
        usuarioModificado.setContraseña(usuario.getContraseña());
        usuarioModificado.setEmail(usuario.getEmail());
        usuarioModificado.setRol(usuario.getRol());

        usuarioRepository.save(usuarioModificado);
        return "Usuario Modificado!!!";
    }


    @DeleteMapping(value = "/borrarUsuario/{usuario_id}")
    public String borrarUsuario(@PathVariable long usuario_id){
        String nombre = usuarioRepository.findById(usuario_id).get().getNombre();
        usuarioRepository.delete(usuarioRepository.findById(usuario_id).get());
        return "Usuario " + nombre + " Eliminado!!";
    }

}
