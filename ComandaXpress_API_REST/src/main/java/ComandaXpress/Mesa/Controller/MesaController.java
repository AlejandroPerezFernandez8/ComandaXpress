package ComandaXpress.Mesa.Controller;

import ComandaXpress.Api_Map;
import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.DTO.CategoriaDTO;
import ComandaXpress.DTO.MesaDTO;
import ComandaXpress.Mesa.Model.Mesa;
import ComandaXpress.Mesa.Repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api_Map. MESA_BASE_URL)
public class MesaController {
    @Autowired
    private MesaRepository mesaRepository;
    @GetMapping
    public List<MesaDTO> getAllMesas() {
        return mesaRepository.findAll().stream().map(MesaDTO::converter).collect(Collectors.toList());
    }
    @GetMapping(Api_Map.MESA_ID_URL)
    public ResponseEntity<MesaDTO> obtenerMesaPorID(@PathVariable Long mesaId){
        Optional<Mesa> mesa = mesaRepository.findById(mesaId);
        return mesa.map(c -> ResponseEntity.ok(MesaDTO.converter(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public String guardarMesa(@RequestBody Mesa mesa) {
        mesaRepository.save(mesa);
        return "Mesa guardada con éxito!";
    }
    @PutMapping(Api_Map.MESA_ID_URL)
    public String actualizarMesa(@PathVariable Long mesaId, @RequestBody MesaDTO mesaDTO) {
        Mesa mesaExistente = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada")); //Excepcion
        mesaExistente.setActiva(mesaDTO.getActiva());
        mesaExistente.setCapacidad(mesaDTO.getCapacidad());
        mesaExistente.setNumero(mesaDTO.getNumero());
        mesaRepository.save(mesaExistente);
        return "Mesa actualizada con éxito!";
    }
    @DeleteMapping(Api_Map.MESA_ID_URL)
    public String eliminarMesa(@PathVariable Long mesaId) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")); // Excepcion
        mesaRepository.delete(mesa);
        return "Mesa eliminada con éxito!";
    }
}
