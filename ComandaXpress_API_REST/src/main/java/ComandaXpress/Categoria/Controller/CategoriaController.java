package ComandaXpress.Categoria.Controller;

import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.Categoria.Repository.CategoriaRepository;
import ComandaXpress.DTO.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<CategoriaDTO> obtenerCategorias() {
        return categoriaRepository.findAll().stream().map(CategoriaDTO::converter).collect(Collectors.toList());
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorID(@PathVariable Long categoriaId){
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        return categoria.map(c -> ResponseEntity.ok(CategoriaDTO.converter(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public String guardarCategoria(@RequestBody Categoria categoria) {
        categoriaRepository.save(categoria);
        return "Categoría guardada con éxito!";
    }

    @PutMapping("/{categoriaId}")
    public String actualizarCategoria(@PathVariable Long categoriaId, @RequestBody Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoriaExistente.setNombre(categoria.getNombre());
        categoriaExistente.setDescripcion(categoria.getDescripcion());

        categoriaRepository.save(categoriaExistente);
        return "Categoría actualizada con éxito!";
    }

    @DeleteMapping("/{categoriaId}")
    public String eliminarCategoria(@PathVariable Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoriaRepository.delete(categoria);
        return "Categoría eliminada con éxito!";
    }
}
