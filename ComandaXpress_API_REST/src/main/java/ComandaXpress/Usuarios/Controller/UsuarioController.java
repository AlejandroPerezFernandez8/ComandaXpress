package ComandaXpress.Usuarios.Controller;

import ComandaXpress.Api_Map;
import ComandaXpress.DTO.UsuarioDTO;
import ComandaXpress.Usuarios.Model.Usuario;
import ComandaXpress.Usuarios.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api_Map.USUARIO_BASE_URL)
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping
    public List<UsuarioDTO> getUsuarios(){
        return usuarioRepository.findAll().stream().map(UsuarioDTO::converter).collect(Collectors.toList());
    }
    @PostMapping(Api_Map.USUARIO_LOGIN_URL)
    public ResponseEntity<Usuario> loginUsuario(@RequestBody Map<String, String> loginDetails) {
        Usuario usuario = usuarioRepository.findByUsuarioAndContraseña(loginDetails.get("usuario"), loginDetails.get("contraseña"));
        if(usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping(Api_Map.USUARIO_GUARDAR_URL)
    public ResponseEntity saveusuario(@RequestBody UsuarioDTO usuarioDTO){
        try {
            usuarioRepository.save(UsuarioDTO.fromEntity(usuarioDTO));
            return new ResponseEntity<>("Usuario Registrado", HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e) {
            System.out.println(e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>("Error: El usuario ya existe.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(Api_Map.USUARIO_MODIFICAR_URL)
    public String modificarUsuario(@PathVariable long usuario_id ,@RequestBody Usuario usuario){
        try {
            Usuario usuarioModificado = usuarioRepository.findById(usuario_id).get();
            //Modificamos los varoles del usuario
            usuarioModificado.setNombre(usuario.getNombre());
            usuarioModificado.setApellido(usuario.getApellido());
            usuarioModificado.setUsuario(usuario.getUsuario());
            usuarioModificado.setContraseña(usuario.getContraseña());
            usuarioModificado.setEmail(usuario.getEmail());
            usuarioModificado.setFoto(usuario.getFoto());
            usuarioRepository.save(usuarioModificado);
            return "Usuario Modificado!!!";
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            if(ex.getMessage().contains("Duplicate entry")){
                return "Usuario duplicado";
            }
            if(ex.getMessage().contains("duplicate")){
                return "Usuario duplicado";
            }else {return "Ha ocurrido un error";}
        }
    }
    @DeleteMapping(Api_Map.USUARIO_ELIMINAR_URL)
    public String borrarUsuario(@PathVariable long usuario_id){
        String nombre = usuarioRepository.findById(usuario_id).get().getNombre();
        usuarioRepository.delete(usuarioRepository.findById(usuario_id).get());
        return "Usuario " + nombre + " Eliminado!!";
    }
}
